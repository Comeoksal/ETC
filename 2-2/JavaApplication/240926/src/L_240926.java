import java.util.*;

public class L_240926 {
    public static void main(String[] args) throws Exception {
       Scanner sc = new Scanner(System.in);
       int scoreSt[][] = new int[4][3]; // 학생들의 점수를 저장할 2차원 배열 선언
       double valueSt[][] = new double[4][3]; // 학생들의 평균, 분산1, 분산2를 저장할 2차원 배열 선언
       double valueSu[][] = new double[3][3]; // 과목들의 평균, 분산1, 분산2를 저장할 2차원 배열 선언
       double stSum =0, suSum=0; // 한 학생의 과목 점수 합, 한 과목의 학생 점수 합을 저장할 변수
       double stVar1 = 0, stVar2 =0, suVar1 =0, suVar2=0; //두 방식의 분산을 구하기 위한 변수
       //한 학생 차이값 제곱의 합을 저장할 변수1, 학생 값들의 제곱의 합을 저장할 변수2(과목도 마찬가지로 2개)
        //차이값 제곱의 합 / 과목 수 = 분산1, 제곱의 합 - 평균의 제곱 = 분산2

       for(int i=0; i<4; i++){ //학생 4명에게 각 과목의 점수를 입력 받는 반복문
            System.out.print((i+1)+"번 학생 성적 입력 (국어, 영어, 수학) : ");
            for(int j=0; j<3; j++){
                int score = sc.nextInt();
                scoreSt[i][j] = score; // 학생 점수 배열의 각 인덱스에 저장
            }
        }

        //각 학생에 대해 과목들의 평균, 분산1, 분산2 를 구하는 코드
        for(int i=0; i<4; i++){ //평균 구하기
            for(int j=0; j<3; j++){
                stSum += scoreSt[i][j]; // 한 학생씩 점수 배열을 하나씩 접근하여 학생 점수의 합에 더하여 저장
            }
            valueSt[i][0] = stSum/3; //한 학생의 평균위치에 평균값 저장
            stSum=0; // 다음 학생의 평균을 구해야하므로 0으로 초기화
        }
        
        for(int i=0; i<4; i++){ // 분산1, 2 구하기
            for(int j=0; j<3; j++){ 
                stVar1 += (scoreSt[i][j] - valueSt[i][0])*(scoreSt[i][j] - valueSt[i][0]); //차이값 제곱의 합을 저장
                stVar2 += scoreSt[i][j]*scoreSt[i][j]; // 각 점수 값들의 제곱의 합을 저장
            }
            valueSt[i][1] = stVar1/3; // 한 학생의 분산1 위치에 분산1값 저장
            stVar1 = 0; // 다음 학생의 분산1을 구해야하므로 0으로 초기화
            valueSt[i][2] = (stVar2 / 3) - (valueSt[i][0]*valueSt[i][0]); // 한 학생의 분산2 위치에 분산2값 저장
            stVar2 = 0;// 다음 학생의 분산2를 구해야하므로 0으로 초기화
        }

        //각 과목에 대해 학생들의 평균, 분산1, 분산2 를 구하는 코드 (방식은 각 학생에 대한 값 구하기와 같음)
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                suSum += scoreSt[j][i];
            }
            valueSu[i][0] = suSum / 4;
            suSum = 0;
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                suVar1 +=(scoreSt[j][i] - valueSu[i][0])*(scoreSt[j][i] - valueSu[i][0]);
                suVar2 +=scoreSt[j][i]*scoreSt[j][i];
            }
            valueSu[i][1] = suVar1/4;
            valueSu[i][2] = (suVar2/4) - (valueSu[i][0]*valueSu[i][0]);
            suVar1=0;
            suVar2=0;
        }
    
    //출력
    System.out.println("\t  국어  영어   수학     평균    분산1   분산2");
    //[4][3]으로 이루어진 점수 배열(scoreSt)3열과 각 학생에 대하여 평균,분산1,분산2를
    //저장한 배열(valueSt)3열을 총 6열씩 4행 출력 (출력 예시의 흰색, 빨간색 부분)
    for(int i=0; i<4; i++){ 
        System.out.print("학생"+(i+1)+"\t  "); 
        for(int j=0; j<3; j++){
            System.out.printf("%d\t", scoreSt[i][j]);
        }
        for(int j=0; j<3; j++){
            System.out.printf("%.2f\t", valueSt[i][j]);
        }
        System.out.println("\n");
    }
    
    //[3][3]으로 이루어진 각 과목에 대하여 평균, 분산1, 분산2를
    //저장한 배열(valueSu)을 3열씩 3행 출력 (출력 예시의 파란색 부분)
    System.out.print("평균 :\t ");
    for(int i=0; i<3; i++){
        System.out.printf("%.2f\t", valueSu[i][0]);
    }
    System.out.println("\n");
    System.out.print("분산1 :\t ");
    for(int i=0; i<3; i++){
        System.out.printf("%.2f\t", valueSu[i][1]);
    }
    System.out.println("\n");
    System.out.print("분산2 :\t ");
    for(int i=0; i<3; i++){
        System.out.printf("%.2f\t", valueSu[i][2]);
    }
}
}
