#include "common.h"

void *send_msg(void *arg);
void *recv_msg(void *arg);

//서버와 반대로 보내는 버퍼 RECVLINE 사용, 받는 쪽이 MAXLINE 사용

int main(int argc, char* argv[]){
        int sock, nbyte;
        PlayerInfo myinfo;
        char buf[MAXLINE];
        pthread_t sendtid, recvtid;

        if(argc !=4){
                printf("usage: %s server_ip port name\n", argv[0]);
                exit(1);
        }

        sock = tcp_connect(AF_INET, argv[1], atoi(argv[2]));
        if(sock == -1){
                perror("tcp_connect error\n");
                exit(1);
        }

        write(sock, argv[3], strlen(argv[3]));
        if((nbyte = read(sock, buf, sizeof(buf))) > 0){
                buf[nbyte] = 0;
                printf("%s\n", buf);
        }

        pthread_create(&sendtid, NULL, send_msg, &sock);
        pthread_create(&recvtid, NULL, recv_msg, &sock);

        pthread_join(recvtid, NULL);
        pthread_cancel(sendtid);

        return 0;
}

void *send_msg(void *arg){
        int nbyte, sock = *((int *)arg);
        char sendbuf[RECVLINE];

        while((nbyte = read(0, sendbuf, sizeof(sendbuf)))> 0){
                sendbuf[nbyte]=0;
                write(sock, sendbuf, strlen(sendbuf));
        }
        return NULL;
}

void *recv_msg(void *arg){
        int nbyte, sock = *((int *)arg);
        char recvbuf[MAXLINE];

        while(1){
                nbyte = read(sock, recvbuf, MAXLINE);
                if(nbyte<=0){
                        puts("서버연결 끊어짐");
                        break;
                }
                recvbuf[nbyte] = 0;
                printf("%s\n", recvbuf);
        }
        return NULL;
}