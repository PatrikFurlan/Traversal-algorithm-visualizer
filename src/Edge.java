import java.awt.*;

public class Edge {
    private Node from;
    private Node to;
    private boolean selected = false;
    private Color color = Color.BLACK;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public void setSelected(boolean b, Color color) {
        selected = b;
        this.color = color;
    }
    public boolean isSelected() {
        return selected;
    }
    public Color getColor() { return this.color; }
    public String toString() {
        return String.format("%s - %s", from, to);
    }
}
