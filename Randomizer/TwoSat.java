//package sutd.twosat;
//
//import java.util.*;
//import java.io.*;
//
//public class TwoSat {
//    private Graph graph = new Graph();
//    private Graph transposedGraph = new Graph();
//    private ArrayList<Integer> stack = new ArrayList<Integer>();
//    private int[][] scc;
//
//    TwoSat() {};
//
//    public TwoSat(Parser p) throws InvalidInputException {
//        //check for valid input file type
//        if ( !p.getFileType().equals("cnf") ) throw new InvalidInputException();
//        //check for valid 2 variables in a clause
//        for ( int[] c : p.getClauses() ) {
//            if ( c.length != 2 ) throw new InvalidInputException();
//        }
//        //build Graph
//        graph.add(new Node(0)); //virtual almighty root node
//        transposedGraph.add(new Node(0)); //virtual almighty root node
//        int flippedFirst, flippedSecond;
//        for ( int[] c : p.getClauses() ) {
//            System.out.println(Arrays.toString(c));
//            //instantiate new nodes and add to graph if not in yet
//            Node nodeFromFirst = new Node(c[0]);
//            if ( graph.indexOf(nodeFromFirst) == -1 ) {
//                graph.add(nodeFromFirst.clone());
//                transposedGraph.add(nodeFromFirst.clone());
//                transposedGraph.getNodeFromVal(0).addAChild(transposedGraph.getNodeFromVal(c[0])); //connect yo virtual root
//            }
//            Node nodeFromSecond = new Node(c[1]);
//            if ( graph.indexOf(nodeFromSecond) == -1 ) {
//                graph.add(nodeFromSecond.clone());
//                transposedGraph.add(nodeFromSecond.clone());
//                transposedGraph.getNodeFromVal(0).addAChild(transposedGraph.getNodeFromVal(c[1])); //connect yo virtual root
//            }
//            flippedFirst = 0 - c[0];
//            Node nodeFromFlippedFirst = new Node(flippedFirst);
//            if ( graph.indexOf(nodeFromFlippedFirst) == -1 ) {
//                graph.add(nodeFromFlippedFirst.clone());
//                graph.getNodeFromVal(0).addAChild(graph.getNodeFromVal(flippedFirst)); //connect to virtual root
//                transposedGraph.add(nodeFromFlippedFirst.clone());
//            }
//            flippedSecond = 0 - c[1];
//            Node nodeFromFlippedSecond = new Node(flippedSecond);
//            if ( graph.indexOf(nodeFromFlippedSecond) == -1 ) {
//                graph.add(nodeFromFlippedSecond.clone());
//                graph.getNodeFromVal(0).addAChild(graph.getNodeFromVal(flippedSecond)); //connect to virtual root
//                transposedGraph.add(nodeFromFlippedSecond.clone());
//            }
//            //add children to nodes
//            if ( graph.getNodeFromVal(flippedFirst).getChildren().indexOf(graph.getNodeFromVal(c[1])) == -1 ){
//                graph.getNodeFromVal(flippedFirst).addAChild(graph.getNodeFromVal(c[1]));
//            }
//            if ( graph.getNodeFromVal(flippedSecond).getChildren().indexOf(graph.getNodeFromVal(c[0])) == -1 ) {
//                graph.getNodeFromVal(flippedSecond).addAChild(graph.getNodeFromVal(c[0]));
//            }
//            if ( transposedGraph.getNodeFromVal(c[1]).getChildren().indexOf(transposedGraph.getNodeFromVal(flippedFirst)) == -1 ) {
//                transposedGraph.getNodeFromVal(c[1]).addAChild(transposedGraph.getNodeFromVal(flippedFirst));
//            }
//            if ( transposedGraph.getNodeFromVal(c[0]).getChildren().indexOf(transposedGraph.getNodeFromVal(flippedSecond)) == -1 ) {
//                transposedGraph.getNodeFromVal(c[0]).addAChild(transposedGraph.getNodeFromVal(flippedSecond));
//            }
//        }
//
//        System.out.println(graph);
//        System.out.println(transposedGraph);
//        dfs(graph.getNodeFromVal(0));
//        System.out.println(stack);
//    }
//
//    public void dfs(Node n) {
//        if ( n.getState() == 1 ) {
//            System.out.println("adding " + n);
//            n.incState();
//            if ( n.getValue() != 0 ) stack.add(n.getValue());
//        } else if ( n.getState() == 0 ) {
//            n.incState();
//            ArrayList<Node> untravelledChildren = n.getUntravelledChildren();
//            if ( untravelledChildren == null ) {
//                dfs(n);
//            } else {
//                for ( Node c : untravelledChildren ) {
//                    System.out.println("travelling " + c);
//                    dfs(c);
//                }
//                dfs(n);
//            }
//        } else {
//            System.out.println("state 2 ah?");
//        }
//    }
//
//    public void randomWalk(){
//
//    }
//
//    public String solve() {
//        return "FORMULA SATISFIABLE";
//    }
//}
