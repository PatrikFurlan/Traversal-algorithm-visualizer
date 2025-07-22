import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Edge extends JComponent {

    private Node n1, n2;

    public Edge(ArrayList<Node> en) {
        this.n1 = en.get(0);
        this.n2 = en.get(1);
        this.setBounds(0, 0, 700, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawLine(n1.getXCoord(), n1.getYCoord(), n2.getXCoord(), n2.getYCoord());
    }

    public Node getN1() {
        return n1;
    }

    public Node getN2() {
        return n2;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", n1.getNodeLetter(), n2.getNodeLetter());
    }
}
