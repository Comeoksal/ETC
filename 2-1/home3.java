package homework1;

import java.util.ArrayList;
import java.util.Scanner;

interface SoundMaker{
	void sound();
}

abstract class Animal implements SoundMaker{ //interface 구현
	private int weight;
	public Animal(int w) {
		this.weight = w;
	}
	@Override 
	public String toString() { //Obj 클래스의 toString 메소드를 오버라이딩
		return ("weight = " + this.weight);
	}
}

class Dog extends Animal{
	public Dog(int w) {
		super(w); //super() 를 통해서 슈퍼클래스의 생성자 불러오기
	}
	@Override
	public void sound() {
		System.out.println("Bow-wow");
	}
	@Override
	public String toString() { 
		return ("Dog, " + super.toString());
	}
}
class Cat extends Animal{
	public Cat(int w) {
		super(w);
	}
	@Override
	public void sound() {
		System.out.println("Meow");
	}
	public String toString() {
		return ("Cat, " + super.toString());
	}
}
class home3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Animal> a = new ArrayList<Animal>();
		
		int num=0, target, weight;
		while(num!=5) {
		System.out.println("1.Add, 2.Remove, 3.Show all, 4.Make them sound, 5.Exit");
		System.out.print("Choose a number : ");
		num = sc.nextInt();
		switch(num) {
		case 1 : 
			System.out.println("1.Dog, 2.Cat");
			System.out.print("Choose a number : ");
			target = sc.nextInt();
			System.out.print("Enter weight : ");
				if(target==1) { 
						weight = sc.nextInt();
						a.add(new Dog(weight));
					}
				else if (target==2) {
					weight = sc.nextInt();
					Animal c = new Cat(weight);
					a.add(c); // == a.add(new Cat(weight)); ★★★
				}
			break;
		case 2: 
			System.out.print("Enter an index to remove (0~" +(a.size()-1)+") : "); //a.size() 는 a ArrrayList의 요소 개수
			int r = sc.nextInt();
			a.remove(r); //a.remove() 는 r위치 인덱스의 요소 삭제
			break;
		case 3:
			for(int i=0; i<a.size(); i++) {
				System.out.println(a.get(i).toString()); //a.get(i)을 통해서 a ArrayList의 i위치 인덱스의 요소(객체) 획득-> 그것의 .toString
			}
			break;
		case 4:
			for(Animal as : a) { // ★★★ 0번째 인덱스 부터 순서대로 하는 것이기에 가능
				as.sound();
			} 
			break;
		case 5:
			break;
		}
		}
		}
}