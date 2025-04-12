public class SmartPhone extends Phone {
    private int memory; // 스마트폰의 메모리 용량
    private int giga; //스마트폰의 인터넷 속도

    SmartPhone(){ // 스마트폰의 기본 생성자
        super(); // 상위 클래스인 휴대폰의 기본 생성자 호출
        memory = 64;
        giga = 4;
    }
    SmartPhone(int pNumber, String pName, int pMemory, int pGiga){ // 스마트폰의 매개변수가 있는 생성자
        super(pNumber, pName); // 상위 클래스인 휴대폰의 매개변수가 있는 생성자 호출 
        if(pMemory>=0){ //메모리가 0보다 클시 할당
            memory = pMemory;
        }
        else{ //조건에 부합하지 않으면 문장 출력 후 메모리 0으로 초기화
            System.out.print("Memory cannot be changed to " + pMemory + " because out of range [<0] Memory setted : 64\n");
            memory = 0;
        }
        if(pGiga>=1&&pGiga<=5){ // 인터넷 속도가1~5G 일시 할당
            giga = pGiga;
        }
        else{ //조건에 부합하지 않으면 1G 속도로 할당
            System.out.print("Giga cannot be changed to " + pGiga + " because out of range [1~5] Giga setted : 1\n");
            giga = 1;
        }
    }
    @Override
    void PrintClassName(){
        System.out.println("Class Name is SmartPhone");
    }
    int getMemory(){
        return memory;
    }
    void setMemory(int pMemory){
        if(pMemory>=0){
            memory = pMemory;
        }
        else{
            System.out.println("Memory cannot be changed to " + pMemory + " because out of range [<0]\n");
        }
    }
    int getGiga(){
        return giga;
    }
    void setGiga(int pGiga){
        if(pGiga>=1&&pGiga<=5){
            giga = pGiga;
        }
        else{
            System.out.println("Giga cannot be changed to " + pGiga + " because out of range [1~5]\n");
        }
    }
}
