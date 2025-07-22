import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphPanel extends JPanel {

    public static String nodeLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static ArrayList<Node> nodes = new ArrayList<>();
    public static HashMap<Node, List<Node>> edges = new HashMap<>(); //map with node name and connections to it eg. A: BCDE

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
            Edge ed = new Edge(edgeNodes);

            add(ed);

            for (Node n : nodes) n.isActive(false);

            ed.repaint();

            return ed;
        }

        return null;
    }

    public boolean onNode(Node n, int x, int y) {
        return n.getHitbox().contains(x, y);
    }

}
