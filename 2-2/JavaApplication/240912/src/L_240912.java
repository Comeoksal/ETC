import java.util.*;

class L_240912 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int x; // 사용자로부터 입력받을 정수형 변수 x 선언
		int PositiveCount = 0, NegativeCount =0; //양수와 음수 갯수 저장할 변수
		double PositiveSum =0, NegativeSum=0; // 양수와 음수의 합 저장할 변수

		int PositiveCount2 = 0, NegativeCount2 =0; //양의 짝수, 음의 짝수 갯수 저장할 변수
		double PositiveSum2=0, NegativeSum2=0; // 양의 짝수의 합, 음의 짝수의 합 저장할 변수

		int PositiveCount3 = 0, NegativeCount3 =0; //양의 홀수, 음의 홀수 갯수 저장할 변수
		double PositiveSum3=0, NegativeSum3=0; //양의 홀수, 음의 홀수 갯수 저장할 변수
		//2는 짝수변수 3은 홀수변수
		
		for(int i=0; i<10; i++) { // for (int i=0;(초기값) i<10;(i가 10보다 작을시 반복 수행) i++(i값 1씩 증가))
			System.out.print((i+1)+".input number : ");
			x = sc.nextInt(); // 사용자로부터 정수 입력 받기
			if(x>0) { //x가 양수일 시
				PositiveSum +=x; // 양수의 합에 x값 더하여 저장
				PositiveCount++; // 양수의 갯수 ++
				if(x%2==0) { //x가 짝수일 시
					PositiveSum2+=x; //양의 짝수의 합에 x값 더하여 저장
					PositiveCount2++; // 양의 짝수의 갯수 ++
				}
				else { // x가 짝수가 아닐 시(홀수일 때)
					PositiveSum3+=x; // 양의 홀수의 합에 x값 더하여 저장
					PositiveCount3++; // 양의 홀수의 갯수 ++
				}
			}
			//x가 음수일 때도 같은 조건 적용
			else if(x<0) {
				NegativeSum +=x;
				NegativeCount++;
				if(x%2==0) {
					NegativeSum2+=x;
					NegativeCount2++;
				}
				else {
					NegativeSum3+=x;
					NegativeCount3++;
				}
			}
		}
		//각 갯수가 0이면 평균을 구할 수 없으므로 0이 아닐 때만 평균을 구하여 출력
		if(PositiveCount!=0) {
			System.out.println("양수들의 평균 : " + PositiveSum/PositiveCount);
		}
		if(NegativeCount!=0) {
			System.out.println("음수들의 평균 : " + NegativeSum/NegativeCount);
		}
		if(PositiveCount2!=0) {
			System.out.println("양의 짝수들의 평균 : " + PositiveSum2/PositiveCount2);
		}
		if(PositiveCount3!=0) {
			System.out.println("양의 홀수들의 평균 : " + PositiveSum3/PositiveCount3);
		}
		if(NegativeCount2!=0) {
			System.out.println("음의 짝수들의 평균 : " + NegativeSum2/NegativeCount2);
		}
		if(NegativeCount3!=0) {
			System.out.println("음의 홀수들의 평균 : " + NegativeSum3/NegativeCount3);
		}
	}
}

