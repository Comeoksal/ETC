public class Television implements Cloneable{ // Cloneable 인터페이스 구현
    int channel;    // 채널 번호
    int volume;     // 소리 크기
    boolean onoff;  // 전원 상태

    int getChannel(){ //channel 값 반환
        return channel;
    }
    void setChannel(int pChannel){ // channel 값 설정
        if((pChannel>=0) && (pChannel<=50)){ //채널 값이 0~50 사이일 때만 할당
            this.channel = pChannel;
        }
        else{ // 조건에 해당하지 않을 시 문자열 출력
            System.out.println("Channel cannot be changed to " + pChannel + " because out of range [1..50]");
        }
    }
    int getVolume(){//volume 값 반환
        return volume;
    }
    void setVolume(int pVolume){ // volume 값 설정
        if((pVolume>=0) && (pVolume<=100)){ //볼륨 값이 0~100 사이일 때만 할당
            this.volume = pVolume;
        }
        else{ // 조건에 해당하지 않을 시 문자열 출력
            System.out.println("Volume cannot be changed to" + pVolume + "because out of range [1..100]");
        }
    }
    boolean getOnoff(){ //onoff값 반환
        return onoff;
    }
    void setOnoff(boolean pOnoff){ // onoff값 설정
        this.onoff = pOnoff;
    }

    // Object 클래스의 clone() 메소드 오버라이딩 
    protected Object clone() throws CloneNotSupportedException{ 
        return super.clone(); //객체의 얕은 복사를 수행하는 Object 클래스의 clone 메소드를 반환값으로 호출
    }
}