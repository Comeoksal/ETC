import java.util.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String U_InputFile = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\scenery(Gray).png";
        String U_InputFile2 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\scenery.png";

        String U_OutputFile = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\scenery(Edge_Detection20).png";
        String U_OutputFile1 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\scenery(Edge_diffenrence).png";
        String U_OutputFile2 = "C:\\Users\\금시언\\Desktop\\2-2\\자바어플리케이션\\scenery(Edge_cover).png";

        EdgeDetection GI1 = new EdgeDetection(U_InputFile, U_OutputFile, 20);
        EdgeDifference GI2 = new EdgeDifference(U_InputFile2, U_OutputFile1, 10);
        EdgeCover GI3 = new EdgeCover(U_InputFile2, U_OutputFile2, 20);
    }
}
