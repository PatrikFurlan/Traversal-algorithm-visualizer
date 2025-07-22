import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashSet;

public class Node extends JComponent {

    private int x;
    private int y;

    private ArrayList<Edge> connections = new ArrayList<>();

    private int clickedX;
    private int clickedY;

    private final int RANGE_X = 40;
    private final int RANGE_Y = 40;
    private Rectangle hitbox;
    private final String nodeLetter = GraphPanel.nodeLetters.charAt(GraphPanel.nodes.size()) + "";
    private boolean active = false;

    private Color nodeColor = Color.BLACK;

    public Node(int x, int y, GraphPanel gp) {
        this.x = x;
        this.y = y;

        setBounds(x - RANGE_X / 2, y - RANGE_Y / 2, RANGE_X, RANGE_Y);

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {
                    clickedX = e.getX();
                    clickedY = e.getY();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {
                    int newX = getX() + e.getX() - clickedX;
                    int newY = getY() + e.getY() - clickedY;
                    setLocation(newX, newY);

                    setXCoord(newX + RANGE_X / 2);
                    setYCoord(newY + RANGE_Y / 2);

                    for (Edge eg : connections) { eg.repaint(); }

                    hitbox = new Rectangle(x - RANGE_X / 2, y - RANGE_Y / 2, RANGE_X, RANGE_Y);

                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clickedX = 0;
                clickedY = 0;

                if (SwingUtilities.isRightMouseButton(e)) {

                    isActive(true);
                    GraphPanel.edgeNodes.add(Node.this);

                    repaint();

                    if (gp.checkLinkable() != null) {
                        Edge eg = gp.checkLinkable();

                        boolean duplicate = false;
                        for (Edge existing : eg.getN1().connections) {
                            if (
                                    (existing.getN1() == eg.getN2() && existing.getN2() == eg.getN1()) ||
                                            (existing.getN1() == eg.getN1() && existing.getN2() == eg.getN2())
                            ) {
                                duplicate = true;
                                break;
                            }
                        }

                        if (!duplicate) {

                            // TODO: Consider adding edges to a data structure alongside connecting nodes to simplify edge coloring in the future when implemening various algorithms
                            eg.getN1().connections.add(eg);
                            eg.getN2().connections.add(eg);

                            eg.getN1().isActive(false);
                            eg.getN2().isActive(false);

                            GraphPanel.graphData.computeIfAbsent(eg.getN1(), k -> new HashSet<>()).add(eg.getN2());
                            GraphPanel.graphData.computeIfAbsent(eg.getN2(), k -> new HashSet<>()).add(eg.getN1());

                            System.out.println(GraphPanel.graphData);

                            GraphPanel.edgeNodes.clear();
                        } else {
                            GraphPanel.edgeNodes.clear();
                        }
                    }
                }
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        hitbox = new Rectangle(x - RANGE_X / 2, y - RANGE_Y / 2, RANGE_X, RANGE_Y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Always call super.paintComponent

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int drawX = RANGE_X / 2;
        int drawY = RANGE_Y / 2;

        g2d.setColor(Color.BLACK);
        g2d.drawString(nodeLetter, drawX - 7, drawY - 7);

        Ellipse2D.Double node = new Ellipse2D.Double(drawX - 5, drawY - 5, 10.0, 10.0);
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
        setNodeColor(b ? Color.RED : Color.BLACK);
    }

    public boolean getActive() {
        return active;
    }

    public Point getNodePosition() {
        return new Point(this.x, this.y);
    }

    public void setXCoord(int x) {
        this.x = x;
        setLocation(x - RANGE_X / 2, getY());
        hitbox = new Rectangle(x - RANGE_X / 2, y - RANGE_Y / 2, RANGE_X, RANGE_Y);
    }

    public void setYCoord(int y) {
        this.y = y;
        setLocation(getX(), y - RANGE_Y / 2);
        hitbox = new Rectangle(x - RANGE_X / 2, y - RANGE_Y / 2, RANGE_X, RANGE_Y);
    }

    public int getXCoord() {
        return x;
    }

    public int getYCoord() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("%s", nodeLetter);
    }
}
