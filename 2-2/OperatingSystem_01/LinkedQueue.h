#pragma once
typedef int element;

typedef struct QNode { //프로세스에 해당하는 노드 구조체
	element id; //프로세스 ID값을 나타내는 변수 (10~100)
	element repeat;  //프로세스의 반복 수를 나타내는 변수 (1~5사이의 정수)
	struct QNode* link; //해당 프로세스로부터 다음 프로세스를 가리키는 link 포인터
}QNode;

typedef struct { //연결큐에서 먼저 수행될 프로세스를 가리키는 포인터와, 마지막 프로세스를 가리키는 포인터를 가진 구조체
	QNode* head, * tail; //먼저 수행될 프로세스를 가리키는 head 포인터, 마지막(꼬리) 프로세스를 가리키는 tail 포인터
}LQueueType;

LQueueType* createLinkedQueue(void); // 연결 큐를 생성하는 함수 선언
QNode* createQNode(element id, element r); // 연결 큐에 들어갈 프로세스 노드를 생성하는 함수 선언
int isLQEmpty(LQueueType* LQ); // 연결큐가 공백일 때를 나타내는 함수 선언
void insert(LQueueType* LQ, QNode*Q); // 연결 큐에 프로세스 노드를 삽입하는 함수 선언
element delete(LQueueType* LQ); // 연결 큐에서 프로세스를 선택하여 수행하는 함수 선언
void printLQ(LQueueType* LQ); // 연결 큐의 현재 상태를 나타내는 함수 선언

