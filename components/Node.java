package components;

import java.util.*;
import java.io.*;


public class Node {
    private int value;
    private ArrayList<Node> children = new ArrayList<Node>();
    private int state = 0;

    Node() {};

    public Node(int v) {
        this.value = v;
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

    public void decState() {
        this.state--;
    }

    public String toString() {
        return "";
    }
}
