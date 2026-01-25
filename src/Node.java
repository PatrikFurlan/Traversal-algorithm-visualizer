import java.awt.*;

public class Node {

    private static int nodesDropped = 0;
    private static String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int id;
    private String label = "";
    private double x;
    private double y;
    private boolean selected;
    private Color color = Color.BLACK; // When selecting a node we can specify the color that GraphView will paint
    private boolean visited = false; // Used for graph algorithms

    public Node(double x, double y) {
        this.id = nodesDropped + 1;

        this.label = "" + abc.charAt(this.id - 1);
        this.x = x;
        this.y = y;

        nodesDropped++;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static int getNodesDropped() {
        return nodesDropped;
    }

    public static void setNodesDropped(int n) {
        nodesDropped = n;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected, Color color) {
        this.selected = selected;
        this.color = color;
    }

    public Color getColor() { return this.color; }

    public String toString() {
        return getLabel() + "";
    }
}
