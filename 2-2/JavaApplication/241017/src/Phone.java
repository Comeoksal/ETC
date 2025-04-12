public class Phone {
    private int number; // 휴대폰 번호 (초기값 0)
    private String name; // 휴대폰 이름
    
    Phone(){ //Phone 클래스의 기본 생성자
        number = 0;
        name = "Phone";
    }
    Phone(int pNumber, String pName){ //Phone 클래스의 매개변수가 있는 생성자
        if(pNumber>=0){ //번호가 0 이상일 시 할당
            number = pNumber;
        }
        else{ //조건에 부합하지 않으면 문장 출력 후 번호를 0으로 할당
            System.out.println("PhoneNumber cannot be changed to " + pNumber + " because out of range [<0]\n");
            number = 0;
        }
        name =pName;
    }
    void PrintClassName(){
        System.out.println("Class Name is Phone");
    }
    int getNumber(){
        return number;
    }
    void setNumber(int pNumber){
        if(pNumber>=0){
            number = pNumber;
        }
        else{
            System.out.println("PhoneNumber cannot be changed to " + pNumber + " because out of range [<0]\n");
        }
    }
    String getName(){
        return name;
    }
    void setName(String pName){
        name = pName;
    }
}