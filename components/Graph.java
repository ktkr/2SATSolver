package components;

import java.util.*;

@SuppressWarnings("serial")
public class Graph extends ArrayList<Node> {
    public int indexOf(Node n) {
        for ( int i = 0; i < this.size(); i++ ) {
            if ( this.get(i).getValue() == n.getValue() ) return i;
        }
        return -1;
    }

    public Node getNodeFromVal(int v) {
        return this.get(this.indexOf(new Node(v)));
    }
}
