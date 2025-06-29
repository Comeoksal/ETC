#include "common.h"
#include "server.h"

//플레이어 저장, 플레이어 삭제
void addPlayer(int sock, char *name);
void removePlayer(int sock);

//메인 화면 스레드, 게임 채널 연결, 게임 채널 나가기
void *main_input_handler(void *playerinfo);
void connectChannel(void *player, int sock, int channel_num, int *isConnectChannel);
void outChannel(int channel_number, int sock);

//게임 시작, 게임 초기화, 게임 진행
void *run_game(void *channel);
void gameInit(int channel_number);
void gameProgress(int channel_number, int flag);

//게임 화면 스레드
void *game_input_handler(void *channel);

//메인화면의 출력 함수들(맨 아래로 뺐습니다)
void printMainPage(char *pagebuf);
void printRule(char *pagebuf);
void printMyInfo(char *sendbuf, void *playerinfo);
void printRanking(char *sendbuf);

//사용자 구조체 리스트, 뮤텍스
PlayerInfo players[MAX_PLAYER];
int playerCount = 0;
pthread_mutex_t player_lock = PTHREAD_MUTEX_INITIALIZER;

//게임 채널 리스트, 채널별 뮤텍스, 채널 전체 뮤텍스
GameChannel channels[MAX_CHANNEL];
pthread_mutex_t channel_lock[MAX_CHANNEL] = {PTHREAD_MUTEX_INITIALIZER, PTHREAD_MUTEX_INITIALIZER};

//게임 진행  리스트, 게임 진행별 뮤텍스
GameState states[MAX_CHANNEL];
pthread_mutex_t game_lock[MAX_CHANNEL] = {PTHREAD_MUTEX_INITIALIZER, PTHREAD_MUTEX_INITIALIZER};

//게임에서 턴을 기다리는 상대 깨워주기(게임이 진행되거나, 끝났을 때)
pthread_cond_t game_cond[MAX_CHANNEL] = {PTHREAD_COND_INITIALIZER, PTHREAD_COND_INITIALIZER};

//자주 사용하는 문자열 메시지
char *number_msg = "| Input Number |";
char *command_msg = "| Input Command |";
char *unknown_msg = "Server : Unknown Command\n";

int main(int argc, char *argv[]){
        struct sockaddr_in cliaddr;
        int listen_sock, sock, addrlen = sizeof(struct sockaddr_in), nbyte;
        char name[32];

        srand(time(NULL));

        if(argc != 2){
                printf("usage: %s port\n", argv[0]);
                exit(1);
        }

        listen_sock = tcp_listen(INADDR_ANY, atoi(argv[1]), 5);

        while(1){
                sock = accept(listen_sock, (struct sockaddr*)&cliaddr, &addrlen);
                nbyte = read(sock, name, sizeof(name));
                name[nbyte] = 0;
                printf("사용자 %s : %d번 소켓으로  연결\n", name, sock);
                addPlayer(sock, name);
        }
        return 0;
}

void addPlayer(int sock, char *name){
        int nbyte;
        char pagebuf[PAGELINE], sendbuf[MAXLINE];
        pthread_t tid;

        //인자로 받은 이름과 소켓 번호로 플레이어 정보 구조체 저장
        pthread_mutex_lock(&player_lock);

        players[playerCount].sock = sock;
        strcpy(players[playerCount].name, name);
        players[playerCount].chips = 100;
        pthread_create(&tid, NULL, main_input_handler, &players[playerCount]);
        playerCount++;

        pthread_mutex_unlock(&player_lock);
}

void removePlayer(int sock){
        int i;
        char name[32];

        //사용자 제거, 만약 리스트 끝 플레이어가 아닐시 끝 플레이어를 제거된 사용자 위치로
        pthread_mutex_lock(&player_lock);

        for(i=0; i < playerCount; i++){
                if(players[i].sock == sock){
                        strcpy(name, players[i].name);
                        //outChannel(sock); 일단 보류
                        if(i!= playerCount-1){
                                players[i].sock = players[playerCount-1].sock;
                                strcpy(players[i].name, players[playerCount-1].name);
                                players[i].chips = players[playerCount-1].chips;
                        }
                        playerCount--;
                        close(sock);
                        break;
                }
        }
        pthread_mutex_unlock(&player_lock);

        printf("사용자 %s : %d 번 소켓 연결 종료\n", name,  sock);
}

