     import java.util.*;

    public class L_240905 {
    public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    int PositiveCount = 0, NegativeCount=0; // 양수와 음수의 갯수를 셀 정수형 변수 선언, 정의
    int PositiveSum = 0, NegativeSum = 0; // 모든 양수의 합, 모든 음수의 합을 저장할 정수형 변수 선언, 정의
    double PositiveAvg = 0, NegativeAvg = 0; // 양수와 음수의 평균값을 저장할 실수형 변수 선언, 정의

    int x; // 사용자로부터 값을 입력 받을 정수형 변수 x 선언

    x = sc.nextInt();
    if(x > 0) { // 만약 x가 양수일시
        PositiveCount++; // 양수 count++;(count=count+1;)
        PositiveSum += x; // 양수의 합에 x 값을 더하여 저장 (PositiveSum = PoritiveSum + x;)
    }
    else if(x < 0){ // 만약 x가 음수일시
        NegativeCount++; // 음수 count++;
        NegativeSum += x; // 음수의 합에 x값을 더하여 저장
    }
    // 사용자로부터 5개의 정수들을 입력 받아야 하므로 위 조건문 4회 더 반복
		x = sc.nextInt();
    if(x > 0) {
        PositiveCount++;
        PositiveSum += x;
    }
    else if(x < 0){
        NegativeCount++;
        NegativeSum += x;
    }
    
    x = sc.nextInt();
    if(x > 0) {
        PositiveCount++;
        PositiveSum += x;
    }
    else if(x < 0){
        NegativeCount++;
        NegativeSum += x;
    }
    
    x = sc.nextInt();
    if(x > 0) {
        PositiveCount++;
        PositiveSum += x;
    }
    else if(x < 0){
        NegativeCount++;
        NegativeSum += x;
    }
    
    x = sc.nextInt();
    if(x > 0) {
        PositiveCount++;
        PositiveSum += x;
    }
    else if(x < 0){
        NegativeCount++;
        NegativeSum += x;
    }


    System.out.println("양수들의 개수 = "+PositiveCount); // 양수의 개수 출력
    if(PositiveCount > 0) { // 양수가 0개일시 평균을 구할 수 없으므로 
        PositiveAvg = PositiveSum / (double)PositiveCount; // 개수가 0 이상일 때 평균값 구하기
        System.out.println("양수들의 평균 = " +PositiveAvg);
    }
    else{ // 양수의 개수가 0개 이상이 아닐시 다음 문장 출력
	    System.out.println("양수들의 평균은 구할 수 없습니다.");
	}
    // 음수도 같은 방식의 조건문 사용하여 평균 구하기
    System.out.println("음수들의 개수 = " + NegativeCount);
    if(NegativeCount > 0){
        NegativeAvg = NegativeSum / (double)NegativeCount;
        System.out.println("음수들의 평균 = "+NegativeAvg);
    }
    else{
	    System.out.println("음수들의 평균은 구할 수 없습니다.");
	   }

}
}