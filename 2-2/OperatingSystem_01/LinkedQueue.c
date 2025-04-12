#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include "LinkedQueue.h"

LQueueType* createLinkedQueue(void) { // 연결 큐 생성
	LQueueType* LQ = (LQueueType*)malloc(sizeof(LQueueType)); // LQueueType 구조체 크기만큼 메모리 동적할당
	LQ->head = NULL; 
	LQ->tail = NULL; 
	return LQ; //생성된 연결 큐 반환
}
QNode* createQNode(element id, element r) { // 연결 큐에 삽입 할 프로세스 노드 생성 id = 프로세스 ID, r = 반복 횟수값
	QNode* newNode = (QNode*)malloc(sizeof(QNode)); // QNode (프로세스 노드) 구조체 크기만큼 메모리 동적할당
	newNode->id = id;
	newNode->repeat = r;
	newNode->link = NULL; // 생성 과정이므로 연결된 link 없음
	return newNode;
}
int isLQEmpty(LQueueType* LQ) { // 연결 큐가 공백일 때 1을 return, 공백이 아닐 땐 0을 return 하는 함수
	if (LQ->head == NULL) {
		return 1;
	}
	else return 0;
}
void insert(LQueueType* LQ, QNode* Q) { // 연결 큐에 프로세스 노드를 삽입하는 함수 LQ = 노드를 삽입 할 연결 큐, Q = 삽입 할 프로세스 노드
	QNode* node = Q; // 삽입 할 노드 = node
	if (LQ->head == NULL) { // 연결 큐가 공백일 시
		LQ->head = node;
		LQ->tail = node; 
	}
	else { // 연결 큐가 공백이 아닐 시
		LQ->tail->link = node; 
		LQ->tail = node; 
		node->link = NULL; //tail 노드는 node 이므로 연결된 link 없음(NULL)
	}
}
element delete(LQueueType* LQ) {
	QNode* selected = LQ->head; // selected는 선택된 프로세스
	element num;
	num = selected->id;
	selected->repeat--; // 해당 프로세스는 선택 되었으므로 반복 횟수값 - 1
	LQ->head = LQ->head->link; // 프로세스를 하나 선택하였으므로 head노드를 기존의 head노드에 link된 노드로 설정 ★
	if (LQ->head == NULL) {
		LQ->tail = NULL;
	}
	if (selected->repeat != 0) { // 반복 횟수가 0이 아닐시
		insert(LQ, selected); // 해당 노드를 다시 연결큐에 삽입
		return num;
	}
	else {// 반복 횟수가 0일시
		free(selected); // 해당 노드 구조체의 메모리 해제 후 재삽입 과정 x
		return num;
	}
}
void printLQ(LQueueType* LQ) {
	QNode* temp = LQ->head;
	printf(" 현재 큐 상태 : [");
	while (temp) { // 연결 큐에 존재하는 노드를 계속 link를 통해 방문하며 NULL이 나올 때까지
		printf(" %3d(%d)", temp->id, temp->repeat);
		temp = temp->link;
	}
	printf(" ] ");
}