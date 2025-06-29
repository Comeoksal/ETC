#define MAX_PLAYER 100
#define MAX_CHANNEL 2
#define MAX_GAMEPLAYER 2


//게임 채널 구조체
typedef struct {
    PlayerInfo *player1;
    PlayerInfo *player2;
    int isFull;
    int channelNum;
}GameChannel;

//게임 상태(진행)  구조체
typedef struct {
    PlayerInfo *player1;
    PlayerInfo *player2;
    int turn;
    int card[20];
    int player_card[2][2];
    int player_bet[2];
    int pot;
    int checkResult;
    int flag;
}GameState;