package components;

import java.util.*;
import java.io.*;

public class Parser {
    protected String fileType; //file type (cnf, etc.)
    protected int varNo, clausesNo; //Number of variables, number of clauses
    protected String[] comments;
    protected int[][] clauses;

    public Parser() {};

    public Parser(String filePath) {
        String input = Parser.readFile(filePath);
        this.processString(input);
    }

    public String getFileType() {
        return this.fileType;
    }

    public int getVarNo() {
        return this.varNo;
    }

    public int getClausesNo() {
        return this.clausesNo;
    }

    public String[] getComments() {
        return this.comments;
    }

    public int[][] getClauses() {
        return this.clauses;
    }

    //read from file and return a string
    protected static String readFile(String filePath) {
        String everything = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
            br.close();
        } catch(FileNotFoundException e) {
            System.out.println(filePath + " not found!");
        } catch(IOException e) {
            System.out.println("IO error!");
        }
        return everything;
    }

    protected void processString(String input) {
        //split input strings into lines
        String[] lines = input.split("\n+");
        String comments = "", clauses = "", configLine = "";

        for ( String line : lines ) {
            if ( line.matches("^c\\s.*$") ) comments += line.replaceFirst("c +", "") + "\n";
            else if ( line.matches("^p\\s.*$") ) configLine = line;
            else if ( line.matches("^(-?\\d\\s*)*-?\\d$") ) clauses += line + " ";
        }
        //parse comments
        this.comments = comments.split("\n");
        //parse file config
        String[] config = configLine.split("\\s+");
        this.fileType = config[1];
        this.varNo = Integer.parseInt(config[2]);
        this.clausesNo = Integer.parseInt(config[3]);
        this.clauses = new int[this.clausesNo][];
        //parse clauses
        String[] clausesString = clauses.split("\\s0");
        String[] clauseMembers;
        int[] clausesVars;
        for ( int i = 0; i < clausesString.length; i++ ) {
            clauseMembers = clausesString[i].trim().split(" +");
            clausesVars = new int[clauseMembers.length];
            if ( clauseMembers.length == 1 && clauseMembers[0].equals("") ) {
                this.clauses[i] = new int[0];
            } else {
                this.clauses[i] = new int[clauseMembers.length];
                for ( int j = 0; j < clauseMembers.length; j++ ) {
                    clausesVars[j] = Integer.parseInt(clauseMembers[j]);
                }
                this.clauses[i] = clausesVars.clone();
            }
        }
    }

    public String toString() {
        return
            "file type: " + this.fileType +
            "\nno of vars: " + this.varNo +
            "\nno of clauses: " + this.clausesNo +
            "\ncomments: " + Arrays.toString(this.comments) +
            "\nclauses: " + Arrays.deepToString(this.clauses);
    }
}
