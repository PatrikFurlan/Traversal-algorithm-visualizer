import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphView extends JPanel implements GraphModelListener {

    private JFrame f;
    private ArrayList<Node> pendingNodes = new ArrayList<>();
    private ArrayList<Edge> pendingEdges = new ArrayList<>();

    public GraphView() {
        f = new JFrame();

        f.add(this);
        f.setSize(800, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addNodes(ArrayList<Node> nodes) {
        pendingNodes = nodes;
        repaint();
    }

    public void addEdges(ArrayList<Edge> edges) {
        pendingEdges = edges;
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
        }

        g.setColor(new Color(0, 0, 0));
        if(pendingEdges != null) {
            for (Edge e : pendingEdges) {
                int x1 = (int) e.getFrom().getX();
                int y1 = (int) e.getFrom().getY();

                int x2 = (int) e.getTo().getX();
                int y2 = (int) e.getTo().getY();

                g.drawLine(x1, y1, x2, y2);
            }
        }
    }


    //Observer mechanism methods

    @Override
    public void modelChanged() {
        repaint(); // This gets called by the model when it changes its state
    }
}
