package components;

import java.util.*;
import java.io.*;

public class TwoSat {
    private HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
    private HashMap<Integer, Node> transposedGraph = new HashMap<Integer, Node>();
    private ArrayList<Integer> stack = new ArrayList<Integer>();
    private ArrayList<ArrayList<Integer>> sccStack = new ArrayList<ArrayList<Integer>>();
    private HashMap<Integer, Integer> output = new HashMap<Integer, Integer>();

    public TwoSat() {};

    public TwoSat(Parser p) throws InvalidInputException {
        //check for valid input file type
        if ( !p.getFileType().equals("cnf") ) throw new InvalidInputException();
        //check for valid 2 variables in a clause
        for ( int[] c : p.getClauses() ) {
            if ( c.length != 2 ) throw new InvalidInputException();
        }
        //build Graph
        graph.put(0, new Node(0)); //virtual almighty root node
        int flippedFirst, flippedSecond;
        final long startTime = System.currentTimeMillis();
        for ( int[] c : p.getClauses() ) {
            //instantiate new nodes and add to graph if not in yet
            Node nodeFromFirst = new Node(c[0]);
            if ( !graph.containsKey(c[0]) ) {
                graph.put(c[0], nodeFromFirst.clone());
                transposedGraph.put(c[0], nodeFromFirst.clone());
            }
            Node nodeFromSecond = new Node(c[1]);
            if ( !graph.containsKey(c[1]) ) {
                graph.put(c[1], nodeFromSecond.clone());
                transposedGraph.put(c[1], nodeFromSecond.clone());
            }
            flippedFirst = 0 - c[0];
            Node nodeFromFlippedFirst = new Node(flippedFirst);
            if ( !graph.containsKey(flippedFirst) ) {
                graph.put(flippedFirst, nodeFromFlippedFirst.clone());
                graph.get(0).addAChild(graph.get(flippedFirst)); //connect to virtual root
                transposedGraph.put(flippedFirst, nodeFromFlippedFirst.clone());
            }
            flippedSecond = 0 - c[1];
            Node nodeFromFlippedSecond = new Node(flippedSecond);
            if ( !graph.containsKey(flippedSecond) ) {
                graph.put(flippedSecond, nodeFromFlippedSecond.clone());
                graph.get(0).addAChild(graph.get(flippedSecond)); //connect to virtual root
                transposedGraph.put(flippedSecond, nodeFromFlippedSecond.clone());
            }
            //add children to nodes
            if ( graph.get(flippedFirst).getChildren().indexOf(graph.get(c[1])) == -1 ){
                graph.get(flippedFirst).addAChild(graph.get(c[1]));
            }
            if ( graph.get(flippedSecond).getChildren().indexOf(graph.get(c[0])) == -1 ) {
                graph.get(flippedSecond).addAChild(graph.get(c[0]));
            }
            if ( transposedGraph.get(c[1]).getChildren().indexOf(transposedGraph.get(flippedFirst)) == -1 ) {
                transposedGraph.get(c[1]).addAChild(transposedGraph.get(flippedFirst));
            }
            if ( transposedGraph.get(c[0]).getChildren().indexOf(transposedGraph.get(flippedSecond)) == -1 ) {
                transposedGraph.get(c[0]).addAChild(transposedGraph.get(flippedSecond));
            }
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("generating graph time taken: " + (endTime - startTime));
        //instantiate output
        // this.output = new int[p.getVarNo()];
    }

    public void dfs(Node n) {
        if ( n.getState() == 1 ) {
            n.incState();
            if ( n.getValue() != 0 ) stack.add(n.getValue());
        } else if ( n.getState() == 0 ) {
            n.incState();
            ArrayList<Node> untravelledChildren = n.getUntravelledChildren();
            if ( untravelledChildren != null ) {
                for ( Node c : untravelledChildren ) {
                    dfs(c);
                }
            }
            dfs(n);
        }
    }

    public void buildSCC() {
        int currentVal = stack.remove(stack.size() - 1), counter = 0;
        Node currentNode;
        ArrayList<Integer> currentSCC = new ArrayList<Integer>();
        while ( stack.size() > 0 ) {
            for ( int i = 1; i <= counter; i++ ) {
                currentVal = stack.remove(stack.size() - 1);
            }
            currentNode = transposedGraph.get(currentVal);
            currentNode.incState();
            currentSCC.add(currentNode.getValue());
            counter = 0;
            while ( currentNode.getUntravelledChildren() != null ) {
                currentNode.incState();
                currentNode = currentNode.getUntravelledChildren().get(0);
                currentSCC.add(currentNode.getValue());
                counter++;
            }
            if ( counter == 0 ) {
                counter++;
            }
            sccStack.add(new ArrayList<Integer>(currentSCC));
            currentSCC.clear();
        }
    }

    public String solve() {
        long startTime = System.currentTimeMillis();
        this.dfs(graph.get(0));
        long endTime = System.currentTimeMillis();
        System.out.println("time taken to dfs: " + (endTime - startTime));
        startTime = System.currentTimeMillis();
        this.buildSCC();
        endTime = System.currentTimeMillis();
        System.out.println("time taken to build SCC: " + (endTime - startTime));
        startTime = System.currentTimeMillis();
        for ( int j = this.sccStack.size() - 1; j > -1; j-- ) {
            ArrayList<Integer> s = this.sccStack.get(j);
            for ( int i : s ) {
                if ( s.indexOf( 0 - i ) != -1 ) return "FORMULA UNSATISFIABLE";
                else if ( i > 0 && !output.containsKey(i) ) output.put(i, 1);
                else if ( i < 0 && !output.containsKey(0-i) ) output.put(0-i, 0);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("time taken to build output: " + (endTime - startTime));
        startTime = System.currentTimeMillis();
        String result = "";
        for ( int i : output.keySet() ) {
            result += output.get(i) + " ";
        }
        System.out.println("output: " + result);
        endTime = System.currentTimeMillis();
        System.out.println("time taken to print result string: " + (endTime - startTime));
        return "FORMULA SATISFIABLE";
    }
}