void *main_input_handler(void *playerinfo){
        int i, j, nbyte, sock, isConnectChannel=0;
        char recvbuf[RECVLINE], sendbuf[MAXLINE], pagebuf[PAGELINE], *name;

        PlayerInfo *player = (PlayerInfo *)playerinfo;

        //메인화면 스레드 아이디를 플레이어 정보에 저장
        pthread_mutex_lock(&player_lock);
        player->main2_tid = pthread_self();
        player->channelNum = 0;
        pthread_mutex_unlock(&player_lock);

        //사용하기 편리하도록 지역변수에 저장
        sock = player->sock;
        name = player->name;

        //메인 페이지 보여주기
        printMainPage(pagebuf);
        snprintf(sendbuf, sizeof(sendbuf), "%s%s", pagebuf, number_msg);
        write(sock, sendbuf, strlen(sendbuf));

        while(1){
                //항상 보내는 버퍼 초기화(덮어 씌워짐 방지)
                memset(sendbuf, 0, sizeof(sendbuf));
                nbyte = read(sock, recvbuf, RECVLINE);
                if(nbyte<=0){
                        close(sock);
                        removePlayer(sock);
                        break;
                }

                recvbuf[nbyte] = 0;
                printf("[MainChannel]%s : %s", name, recvbuf);

                //사용자가 어떤 메시지를 보내도 채널에 접속해있는 상태면 막아버리기
                if(isConnectChannel==1){
                        snprintf(sendbuf, sizeof(sendbuf), "Server : You can't Input Message(reason : Connecting game channel)");
                        write(sock, sendbuf, strlen(sendbuf));
                        continue;
                }

                //게임 룰 출력
                if(strcmp(recvbuf, "0\n") == 0){
                        printRule(pagebuf);
                        snprintf(sendbuf, sizeof(sendbuf), "%s%s", pagebuf, number_msg);
                        write(sock, sendbuf, strlen(sendbuf));
                } else if (strcmp(recvbuf, "1\n") == 0){ //게임채널 1 접속
                        if(player->chips<10){
                                snprintf(sendbuf, sizeof(sendbuf), "\n### Server : You can't connect channel(reason : has less than 10 chips) ###\n%s", numbe
r_msg);
                                write(sock, sendbuf, strlen(sendbuf));
                                continue;
                        }
                        //게임 채널1로 연결
                        connectChannel(player, sock, 1, &isConnectChannel);
                } else if (strcmp(recvbuf, "2\n") == 0) { //게임채널 2접속
                        if(player->chips<10){
                snprintf(sendbuf, sizeof(sendbuf), "\n### Server : You can't connect channel(reason : has less than 10 chips) ###\n%s", number_msg);
                write(sock, sendbuf, strlen(sendbuf));
                continue;
            }
                        //게임 채널2로 연결
                        connectChannel(player, sock, 2, &isConnectChannel);
                } else if(strcmp(recvbuf, "3\n")==0){ //내 정보 출력
                        printMyInfo(sendbuf, playerinfo);
                        write(sock, sendbuf, strlen(sendbuf));
                } else if(strcmp(recvbuf, "4\n") == 0){ //랭킹 출력
                        printRanking(sendbuf);
                        write(sock, sendbuf, strlen(sendbuf));
                } else if(strcmp(recvbuf, "5\n") == 0){ //나가기
                        removePlayer(sock);
                        break;
                } else if(strcmp(recvbuf, "777\n") == 0){ //관리자 전용 비밀 커맨드(입력시 chips 100개 추가)
                        pthread_mutex_lock(&player_lock);
                        player->chips += 100;
                        pthread_mutex_unlock(&player_lock);
                        snprintf(sendbuf, sizeof(sendbuf), "%s", number_msg);
                        write(sock, sendbuf, strlen(sendbuf));
                } else { //존재하지 않는 커맨드
                        snprintf(sendbuf, sizeof(sendbuf), "%s%s", unknown_msg, number_msg);
                        write(sock, sendbuf, strlen(sendbuf));
                }
        }
}

