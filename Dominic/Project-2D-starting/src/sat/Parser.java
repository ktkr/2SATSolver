package src.sat;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private String fileType; //file type (cnf, etc.)
    private int varNo, clausesNo; //Number of variables, number of clauses
    private String comments;
    private String[][] clauses;

    // Accessors

    public String getFileType() {
        return this.fileType;
    }

    public int getVarNo() {
        return this.varNo;
    }

    public int getClausesNo() {
        return this.clausesNo;
    }

    public String getComments() {
        return this.comments;
    }

    public String[][] getClauses() {
        return this.clauses;
    }

    public Parser(String path) {
        parse(readFile(path));
    }

    private String readFile(String path) {
        String contents = "";

        try {
            FileReader fr = new FileReader(path);
            BufferedReader textReader = new BufferedReader(fr);

            String aLine;
            while ((aLine = textReader.readLine()) != null) {
                contents += aLine + "\n";
            }

            textReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents;
    }

    // Regex for parsing

    private final static Pattern clause_parser = Pattern.compile("(?:p\\s*cnf\\s*\\d*\\s\\d*|0)\\s*((?:\\s*-?[1-9]\\d*\\s*)+)(?<! 0)", Pattern.DOTALL);
    private final static Pattern var_parser = Pattern.compile("\\s*(-?\\d+)\\s*", Pattern.DOTALL);
    private final static Pattern comment_parser = Pattern.compile("c\\s*(.*)\\s*p\\s*(cnf|sat)\\s*(\\d*)\\s*(\\d*)\\s*", Pattern.DOTALL);

    private void parse(String contents) {

        Matcher m_comments = comment_parser.matcher(contents);

        m_comments.find();

        this.comments = m_comments.group(1);
        this.fileType = m_comments.group(2);
        this.varNo = Integer.valueOf(m_comments.group(3));
        this.clausesNo = Integer.valueOf(m_comments.group(4));

        Matcher m = clause_parser.matcher(contents);
        String clause;

        Matcher m2;
        String literalname;

        this.clauses = new String[this.clausesNo][];

        int i = 0;
        ArrayList<String> literals = new ArrayList<String>();

        while (m.find()) {

            clause = m.group(1);
            m2 = var_parser.matcher(clause);

            literals.clear();
            while (m2.find()) {
                literalname = m2.group(1);

                literals.add(literalname);
            }

            this.clauses[i] = new String[literals.size()];
            this.clauses[i] = literals.toArray(this.clauses[i]);
            i++;

        }

    }

    public String toString() {
        return
                "file type: " + this.fileType +
                        "\nno of vars: " + this.varNo +
                        "\nno of clauses: " + this.clausesNo +
                        "\ncomments: " + this.comments +
                        "\nclauses: " + Arrays.deepToString(this.clauses);
    }
}