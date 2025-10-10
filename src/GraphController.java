import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GraphController {

    private GraphModel model;
    private GraphView view;

    private Node firstNode = null; // For Edge creation

    public GraphController(GraphModel model, GraphView view) {
        this.model = model;
        this.view = view;

        model.addObserver(new GraphModelListener() {
            @Override
            public void modelChanged() {
                view.addNodes(model.getNodes());
                view.addEdges(model.getEdges());
            }
        });


        this.view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    Node n = new Node(e.getX(), e.getY());
                    model.addNode(n);
                }

                else if(SwingUtilities.isRightMouseButton(e)) {
                    Node clickedNode = getClickedNode(e.getX(), e.getY());

                    if(clickedNode != null) {
                        if(firstNode == null) {
                            firstNode = clickedNode;
                            firstNode.setSelected(true);
                            view.repaint();

                        } else if(firstNode != clickedNode){
                            firstNode.setSelected(false);

                            Edge edge = new Edge(firstNode, clickedNode);
                            model.addEdge(edge);
                            System.out.println(model.getEdges());

                            firstNode = null; // Reset firstNode for next time
                        }
                    } else {
                        if (firstNode != null) {
                            firstNode.setSelected(false);
                            view.repaint();
                        }
                        firstNode = null;
                    }

                }

            }


        });

    }

    private Node getClickedNode(int x, int y) {
        for (Node n : model.getNodes()) {
            int nodeX = (int) n.getX();
            int nodeY = (int) n.getY();
            int radius = 20;
            if (x >= nodeX - radius && x <= nodeX + radius &&
                    y >= nodeY - radius && y <= nodeY + radius) {
                return n;
            }
        }
        return null; // clicked on empty space
    }
}
