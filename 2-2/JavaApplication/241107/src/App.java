import java.util.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String U_InputFile = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red.jpg";
        String U_InputFile2 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(DivideToIRGB).jpg";

        //String U_OutputFile1 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(ColorChangeToRed).jpg";
        //String U_OutputFile2 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(ColorChangeToGreen).jpg";
        //String U_OutputFile3 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(ColorChangeToBlue).jpg";
        //String U_OutputFile4 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(DivideToIRGB).jpg";
        String U_OutputFile5 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(Brighter).jpg";
        String U_OutputFile6 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(Darker).jpg";

        //ColorChangeToRed GI1 = new ColorChangeToRed(U_InputFile, U_OutputFile1);
        //ColorChangeToGreen GI2 = new ColorChangeToGreen(U_InputFile, U_OutputFile2);
        //ColorChangeToBlue GI3 = new ColorChangeToBlue(U_InputFile, U_OutputFile3);
        //DivideToIRGB GI4 = new DivideToIRGB(U_InputFile, U_OutputFile4);
        Brighter GI5 = new Brighter(U_InputFile2, U_OutputFile5, 50);
        Darker GI6 = new Darker(U_InputFile2, U_OutputFile6, 50);
    }
}
