import java.util.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String U_InputFile1 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red.jpg";
        String U_InputFile2 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(Smooth5N).jpg";
        String U_InputFile3 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(Sharpening).jpg";

        String U_OutputFile1 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(Smooth9N).jpg";
        String U_OutputFile2 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(Smooth5N).jpg";
        String U_OutputFile3 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(Sharpening).jpg";
        String U_OutputFile4 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(SharpeningToSmoothing).jpg";
        String U_OutputFile5 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(SmoothingToSharpening).jpg";
        //Smoothing9N GI1 = new Smoothing9N(U_InputFile1, U_OutputFile1);
        //Smoothing5N GI2 = new Smoothing5N(U_InputFile1, U_OutputFile2);
        //Sharpening GI3 = new Sharpening(U_InputFile1, U_OutputFile3);
        Smoothing5N GI4 = new Smoothing5N(U_InputFile3, U_OutputFile4);
        Sharpening GI5 = new Sharpening(U_InputFile2, U_OutputFile5);
    }
}
