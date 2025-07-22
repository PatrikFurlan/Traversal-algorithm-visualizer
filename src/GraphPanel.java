import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphPanel extends JPanel {

    public static String nodeLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static ArrayList<Node> nodes = new ArrayList<>();
    public static HashMap<Node, HashSet<Node>> graphData = new HashMap<>(); //map with node name and connections to it eg. A: BCDE

    public static ArrayList<Node> edgeNodes = new ArrayList<>();

    public GraphPanel() {
        setLayout(null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Node n = new Node(e.getX(), e.getY(), GraphPanel.this);
                    nodes.add(n);

                    repaint();
                    add(n);
                    n.repaint();
                  }
            }
        });
    }

    public Edge checkLinkable() {
        if (edgeNodes.size() == 2) {
            if (edgeNodes.get(0) == edgeNodes.get(1)) {
                System.out.println("Loopback connection prevented.");
                edgeNodes.clear();
                return null;
            }

            Edge ed = new Edge(edgeNodes);

            add(ed);

            ed.repaint();

            return ed;
        }

        return null;
    }

    public boolean onNode(Node n, int x, int y) {
        return n.getHitbox().contains(x, y);
    }

}
