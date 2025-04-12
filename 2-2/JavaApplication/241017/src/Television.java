public class Television{ 
    private int channel;    // 멤버 변수는 private으로 선언하여 외부에서 접근을 금지함
    private int volume;     
    protected boolean onoff; //외부 접근은 금지하면서, 자식 클래스가 사용할 수 있게 protected 선언

    // 생성자, 멤버 함수는 public으로 선언 (자료형 앞에 아무것도 안 쓸시 public 선언)
    public Television(){ // 생성자 #1 - 매개변수가 없는 생성자
        channel = 10;
        volume = 20;
        onoff = true;
    }
    public Television(int pChannel, int pVolume, boolean pOnoff){ // 생성자 #2 - 매개변수가 있는 생성자
        channel = pChannel;
        volume = pVolume;
        onoff = pOnoff;
    }
    //클래스명을 출력하는 메소드
    void PrintClassName(){
        System.out.println("Class Name = Television");
    }
    int getChannel(){
        return channel;
    }
    void setChannel(int pChannel){
        if((pChannel>=0) && (pChannel<=50)){
            this.channel = pChannel;
        }
        else{
            System.out.println("Channel cannot be changed to " + pChannel + " because out of range [1..50]");
        }
    }
    int getVolume(){
        return volume;
    }
    void setVolume(int pVolume){
        if((pVolume>=0) && (pVolume<=100)){
            this.volume = pVolume;
        }
        else{
            System.out.println("Volume cannot be changed to" + pVolume + "because out of range [1..100]");
        }
    }
    boolean getOnoff(){
        return onoff;
    }
    void setOnoff(boolean pOnoff){
        this.onoff = pOnoff;
    }
}