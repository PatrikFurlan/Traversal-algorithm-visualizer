import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphView extends JPanel implements GraphModelListener {

    private JFrame f;
    private ArrayList<Node> pendingNodes = new ArrayList<>();

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Node n : pendingNodes) {
            g.fillOval((int) n.getX() - 5, (int) n.getY() - 5, 10, 10);
        }
    }


    //Observer mechanism methods

    @Override
    public void modelChanged() {
        repaint(); // This gets called by the model when it changes its state
    }
}
