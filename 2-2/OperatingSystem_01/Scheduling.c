#include <stdio.h>
#include <stdlib.h>
#include "LinkedQueue.h"

int main() {
	LQueueType* Q1 = createLinkedQueue(); //연결 큐 생성

	QNode* N1 = createQNode(10, (rand() % 5 + 1)); //노드 10개 생성 createQNode(<프로세스ID값>, <1~5사이의 repeat값>);
	QNode* N2 = createQNode(20, (rand() % 5 + 1));
	QNode* N3 = createQNode(30, (rand() % 5 + 1));
	QNode* N4 = createQNode(40, (rand() % 5 + 1));
	QNode* N5 = createQNode(50, (rand() % 5 + 1));
	QNode* N6 = createQNode(60, (rand() % 5 + 1));
	QNode* N7 = createQNode(70, (rand() % 5 + 1));
	QNode* N8 = createQNode(80, (rand() % 5 + 1));
	QNode* N9 = createQNode(90, (rand() % 5 + 1));
	QNode* N10 = createQNode(100, (rand() % 5 + 1));

	insert(Q1, N1); //생성된 10개의 프로세스를 큐에 삽입
	insert(Q1, N2);
	insert(Q1, N3);
	insert(Q1, N4);
	insert(Q1, N5);
	insert(Q1, N6);
	insert(Q1, N7);
	insert(Q1, N8);
	insert(Q1, N9);
	insert(Q1, N10);

	printf("==>");  printLQ(Q1); printf("\n"); //현재 큐 상태를 출력하여 어떤 프로세스를 먼저 수행할 지 체크
	while (!isLQEmpty(Q1)) { //큐가 공백 상태가 될 때까지 반복
		int num = delete(Q1); //수행할 프로세스를 큐에서 선택하여 해당 프로세스 ID를 int 변수 num에 저장
		printf("######프로세스 선택 수행###### ");
		printf("-> 선택된 프로세스 ID : %d\n\n", num); //수행할 프로세스로 선택된 프로세스의 ID : num
		printf("==>");  printLQ(Q1); printf("\n"); //현재 큐 상태를 출력
	}
	getchar(); return 0;
}