void connectChannel(void *player, int sock, int channel_number, int *isConnectChannel){
    char sendbuf[MAXLINE];

        //해당 채널 잠그고 채널 참가자 수를 조건으로 다른 실행
    pthread_mutex_lock(&channel_lock[channel_number-1]);
        pthread_mutex_lock(&player_lock);
    if(!channels[channel_number-1].player1){ //첫번째 참가자일 때
        channels[channel_number-1].player1 = player;
        snprintf(sendbuf, sizeof(sendbuf), "Server : waiting other player..\n");

                channels[channel_number-1].player1->channelNum = channel_number;

                //주소로 받은 채널 참가여부값 증가
        *isConnectChannel = 1;
        write(sock, sendbuf, strlen(sendbuf));
                pthread_mutex_unlock(&player_lock);
    } else if(!channels[channel_number-1].player2){ //두번째 참가자일 때
        channels[channel_number-1].player2 = player;
        channels[channel_number-1].isFull = 1;
        channels[channel_number-1].channelNum = channel_number;

                channels[channel_number-1].player2->channelNum = channel_number;
                pthread_mutex_unlock(&player_lock);

                //주소로 받은 채널 참가여부값 증가
        *isConnectChannel = 1;

                //두 번째 참가자는 run_game 스레드 시작함수로 이동
        pthread_t run_tid;
        pthread_create(&run_tid, NULL, run_game, &channels[channel_number-1]);
    } else if (channels[channel_number-1].isFull == 1){ //채널에 참가자가 가득 찼을 때
        snprintf(sendbuf, sizeof(sendbuf), "Server : The channel is already full!!\n%s", number_msg);
        write(sock, sendbuf, strlen(sendbuf));
                pthread_mutex_unlock(&player_lock);
    }
    pthread_mutex_unlock(&channel_lock[channel_number-1]);
}

void outChannel(int channel_number, int sock){
        //원래 사용자별 exit가 가능하게 만들려고 해서 코드가 아래와 같습니다.
    pthread_mutex_lock(&channel_lock[channel_number-1]);
        if(channels[channel_number-1].player1 && channels[channel_number-1].player1->sock == sock){
            if(!channels[channel_number-1].player2){
                channels[channel_number-1].player1 = channels[channel_number-1].player2;
            } else {
                channels[channel_number-1].player1 = NULL;
            }
        } else if(channels[channel_number-1].player2 && channels[channel_number-1].player2->sock == sock){
            channels[channel_number-1].player2 = NULL;
        }
    channels[channel_number-1].isFull = 0;
    pthread_mutex_unlock(&channel_lock[channel_number-1]);
}

void *run_game(void *channel){
        int i, j, nbyte;
        char sendbuf[MAXLINE];
        pthread_t game_tid1, game_tid2;

        GameChannel *ch = (GameChannel *)channel;
        PlayerInfo *player1 = ch->player1;
        PlayerInfo *player2 = ch->player2;

        //두 사용자의 메인화면 스레드  종료
        pthread_cancel(player1->main2_tid);
        pthread_cancel(player2->main2_tid);
        snprintf(sendbuf, sizeof(sendbuf), "\n ### Server : Matching Success. Game Start ###\n");

        pthread_mutex_lock(&player_lock);
        printf("player1 sock : %d\n", player1->sock);
        printf("player2 sock : %d\n", player2->sock);
        write(player1->sock, sendbuf, strlen(sendbuf));
        write(player2->sock, sendbuf, strlen(sendbuf));
        pthread_mutex_unlock(&player_lock);

        //게임 진행 구조체 초기화
        gameInit(ch->channelNum);

        //사용자별 진행 스레드 생성
        pthread_create(&game_tid1, NULL, game_input_handler, player1);
        pthread_create(&game_tid2, NULL, game_input_handler, player2);
}

