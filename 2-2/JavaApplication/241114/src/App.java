import java.util.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String U_InputFile = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\min.jpg";
        
        String U_OutputFile1 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\min(Gray).jpg";
        String U_OutputFile2 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\min(Gray2).jpg";
        // String U_OutputFile3 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(Gray3).jpg";
        // String U_OutputFile4 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(DivideToGray123).jpg";
        // String U_OutputFile5 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(BrighterByDifference).jpg";
        // String U_OutputFile6 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(DarkerByDifference).jpg";
        // String U_OutputFile7 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(DivideToBrDaDi).jpg";

        Gray1 GI1 = new Gray1(U_InputFile, U_OutputFile1);
        Gray2 GI2 = new Gray2(U_InputFile, U_OutputFile2);
        // Gray3 GI3 = new Gray3(U_InputFile, U_OutputFile3);
        // DivideToGray123 GI4 = new DivideToGray123(U_InputFile, U_OutputFile4);
        // BrighterByDifference GI5 = new BrighterByDifference(U_InputFile, U_OutputFile5);
        // DarkerByDifference GI6 = new DarkerByDifference(U_InputFile, U_OutputFile6);
        // DivideToBrDaDi GI7 = new DivideToBrDaDi(U_InputFile, U_OutputFile7);
    }
}
