package src.sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.sat.env.*;
import src.sat.formula.*;


public class SATSolverTest {
    final static Literal a = PosLiteral.make("a");
    final static Literal b = PosLiteral.make("b");
    final static Literal c = PosLiteral.make("c");
    final static Literal na = a.getNegation();
    final static Literal nb = b.getNegation();
    final static Literal nc = c.getNegation();

    public static void main(String[] args) {
        //String path = "C:\\Users\\Dominic\\Desktop\\Project-2D\\Project-2D-starting\\sampleCNF\\s8Sat.cnf";
        //String path = "C:\\Users\\Dominic\\Desktop\\Project-2D\\Project-2D-starting\\sampleCNF\\largeUnsat.cnf";
        String path = "C:\\Users\\Dominic\\Desktop\\Project-2D\\Project-2D-starting\\sampleCNF\\largeSat.cnf";

        Parser p = new Parser(path);
        Formula fml = new Formula();

        Clause c;
        Literal l;

        for (String[] clause_string : p.getClauses()) {

            c = new Clause();

            for (String literalname : clause_string) {

                if (literalname.startsWith("-")) {
                    l = NegLiteral.make(literalname.substring(1));
                } else {
                    l = PosLiteral.make(literalname);
                }

                c = c.add(l);
            }

            fml = fml.addClause(c);

        }

        System.out.println("SAT solver starts!!!");
        long started = System.nanoTime();

        Environment e = SATSolver.solve(fml);

        long time = System.nanoTime();
        long timeTaken= time - started;
        System.out.println("Time:" + timeTaken/1000000.0 + "ms");

        System.out.println(e);

    }
	
    public static void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);

        System.out.println(e);
/*
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);
    	
*/    	
    }
    
    
    public static void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));

        System.out.println(e);
/*
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/
    }
    
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }
    
    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }
    
    
    
}