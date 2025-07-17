import javax.swing.*;
import java.awt.*;

public class Edge extends JComponent {

    private Node n1, n2;

    public Edge(Node[] en) {
        this.n1 = en[0];
        this.n2 = en[1];
        this.setBounds(0, 0, 700, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawLine(n1.getXCoord(), n1.getYCoord(), n2.getXCoord(), n2.getYCoord());
    }

    @Override
    public String toString() {
        return String.format("%s - %s", n1.getNodeLetter(), n2.getNodeLetter());
    }
}
