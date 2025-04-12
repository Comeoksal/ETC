import java.util.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String U_InputFile = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red.jpg";
        String U_OutputFile1 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(FlipHorizontal).jpg";
        String U_OutputFile2 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(FlipVertical).jpg";
        String U_OutputFile3 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\red(FlipPoint).jpg";
        FlipHorizontalImage GI1 = new FlipHorizontalImage(U_InputFile, U_OutputFile1);
        FlipVerticalImage GI2 = new FlipVerticalImage(U_InputFile, U_OutputFile2);
        FlipPointImage GI3 = new FlipPointImage(U_InputFile, U_OutputFile3);
    }
}
