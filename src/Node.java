import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

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

                    for (Edge eg : connections) {
                        eg.repaint();
                    }

                    hitbox = new Rectangle(x - RANGE_X / 2, y - RANGE_Y / 2, RANGE_X, RANGE_Y);


                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clickedX = 0;
                clickedY = 0;

                // TODO: When right-clicking on a node you have to add the connecting node to the connection array of the node. Every node keeps a log of all of its connections (((( TRY MAYBE HAVING A CENTRALIZED MAP TO STORE CONNECTIONS INSTEAD OF INDIVIDUAL OBJECT STORING THEM ))))
                // When right-clicking on a node you have to somehow pass the clicked node Object to the GraphPanel class so it can create the edge, display it and add the connection to the hashmap.
                // Before deciding on a data structure you want to hold your nodes check which are used for algorithms like Dijkstra, BFS, DFS ...
                // When coloring the selected node loop through all nodes and check if any are "active" if a single node is active then dont color subsequent nodes. If none are active color the recently clicked one.

                // TODO: If a node becomes active you add it to a static ArrayList inside of GraphPanel which constantly check if there are atleast 2 active nodes. If the ArrayList size is 2 then you draw an edge between those two nodes

                if (SwingUtilities.isRightMouseButton(e)) {

                    isActive(true); // Adds the red color
                    GraphPanel.edgeNodes.add(Node.this);

                    // TODO: When linking nodes together with edges add the edges to the connections list and update the edges. I predict the edges will follow the dragging node if I just call the Edge.repaint() function on all Edges connected to the node.

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
                            eg.getN1().connections.add(eg);
                            eg.getN2().connections.add(eg);
                            GraphPanel.edgeNodes.clear();
                            System.out.println(eg.getN1() + ":  " + connections + " _________ " + eg.getN2() + ": " + connections);
                        } else {
                            System.out.println(eg.getN1() + ":  " + connections + " _________ " + eg.getN2() + ": " + connections);
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
        //g2d.drawRect(0, 0, RANGE_X, RANGE_Y);

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
        return String.format("%s (%d, %d)", nodeLetter, x, y);
    }
}
