package components;

import java.util.*;
import java.io.*;


public class Node {
    private int value, state;
    private ArrayList<Node> children = new ArrayList<Node>();

    Node() {};

    public Node(int v) {
        this.value = v;
        this.state = 0;
    }

    public int getValue() {
        return this.value;
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public int getState() {
        return this.state;
    }

    public void addAChild(Node n) {
        this.children.add(n);
    }

    public void incState() {
        this.state++;
    }

    public ArrayList<Node> getUntravelledChildren() {
        ArrayList<Node> result = new ArrayList<Node>();
        for ( Node child : this.children ) {
            if ( child.getState() == 0 ) {
                result.add(child);
            }
        }
        if ( result.size() > 0 ) return result;
        else return null;
    }

    public String toString() {
        List<Integer> childVals = new ArrayList<Integer>();
        for ( Node child : this.children ) {
            childVals.add(child.value);
        }
        return "node(" + this.value + "): " + Arrays.toString(childVals.toArray());
    }

    public Node clone() {
        Node clonedNode = new Node(this.value);
        for ( Node child : this.children ) {
            clonedNode.addAChild(child);
        }
        return clonedNode;
    }
}
