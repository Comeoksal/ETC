import java.util.*;

public class L_241010 {
    public static void main(String[] args) throws Exception, CloneNotSupportedException { // ★
        Television SStv = new Television(); // Television 클래스의 SStv 객체 생성, new로 메모리 할당
        Television HDtv; // Television 클래스의 HDtv 객체 생성

        SStv.setChannel(10); // 채널 값 10으로 설정
        SStv.setVolume(20); // 볼륨 값 20으로 설정
        SStv.setOnoff(false); // onoff값 false로 설정

        System.out.println("SStv :");
        System.out.println("Channel Number : " + SStv.getChannel());
        System.out.println("Volume : " + SStv.getVolume());
        
        HDtv = SStv; //Cloneable 인터페이스가 구현돼있으므로 얕은 복사 시행
        System.out.println();
        System.out.println("HDtv :");
        System.out.println("Channel Number : " + HDtv.getChannel());
        System.out.println("Volume : " + HDtv.getVolume());

        HDtv.setChannel(35); 
        HDtv.setVolume(45);
        System.out.println("After modification"); //값 변경 후 출력 
        System.out.println("HDtv :");
        System.out.println("Channel Number : " + HDtv.getChannel());
        System.out.println("Volume : " + HDtv.getVolume());
        //Cloneable 인터페이스 구현 안 할시 문제 발생 -> 객체는 값을 넘겨주는 것이 아닌 참조값(주소)를 넘겨주기 때문 ★

        Phone SSphone = new Phone(); //SSphone 객체 생성, 메모리 할당
        SSphone.setNumber(45595310); //초기값 설정
        SSphone.setName("GalaxyS21");

        Phone Iphone = new Phone();
        Iphone.setNumber(-72381273); //휴대폰 번호를 음수로 설정하여 설정 안 되게 해보기
        Iphone.setName("IPhone9Pro");

        System.out.println("SSPhone :");
        System.out.println("Phone Number : " + SSphone.getNumber());
        System.out.println("Phone Name : " + SSphone.getName());
        
        System.out.println();
        System.out.println("IPhone :");
        System.out.println("Phone Number : " + Iphone.getNumber());
        System.out.println("Phone Name : " + Iphone.getName());

    }
}
