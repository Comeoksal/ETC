public class Phone {
    private int number; //휴대폰 번호 8자리를 가지는 정수형 변수ex)23453417
    private String name; //휴대폰 이름을 가지는 String 변수
    
    int getNumber(){ //휴대폰 번호 반환
        return number;
    }
    void setNumber(int pNumber){ //휴대폰 번호 설정
        if(pNumber>=0){ //휴대폰 번호가 0보다 클 때만 설정
            number = pNumber;
        }
        else{ //조건에 해당하지 않을 시 출력
            System.out.println("PhoneNumber cannot be changed to " + pNumber + " because out of range [<0]\n");
        }
    }
    String getName(){ //휴대폰 이름 반환
        return name;
    }
    void setName(String pName){ // 휴대폰 이름 설정
        name = pName;
    }
}