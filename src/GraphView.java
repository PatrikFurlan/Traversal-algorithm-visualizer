import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GraphView extends JPanel implements GraphModelListener {

    private JFrame f;
    private int radius;

    private ArrayList<Node> pendingNodes = new ArrayList<>();
    private ArrayList<Edge> pendingEdges = new ArrayList<>();
    private Set<Node> pendingAvailable = new HashSet<>();

    // Ghost edge data
    private Node ghostEdgeStart = null;
    private int ghostX = 0;
    private int ghostY = 0;

    public GraphView() {

    }

    public void addNodes(ArrayList<Node> nodes) {
        pendingNodes = nodes;
        repaint();
    }

    public void addEdges(ArrayList<Edge> edges) {
        pendingEdges = edges;
        repaint();
    }

    public void drawGhostEdge(Node n, int x, int y) {
        ghostEdgeStart = n;
        ghostX = x;
        ghostY = y;
        repaint();
    }

    public void highlightAvailable(Set<Node> available) {
        pendingAvailable = available;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Node n : pendingNodes) {
            g.setColor(new Color(0, 0, 0));
            if (n.isSelected()) {
                g.setColor(new Color(255, 0, 0));
            }
            g.fillOval((int) n.getX() - 5, (int) n.getY() - 5, 10, 10);
            g.drawString(n.getLabel(), (int) n.getX() - 15, (int) n.getY());
        }

        g.setColor(Color.BLACK);
        if(pendingEdges != null) {
            for (Edge e : pendingEdges) {
                int x1 = (int) e.getFrom().getX();
                int y1 = (int) e.getFrom().getY();

                int x2 = (int) e.getTo().getX();
                int y2 = (int) e.getTo().getY();

                g.drawLine(x1, y1, x2, y2);
            }
        }

        // Draw ghost edge
        if (ghostEdgeStart != null) {
            g.drawLine((int) ghostEdgeStart.getX(), (int) ghostEdgeStart.getY(), ghostX, ghostY);
        }

        for (Node n : pendingAvailable) {
            g.drawRect((int) n.getX() - radius / 2, (int) n.getY() - radius / 2, radius, radius);
        }

        g.setColor(Color.BLACK);
    }

    public void setGhostEdgeStart(Node ghostEdgeStart) {
        this.ghostEdgeStart = ghostEdgeStart;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    //Observer mechanism methods

    @Override
    public void modelChanged() {
        repaint(); // This gets called by the model when it changes its state
    }
}
