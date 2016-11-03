import java.util.*;
import components.*;

class Test {

    public static void testCase(int caseNo, String filePath) {
        Parser p = new Parser(filePath);
        // System.out.println(p);
        TwoSat t = new TwoSat();
        final long startTime = System.currentTimeMillis();
        try {
            t = new TwoSat(p);
        } catch (InvalidInputException e) {
            System.out.println("Program ended due to invalid input file!");
        } finally {
            System.out.println(t.solve());
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time of case " + caseNo + ": " + (endTime - startTime) );
        }
    }

    public static void main(String[] args) {
        testCase(0, "largeSat.cnf");
    }
}
