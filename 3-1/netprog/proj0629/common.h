#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <unistd.h>
#include <pthread.h>
// 숫자 카드 랜덤 뽑기 위해 사용
#include <time.h>

//문자열 크기 매크로 값들
#define RECVLINE 256
#define PAGELINE 2048
#define MAXLINE 8192

//사용자 정보 구조체
typedef struct {
    int sock;
    char name[32];
    int chips;
        int channelNum;
    pthread_t main2_tid;
}PlayerInfo;

void errquit(const char *msg);
int tcp_connect(int af, const char *servip, unsigned short port);
int tcp_listen(int host, int port, int backlog);