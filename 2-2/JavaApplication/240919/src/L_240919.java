import java.util.*;
public class L_240919 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int num[] = new int[10]; //사용자로부터 10개의 정수를 입력받을 정수형 배열 선언
        int positive[] = new int[10]; // 양수를 넣을 정수형 배열 선언
        int negative[] = new int[10]; // 음수를 넣을 정수형 배열 선언

        double PositiveSum=0, NegativeSum=0; // 모든 양수, 모든 음수의 합을 저장할 변수 선언
        int PositiveCnt =0, NegativeCnt=0; // 양수, 음수의 갯수를 저장할 변수 선언
        double PositiveAverage=0, NegativeAverage=0; // 양수, 음수의 평균을 저장할 변수 선언
        double PVarianceSum=0, NVarianceSum=0; // 양수, 음수 분산을 구하기 위한 차이값 제곱의 합을 저장할 변수
        //(차이값 제곱의 합) / (양수값의 수) = 양수의 분산

        for(int i=0; i<10; i++){ //반복문 이용하여 사용자로부터 10개의 정수 입력 받기
            System.out.print((i+1)+".input number : ");
            num[i] = sc.nextInt(); // 정수를 입력 받을 때, 배열의 i번째 인덱스에 하나씩 저장
            if(num[i]>0){ //만약 입력값이 양수일 시
                PositiveSum += num[i]; //양수의 합에 입력값을 더하여 저장
                positive[PositiveCnt] = num[i]; // 양수를 넣을 배열의 (양수의 갯수)번째 인덱스에 해당 양수 저장
                PositiveCnt++; // 양수의 갯수 ++
            }
            // 음수에도 같은 조건문을 적용하여 else if문 작성
            else if(num[i]<0){
                NegativeSum += num[i];
                negative[NegativeCnt] = num[i];
                NegativeCnt++;
            }
        }
        System.out.print("reverse output : "); // 역출력
        for(int i=9; i>-1; i--){ // 배열의 끝 인덱스부터 0번(시작)인덱스 까지 순차적으로 출력
            System.out.print(num[i] + " ");
        }
        System.out.println("\n");
        // 양수, 음수의 갯수가 0개가 아닐 시에만 평균값을 구하여 출력
        if(PositiveCnt!=0){
            PositiveAverage = PositiveSum/PositiveCnt;
            System.out.println("PositiveAverage : " + PositiveAverage);
        }
        if(NegativeCnt!=0){
            NegativeAverage = NegativeSum/NegativeCnt;
            System.out.println("NegativeAverage : " + NegativeAverage);
        }
        //양수 음수의 갯수가 0개가 아닐 시에만 분산을 구하여 출력
        if(PositiveCnt!=0){
            //양수를 저장한 배열의 첫 인덱스(0)부터 값이 있는 인덱스(양수의 갯수)까지 반복
            for(int i=0; i<PositiveCnt; i++){ 
                //차이값(값-평균)의 제곱값을 더하여 변수에 저장
                PVarianceSum += (positive[i]-PositiveAverage)*(positive[i]-PositiveAverage);
            }
            System.out.println("Positive Variance : " + PVarianceSum/PositiveCnt); //차이값의 평균인 분산을 출력
        }
        // 음수에도 같은 방식을 적용하여 출력
        if(NegativeCnt!=0){
            for(int i=0; i<NegativeCnt; i++){
                NVarianceSum += (negative[i]-NegativeAverage)*(negative[i]-NegativeAverage);
            }
            System.out.println("Negative Variance : " + NVarianceSum/NegativeCnt);
        }
    }
}