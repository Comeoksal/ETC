import java.util.*;

public class L_241017 {
    public static void main(String[] args) throws Exception{
        Television SStv = new Television(); //아래 초기값(주석)을 주지 않아도 생성자에 의해서 멤버 변수의 값이 초기화 됨
        Television LGtv = new Television(15, 25, false);
        OLED HDtv = new OLED();
        OLED SKtv = new OLED(17, 27, false, 4, 5, true);
        /*SStv.setChannel(10);
        SStv.setVolume(20);
        SStv.setOnoff(false);*/

        SStv.PrintClassName();
        System.out.println("SStv :");
        System.out.println("Channel Number : " + SStv.getChannel());
        System.out.println("Volume : " + SStv.getVolume() + "\n");

        LGtv.PrintClassName();
        System.out.println("LGtv :");
        System.out.println("Channel Number : " + LGtv.getChannel());
        System.out.println("Volume : " + LGtv.getVolume()+"\n");

        HDtv.PrintClassName();
        System.out.println("HDtv :");
        System.out.println("Channel Number : " + HDtv.getChannel());
        System.out.println("Volume : " + HDtv.getVolume());
        System.out.println("USB : " + HDtv.GetUSB());
        System.out.println("HDMI : " + HDtv.GetHDMI()+"\n");

        SKtv.PrintClassName();
        System.out.println("SKtv :");
        System.out.println("Channel Number : " + SKtv.getChannel());
        System.out.println("Volume : " + SKtv.getVolume());
        System.out.println("USB : " + SKtv.GetUSB());
        System.out.println("HDMI : " + SKtv.GetHDMI()+"\n");

        Phone SSphone = new Phone(45595310, "S21");
        SSphone.PrintClassName();

        System.out.println("SSPhone :");
        System.out.println("Phone Number : " + SSphone.getNumber());
        System.out.println("Phone Name : " + SSphone.getName() + "\n");
        
        SmartPhone Iphone = new SmartPhone(45595310, "MAX9", 256, 6);
        Iphone.PrintClassName();
        
        System.out.println("IPhone :");
        System.out.println("Phone Number : " + Iphone.getNumber());
        System.out.println("Phone Name : " + Iphone.getName());
        System.out.println("Phone Memory : " + Iphone.getMemory());
        System.out.println("Phone Giga : " + Iphone.getGiga() + "G");
    }
}
