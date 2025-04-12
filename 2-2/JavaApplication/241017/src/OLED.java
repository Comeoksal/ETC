// 상속 - C에서는 구현할 수 없는 객체지향 언어(자바)의 특징
public class OLED extends Television{ // extends를 사용하여 Television(부모 클래스)의 내용을 확장 시킴
    private int USB;
    private int HDMI;
    private boolean XBOX;

    //생성자 #1 - 매개변수가 없는 생성자
    public OLED(){
        super(); // 부모클래스의 기본 생성자(매개변수가 없는)를 그대로 호출
        USB = 2;
        HDMI = 3;
        XBOX = true;
    }
    //생성자 #2 - 매개변수가 있는 생성자
    public OLED(int pChannel, int pVolume, boolean pOnoff, int pUSB, int pHDMI, boolean pXBOX){
        super(pChannel, pVolume, pOnoff); // 부모클래스의 매개변수가 있는 생성자 호출
        USB = pUSB;
        HDMI = pHDMI;
        XBOX = pXBOX;
    }
    @Override //메소드 오버라이딩
    void PrintClassName(){
        System.out.println("Class Name = OLED");
    }
    int GetUSB(){ 
        return USB;
    }
    void SetUSB(int pUSB){ //지난 주처럼 if문 사용하여 특정한 값만 입력되도록 하기
        USB = pUSB;
    }
    int GetHDMI(){ 
        return HDMI;
    }
    void SetHDMI(int pHDMI){ //지난 주처럼 if문 사용하여 특정한 값만 입력되도록 하기
        HDMI = pHDMI;
    }
    boolean GetXBOX(){ 
        return XBOX;
    }
    void SetXBOX(boolean pXBOX){ //지난 주처럼 if문 사용하여 특정한 값만 입력되도록 하기
        XBOX = pXBOX;
    }
}