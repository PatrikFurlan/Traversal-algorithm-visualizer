public class Node {

    private static int nodesDropped = 0;
    private static String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int id;
    private String label = "";
    private double x;
    private double y;
    private boolean selected;

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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String toString() {
        return getLabel() + "";
    }
}
