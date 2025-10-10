import com.sun.source.tree.Tree;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphModel {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    // Main data structure for use in graph algorithms
    private Map<Node, Set<Node>> neighbourList = new HashMap<>();

    // Add observers of the GraphModel class - (GraphView for now)
    private List<GraphModelListener> observers = new ArrayList<>();

    public GraphModel() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        notifyObservers();
    }

    public void addNode(Node n) {
        nodes.add(n);
        neighbourList.put(n, new TreeSet<Node>(new Comparator<Node>() { // MOGOCE CELO NE RABI BITI UREJEN SET !!!!
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        }));

        notifyObservers();
    }

    public void addEdge(Edge e){
        edges.add(e);

        Node from = e.getFrom();
        Node to = e.getTo();

        Set<Node> neighbours = neighbourList.get(from);
        neighbours.add(to);
        neighbourList.put(from, neighbours);

        neighbours = neighbourList.get(to);
        neighbours.add(from);
        neighbourList.put(to, neighbours);

        System.out.println(neighbourList);
        notifyObservers();
    }

    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    //Observer methods

    public void addObserver(GraphModelListener gml) {
        this.observers.add(gml);
    }

    public void removeObserver(GraphModelListener gml) {
        this.observers.remove(gml);
    }

    protected void notifyObservers() {
        for(GraphModelListener gml : observers) {
            gml.modelChanged();
        }
    }
}
