package components;

import java.util.*;
import java.io.*;

public class TwoSat {

    TwoSat() {};

    public TwoSat(Parser p) throws InvalidInputException {
        //check for valid input file type
        if ( !p.getFileType().equals("cnf") ) throw new InvalidInputException();
        //check for valid 2 variables in a clause
        for ( int[] c : p.getClauses() ) {
            if ( c.length != 2 ) throw new InvalidInputException();
        }
    }

    public String solve() {
        return "FORMULA SATISFIABLE";
    }
}
