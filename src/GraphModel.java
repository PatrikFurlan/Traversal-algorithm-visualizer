import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphModel {

    private ArrayList<Node> nodes;
    //private ArrayList<Edge> edges;

    //Add observers of the GraphModel class - (GraphView for now)
    private List<GraphModelListener> observers = new ArrayList<>();

    public GraphModel() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node n) {
        nodes.add(n);
        notifyObservers();
    }

    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    //Observer methods

    public void addObserver(GraphModelListener gml) {
        this.observers.add(gml);
    }

    public void removeObserver(GraphModelListener gml) {
        this.observers.remove(gml);
    }

    private void notifyObservers() {
        for(GraphModelListener gml : observers) {
            gml.modelChanged();
        }
    }
}
