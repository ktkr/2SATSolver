package components;

import java.util.*;
import java.io.*;

public class TwoSat {
    private Graph graph = new Graph();

    TwoSat() {};

    public TwoSat(Parser p) throws InvalidInputException {
        //check for valid input file type
        if ( !p.getFileType().equals("cnf") ) throw new InvalidInputException();
        //check for valid 2 variables in a clause
        for ( int[] c : p.getClauses() ) {
            if ( c.length != 2 ) throw new InvalidInputException();
        }
        //build Graph
        int flippedFirst, flippedSecond;
        for ( int[] c : p.getClauses() ) {
            //instantiate new nodes and add to graph if not in yet
            Node nodeFromFirst = new Node(c[0]);
            if ( graph.indexOf(nodeFromFirst) == -1 ) graph.add(nodeFromFirst.clone());
            Node nodeFromSecond = new Node(c[1]);
            if ( graph.indexOf(nodeFromSecond) == -1 ) graph.add(nodeFromSecond.clone());
            flippedFirst = 0 - c[0];
            Node nodeFromFlippedFirst = new Node(flippedFirst);
            if ( graph.indexOf(nodeFromFlippedFirst) == -1 ) graph.add(nodeFromFlippedFirst.clone());
            flippedSecond = 0 - c[1];
            Node nodeFromFlippedSecond = new Node(flippedSecond);
            if ( graph.indexOf(nodeFromFlippedSecond) == -1 ) graph.add(nodeFromFlippedSecond.clone());
            //add children to nodes
            graph.getNodeFromVal(flippedFirst).addAChild(graph.getNodeFromVal(c[1]));
            graph.getNodeFromVal(flippedSecond).addAChild(graph.getNodeFromVal(c[0]));
        }

        System.out.println(graph);
    }

    public String solve() {
        return "FORMULA SATISFIABLE";
    }
}