void gameInit(int channel_number){
        int i, j, random1, random2;
        GameChannel *ch = &channels[channel_number-1];

        PlayerInfo *player1 = ch->player1;
        PlayerInfo *player2 = ch->player2;

        pthread_mutex_lock(&game_lock[channel_number-1]);
        //진행 구조체에 플레이어와 선 플레이어 지정
    states[ch->channelNum -1].player1 = player1;
    states[ch->channelNum -1].player2 = player2;
    states[ch->channelNum -1].turn = 1;

        //1~10까지의 카드 묶음 2세트 준비
    for(i=0; i<20; i++){
        if(i>9){
            states[ch->channelNum-1].card[i] = (i+1)-10;
        } else {
            states[ch->channelNum-1].card[i] = i+1;
        }
    }
        //플레이어가 가지고 있는 카드 초기화
    for(i=0; i<2; i++){
        for(j=0; j<2; j++){
            states[ch->channelNum-1].player_card[i][j] = 0;
        }
    }
        //플레이어별 베팅값, 팟 초기화
    states[ch->channelNum-1].player_bet[0] = 0;
    states[ch->channelNum-1].player_bet[1] = 0;
    states[ch->channelNum-1].pot = 0;
    pthread_mutex_unlock(&game_lock[channel_number-1]);

        //랜덤한 두 수 중복되지 않게 뽑아내어 플레이어에게 카드 분배
    random1 = rand() % 20;
    do {
        random2 = rand() % 20;
    } while(random2==random1);

    pthread_mutex_lock(&game_lock[channel_number-1]);
    states[ch->channelNum-1].player_card[0][0] = states[ch->channelNum-1].card[random1];
    states[ch->channelNum-1].player_card[1][0] = states[ch->channelNum-1].card[random2];

        //분배된  카드는 0으로 세팅
    states[ch->channelNum-1].card[random1] = 0;
    states[ch->channelNum-1].card[random2] = 0;

        //사용자 결과 확인을 모두 미확인으로 세팅
        states[ch->channelNum-1].checkResult = 0;
    states[ch->channelNum-1].flag = 0;
    pthread_mutex_unlock(&game_lock[channel_number-1]);
}

void gameProgress(int channel_number, int flag){
        int i, random1, random2, score[2];

        if(flag==1){ //만약 첫번째 패가 들어간 후 베팅이 끝났을 시
        pthread_mutex_lock(&game_lock[channel_number-1]);
                //플레이어들 베팅값 0으로 세팅
                for(i=0; i<MAX_GAMEPLAYER; i++){
                        states[channel_number-1].player_bet[i] = 0;
                }
                //또 랜덤한 두 수 뽑아서 카드 분배(단 이미 분배한 카드의 위치 뽑을시 수 다시뽑기)
        do{
                random1 = rand() % 20;
                        do {
                        random2 = rand() % 20;
                } while(random2==random1);
                } while(states[channel_number-1].card[random1] == 0 || states[channel_number-1].card[random2] == 0);
        states[channel_number-1].player_card[0][1] = states[channel_number-1].card[random1];
        states[channel_number-1].player_card[1][1] = states[channel_number-1].card[random2];
        states[channel_number-1].card[random1] = 0;
        states[channel_number-1].card[random2] = 0;
                states[channel_number-1].flag = 2;
        pthread_mutex_unlock(&game_lock[channel_number-1]);
        } else if(flag == 2){ //만약 두번째 패가 들어가고 베팅이 끝났을 시
                pthread_mutex_lock(&game_lock[channel_number-1]);
                //플레이어들  점수 계산
                for(i=0; i<MAX_GAMEPLAYER; i++){
                        if(states[channel_number-1].player_card[i][0] == states[channel_number-1].player_card[i][1]){
                                score[i] = states[channel_number-1].player_card[i][0] * 10;
                        } else {
                                score[i] = (states[channel_number-1].player_card[i][0] + states[channel_number-1].player_card[i][1]) % 10;
                        }
                }
                if(score[0] > score[1]){ //이겼을 때
                        states[channel_number-1].turn = 1; //턴 1로 지정

                        pthread_mutex_lock(&player_lock);
                        states[channel_number-1].player1->chips += states[channel_number-1].pot;
                        pthread_mutex_unlock(&player_lock);

                } else if(score[0] < score[1]){ // 졌을 때
                        states[channel_number-1].turn = 2; //턴 2로 지정

                        pthread_mutex_lock(&player_lock);
                        states[channel_number-1].player2->chips += states[channel_number-1].pot;
                        pthread_mutex_unlock(&player_lock);

                } else{ // 무승부
                        states[channel_number-1].turn = 0; //턴 0으로 지정

                        pthread_mutex_lock(&player_lock);
                        states[channel_number-1].player1->chips += states[channel_number-1].pot/2;
                        states[channel_number-1].player2->chips += states[channel_number-1].pot/2;
                        pthread_mutex_unlock(&player_lock);
                }
                states[channel_number-1].flag = 3;
                pthread_mutex_unlock(&game_lock[channel_number-1]);
        }
}

