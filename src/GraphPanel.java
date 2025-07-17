import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphPanel extends JPanel {

    public static String nodeLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static ArrayList<Node> nodes = new ArrayList<>(); // make a map with node name and connections to it eg. A: BCDE
    public static HashMap<Node, List<Node>> edges = new HashMap<>();

    private static int nodesClicked = 0;
    private static Node[] edgeNodes = new Node[2];

    public GraphPanel() {
        setLayout(null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Node n = new Node(e.getX(), e.getY());
                    nodes.add(n);

                    repaint();
                    add(n);
                    n.repaint();
                }
                else if (SwingUtilities.isRightMouseButton(e)) {

                    for(Node n : nodes) {

                        if(onNode(n, e.getX(), e.getY())) {  // TODO REWRITE NODE LINKING SYSTEM
                           edgeNodes[nodesClicked % 2] = n;
                           nodesClicked ++;

                            // Define a line connecting two nodes TODO add the connection to the map
                            if (nodesClicked == 2) {
                                Edge ed = new Edge(edgeNodes);
                                System.out.println(ed);


                                add(ed);
                                ed.repaint();

                                nodesClicked = 0;
                            } else if (nodesClicked == 1) {

                            }
                        }
                    }
                }
            }
        });

    }

    public boolean onNode(Node n, int x, int y) {
        return n.getHitbox().contains(x, y);
    }

}
