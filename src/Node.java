import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Node extends JComponent {

    private int x;
    private int y;

    private int RANGE_X = 40;
    private int RANGE_Y = 40;
    private Rectangle hitbox;
    private String nodeLetter = GraphPanel.nodeLetters.charAt(GraphPanel.nodes.size()) + "";

    private Color nodeColor = Color.BLACK;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.setBounds(0, 0, 700, 500);
        hitbox = new Rectangle(x - RANGE_X / 2, y - RANGE_Y / 2, RANGE_X, RANGE_Y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawString(String.valueOf(nodeLetter), (int) x - 7, (int) y - 7);

        g2d.drawRect(x - RANGE_X / 2, y - RANGE_Y / 2, RANGE_X, RANGE_Y);

        Ellipse2D.Double node = new Ellipse2D.Double(x - 5 , y - 5 , 10.0, 10.0);
        g2d.setColor(nodeColor);
        g2d.fill(node);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public String getNodeLetter() {
        return nodeLetter;
    }

    public void setNodeColor(Color nodeColor) {
        this.nodeColor = nodeColor;
    }

    public void isActive(boolean b) {
        if(b) setNodeColor(Color.RED);
        else setNodeColor(Color.BLACK);
    }

    public int getXCoord() {
        return x;
    }

    public int getYCoord() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