void *game_input_handler(void *playerinfo){
        int i, j, nbyte, sock, channel_number, player_number, opponent_number, gamemain_fd, exit_count=0;
        char recvbuf[RECVLINE], sendbuf[MAXLINE], pagebuf[PAGELINE], *name, *token;
        //반복문을 돌 때 편하게 사용하려고 만들었습니다.(0: 현재 스레드 플레이어, 1: 상대 플레이어)
        int player_sock[2];

        //현재 스레드에 들어온 플레이어 정보저장
        pthread_mutex_lock(&player_lock);
        PlayerInfo *player = (PlayerInfo *)playerinfo;
        sock = player->sock;
        player_sock[0] = sock;
        name = player->name;
        channel_number = player->channelNum;

        //상대 정보 저장
        PlayerInfo *opponent;
        pthread_mutex_lock(&channel_lock[channel_number-1]);
        if(channels[channel_number-1].player1 && channels[channel_number-1].player1->sock == sock){
        player_number = 1;
        player_sock[1] = channels[channel_number-1].player2->sock;
        opponent_number = 2;
                opponent = channels[channel_number-1].player2;
    } else if(channels[channel_number-1].player2 && channels[channel_number-1].player2->sock == sock){
        player_number = 2;
        player_sock[1] = channels[channel_number-1].player1->sock;
        opponent_number = 1;
                opponent = channels[channel_number-1].player1;
    }
        pthread_mutex_unlock(&channel_lock[channel_number-1]);
        pthread_mutex_unlock(&player_lock);

        //플래그 1로 세팅하여 게임 시작
        pthread_mutex_lock(&game_lock[channel_number-1]);
        states[channel_number-1].flag = 1;
        pthread_mutex_unlock(&game_lock[channel_number-1]);

        //서버에 로그 출력 후, pagebuf에 문자열 저장해두기
        printf("channel_num : %d\n player : %s\n player_number : %d\n", channel_number, name, player_number);
        gamemain_fd = open("pages/gamemainPage", 0644);
        nbyte = read(gamemain_fd, pagebuf, sizeof(pagebuf));

        while(1){
                //전송 버퍼변수 초기화(덮어쓰기 방지)
                memset(sendbuf, 0, sizeof(sendbuf));
                if(states[channel_number-1].flag == 0){ // 게임 종료(초기화 상태)
                        snprintf(sendbuf, sizeof(sendbuf), "\n ### Server : The channel will close in 7 seconds... ###\n");
                        write(sock, sendbuf, strlen(sendbuf));
                        //7초 대기후 종료
                        while(exit_count!=7){
                                sleep(1);
                                exit_count++;
                        }
                        //해당 채널에 소켓번호를 가진 플레이어 제거
                        outChannel(channel_number, sock);

                        //메인2스레드(메인화면)으로 돌아가기
            pthread_t main2_tid;
            pthread_create(&main2_tid, NULL, main_input_handler, player);
            pthread_exit(NULL);
 } else if(states[channel_number-1].flag == 1){ //1라운드 상태(카드 한 장을 받은 상태)
                        //아직 베팅을 안 한 상태라면 커맨드와 받은 카드 출력(한 번만 되도록)
                        if(states[channel_number-1].player_bet[player_number-1] == 0){
                                snprintf(sendbuf, sizeof(sendbuf), "%s\n|<Your Number Card : %d ? >\n", pagebuf, states[channel_number-1].player_card[player_
number-1][0]);
                                write(sock, sendbuf, strlen(sendbuf));
                        }
                        //이번 라운드에 내 턴이 올 때까지 대기
                        pthread_mutex_lock(&game_lock[channel_number-1]);
                        while(states[channel_number-1].turn != player_number && states[channel_number-1].flag == 1){
                                pthread_cond_wait(&game_cond[channel_number-1], &game_lock[channel_number-1]);
                        }
                        //만약 다음 라운드가 진행됐다면 continue로 다음 라운드의 else if문으로
                        if(states[channel_number-1].flag == 2 || states[channel_number-1].flag == 0){
                                pthread_mutex_unlock(&game_lock[channel_number-1]);
                                continue;
                        }
                        //내 턴에 관한 정보 출력
                        snprintf(sendbuf, sizeof(sendbuf), "| Your turn. (Your Chips : %d)\n| Pot : %d\n| oppenent total bet : %d, to call : %d\n%s", player-
>chips, states[channel_number-1].pot, states[channel_number-1].player_bet[opponent_number-1], states[channel_number-1].player_bet[opponent_number-1] - states
[channel_number-1].player_bet[player_number-1], command_msg);
                        write(sock, sendbuf, strlen(sendbuf));
                        pthread_mutex_unlock(&game_lock[channel_number-1]);
                } else if(states[channel_number-1].flag == 2){ //2라운드 상태 (카드 두 장을 받은 상태)
                        //아직 베팅을 안 한 상태라면 커맨드와 받은 카드 출력(한 번만 되도록)
                        if(states[channel_number-1].player_bet[player_number-1] == 0){
                                snprintf(sendbuf, sizeof(sendbuf), "%s\n<Your Number Card : %d %d >\n", pagebuf, states[channel_number-1].player_card[player_
number-1][0], states[channel_number-1].player_card[player_number-1][1]);
                                write(sock, sendbuf, strlen(sendbuf));
                        }
                        //만약 내 턴인데 칩이 없다면
                        if(states[channel_number-1].turn == player_number && player->chips == 0){
                                snprintf(sendbuf, sizeof(sendbuf), "\n### Server : You have no chips left. Bet any number to continue ###");
                                write(player_sock[0], sendbuf, strlen(sendbuf));
                        }
                        //이번 라운드에 내 턴이 올 때까지 대기
                        pthread_mutex_lock(&game_lock[channel_number-1]);
                        while(states[channel_number-1].turn != player_number && states[channel_number-1].flag == 2){
                                pthread_cond_wait(&game_cond[channel_number-1], &game_lock[channel_number-1]);
                        }
                        //만약 다음 라운드가 진행됐다면 continue로 다음 라운드의 else if문으로
                        if(states[channel_number-1].flag == 3 || states[channel_number-1].flag == 0){
                                pthread_mutex_unlock(&game_lock[channel_number-1]);
                                continue;
                        }
                        //내 턴에 관한 정보 출력
                        snprintf(sendbuf, sizeof(sendbuf), "| Your turn. (Your Chips : %d)\n| Pot : %d\n| oppenent total bet : %d, to call : %d\n%s", player-
>chips, states[channel_number-1].pot, states[channel_number-1].player_bet[opponent_number-1], states[channel_number-1].player_bet[opponent_number-1] - states
[channel_number-1].player_bet[player_number-1], command_msg);
                        write(sock, sendbuf, strlen(sendbuf));
                        pthread_mutex_unlock(&game_lock[channel_number-1]);
                } else if(states[channel_number-1].flag == 3){
                        char *result;

                        //결과 출력
                        if(states[channel_number-1].turn == player_number){
                                result = "win";
                        } else if(states[channel_number-1].turn == opponent_number){
                                result = "lose";
                        } else {
                                result = "draw";
                        }
                        snprintf(sendbuf, sizeof(sendbuf), "| Result : You %s.\n| Pot : %d\n| Your Chips : %d\n| <Opponent Cards : %d %d >\n| <Your Cards : %
d %d >\n", result, states[channel_number-1].pot, player->chips, states[channel_number-1].player_card[opponent_number-1][0], states[channel_number-1].player_c
ard[opponent_number-1][1], states[channel_number-1].player_card[player_number-1][0], states[channel_number-1].player_card[player_number-1][1]);
                        write(sock, sendbuf, strlen(sendbuf));

                        //상대방도 결과를 볼 때까지 대기(상대는 위의 flag=2에서 자고 있다가 깨어나기 때문)
                        pthread_mutex_lock(&game_lock[channel_number-1]);
                        states[channel_number-1].checkResult++;
                        if(states[channel_number-1].checkResult == 2){
                                //모두 결과확인시 게임 초기화
                                pthread_mutex_unlock(&game_lock[channel_number-1]);
                                gameInit(channel_number);
                                pthread_mutex_lock(&game_lock[channel_number-1]);

                                pthread_cond_broadcast(&game_cond[channel_number-1]);
} else {
                                while(states[channel_number-1].flag == 3){ //첫번째 결과 확인 플레이어 기준 상대가 결과 미확인시 게임 초기화까지 대기
                                        pthread_cond_wait(&game_cond[channel_number-1], &game_lock[channel_number-1]);
                                }
                        }
                        pthread_mutex_unlock(&game_lock[channel_number-1]);
                        continue;
                }

                //위 if문들을 거쳐서 자신의 턴이면 입력한 값을 받습니다
                nbyte = read(sock, recvbuf, MAXLINE);
                if(nbyte<=0){
                        close(sock);
                        removePlayer(sock);
                        break;
                }

                recvbuf[nbyte] = 0;
                printf("[GameChannel(%d)]%s : %s", channel_number, name, recvbuf);

                //베팅 값 확인을 위한 공백 기준 토큰화
                token = strtok(recvbuf, " ");

                if(strcmp(token, "/bet") == 0){
                        token = strtok(NULL, " ");
                        int bet = atoi(token);

                        //1. 내가 가진 칩보다 많이 냈을 때
                        if(bet > player->chips){
                                if(player->chips == 0){ //가진칩이 없다면 게임 강제 진행 및 대기 중인 상대 꺠우기
                                        snprintf(sendbuf, sizeof(sendbuf), "\n### Server : The game continues because of the opponent has no chips left ###\n
");
                                        write(player_sock[1], sendbuf, strlen(sendbuf));
                                        gameProgress(channel_number, states[channel_number-1].flag);
                                        pthread_mutex_lock(&game_lock[channel_number-1]);
                                        pthread_cond_broadcast(&game_cond[channel_number-1]);
                                        pthread_mutex_unlock(&game_lock[channel_number-1]);
                                        continue;
                                }
                                //재입력 요청하기
                                snprintf(sendbuf, sizeof(sendbuf), "\n### Server: You don't have enough chips! input again. ###\n");
                                write(sock, sendbuf, strlen(sendbuf));
                                continue;
                        }
                        //2. 상대가 낸 칩보다 적게 냈을 때
                        if(bet + states[channel_number-1].player_bet[player_number-1] < states[channel_number-1].player_bet[opponent_number-1]){
                                snprintf(sendbuf, sizeof(sendbuf), "\n### Server: You must input in at least as many chips as your opponent. input again. ###
\n");
                                write(sock, sendbuf, strlen(sendbuf));
                                continue;
                        }
                        //3. 적어도 상대가 낸 칩만큼 냈을 때
                        pthread_mutex_lock(&player_lock);
                        player->chips -=bet;
                        pthread_mutex_unlock(&player_lock);

                        pthread_mutex_lock(&game_lock[channel_number-1]);
                        states[channel_number-1].player_bet[player_number-1] += bet;
                        states[channel_number-1].pot += bet;
                        states[channel_number-1].turn = opponent_number;
                        pthread_mutex_unlock(&game_lock[channel_number-1]);

                        //양쪽 플레이어 모두의 베팅 금액이 같을 때
                        if(states[channel_number-1].player_bet[opponent_number-1] == states[channel_number-1].player_bet[player_number-1]){
                                snprintf(sendbuf, sizeof(sendbuf), "\n### Server : Bet Completed. ###\n");
                                for(i=0; i<MAX_GAMEPLAYER; i++){
                                        write(player_sock[i], sendbuf, strlen(sendbuf));
                                }
                                //게임 진행
                                gameProgress(channel_number, states[channel_number-1].flag);
                        }
                        //상대 플레이어 깨우기
                        pthread_mutex_lock(&game_lock[channel_number-1]);
                        pthread_cond_broadcast(&game_cond[channel_number-1]);
                        pthread_mutex_unlock(&game_lock[channel_number-1]);
                } else if(strcmp(token, "/die\n") == 0){ //죽었을 때
                        //한 쪽 스레드에서 양쪽 플레이어에게 메시지를 보낸 후 상대 플레이어는 깨움
                        snprintf(sendbuf, sizeof(sendbuf), "| Result : You lose.\n| Pot : %d\n| Your Chips : %d\n", states[channel_number-1].pot, player->chi
ps);
                        write(player_sock[0], sendbuf, strlen(sendbuf));

                        pthread_mutex_lock(&player_lock);
                        opponent->chips += states[channel_number-1].pot;
                        pthread_mutex_unlock(&player_lock);

                        snprintf(sendbuf, sizeof(sendbuf), "| Result : Opponent died. You take the pot!\n| Pot : %d\n| Your Chips : %d", states[channel_numbe
r-1].pot, opponent->chips);
                        write(player_sock[1], sendbuf, strlen(sendbuf));
                        //게임 초기화
                        gameInit(channel_number);

                        pthread_mutex_lock(&game_lock[channel_number-1]);
                        pthread_cond_broadcast(&game_cond[channel_number-1]);
                        pthread_mutex_unlock(&game_lock[channel_number-1]);
                } else { //존재하지 않는 커맨드
                        snprintf(sendbuf, sizeof(sendbuf), "%s%s", unknown_msg, command_msg);
                        write(sock, sendbuf, strlen(sendbuf));
                }
        }
}

void printMainPage(char *pagebuf){
    int nbyte, main_fd;

    memset(pagebuf, 0, PAGELINE);
    main_fd = open("pages/mainPage", 0644);
    nbyte = read(main_fd, pagebuf, PAGELINE);
    pagebuf[nbyte] = 0;
}

void printRule(char *pagebuf){
    int nbyte, gamerule_fd;

    memset(pagebuf, 0, PAGELINE);
    gamerule_fd = open("pages/gamerulePage", 0644);
    nbyte = read(gamerule_fd, pagebuf, PAGELINE);
    pagebuf[nbyte]=0;
}

void printMyInfo(char *sendbuf, void *playerinfo){
    PlayerInfo *player = (PlayerInfo *)playerinfo;

    pthread_mutex_lock(&player_lock);
    snprintf(sendbuf, MAXLINE, "==========MyInformation==========\nName : %s\nChips : %d\n", player->name, player->chips);
    pthread_mutex_unlock(&player_lock);
    strcat(sendbuf, "=================================\n");
    strcat(sendbuf, number_msg);
}

void printRanking(char *sendbuf){
    int i, j;
    char pagebuf[PAGELINE];

    PlayerInfo ranking[MAX_PLAYER];
    pthread_mutex_lock(&player_lock);
    for(i=0; i<playerCount; i++){
        ranking[i] = players[i];
    }
    pthread_mutex_unlock(&player_lock);

        //버블 정렬을 통한 칩 개수별 내림차순 출력
    for(i=0; i<playerCount-1; i++){
        for(j=i+1; j<playerCount; j++){
            PlayerInfo temp;
            if(ranking[i].chips<ranking[j].chips){
                temp = ranking[i];
                ranking[i] = ranking[j];
                ranking[j] = temp;
            }
        }
    }
    strcpy(sendbuf, "===============Ranking===============\n");
    for(i=0; i<playerCount; i++){
        snprintf(pagebuf, sizeof(pagebuf), "%d. %s / Chips : %d\n", i+1, ranking[i].name, ranking[i].chips);
        strcat(sendbuf, pagebuf);
    }
    strcat(sendbuf, "=====================================\n");
    strcat(sendbuf, number_msg);
}