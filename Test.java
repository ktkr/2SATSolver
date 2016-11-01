import java.util.*;
import components.Parser;
import components.TwoSat;
import components.InvalidInputException;

class Test {

    public static void testCase(String filePath) {
        Parser p = new Parser(filePath);
        System.out.println(p);
        try {
            TwoSat t = new TwoSat(p);
        } catch (InvalidInputException e) {
            System.out.println("Program ended due to invalid input file!");
        }
    }

    public static void main(String[] args) {
        testCase("file.txt");
    }
}
