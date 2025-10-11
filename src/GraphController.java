import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphController {

    private final GraphModel model;
    private final GraphView view;

    private Node firstNode = null;
    private Node draggedNode = null;

    private int radius = 30;

    public GraphController(GraphModel model, GraphView view) {
        this.model = model;
        this.view = view;

        view.setRadius(this.radius);

        model.addObserver(new GraphModelListener() {
            @Override
            public void modelChanged() {
                view.addNodes(model.getNodes());
                view.addEdges(model.getEdges());
            }
        });

        addMouseListeners();
        addMouseMotionListeners();
    }

    /* -------------------- EVENT HANDLER SETUP -------------------- */

    private void addMouseListeners() {
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Node clicked = getClickedNode(e.getX(), e.getY());
                    if (clicked != null) {
                        draggedNode = clicked; // prepare for dragging
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (draggedNode != null) {
                        draggedNode = null; // stop dragging
                    } else {
                        // Only create new node if released on empty space
                        Node clicked = getClickedNode(e.getX(), e.getY());
                        if (clicked == null) {
                            handleCreateNode(e);
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    handleRightClick(e);
                }
            }
        });
    }

    private void addMouseMotionListeners() {
        view.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleDrag(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMove(e);
            }
        });
    }

    /* -------------------- EVENT LOGIC -------------------- */

    private void handleCreateNode(MouseEvent e) {
        Node n = new Node(e.getX(), e.getY());
        model.addNode(n);
    }

    private void handleRightClick(MouseEvent e) {
        Node clickedNode = getClickedNode(e.getX(), e.getY());

        if (clickedNode != null) {
            handleNodeClick(clickedNode);
        } else {
            handleEmptySpaceClick();
        }
    }

    private void handleDrag(MouseEvent e) {
        if (draggedNode != null) {
            draggedNode.setX(e.getX());
            draggedNode.setY(e.getY());
            view.repaint();
        }
    }

    private void handleMouseMove(MouseEvent e) {
        if (firstNode != null) {
            view.drawGhostEdge(firstNode, e.getX(), e.getY());
        }
    }

    /* -------------------- RIGHT CLICK HELPERS -------------------- */

    private void handleNodeClick(Node clickedNode) {
        if (firstNode == null) {
            firstNode = clickedNode;
            firstNode.setSelected(true);

            highlightAvailableNodes(firstNode);

            view.repaint();
        } else if (firstNode != clickedNode) {
            firstNode.setSelected(false);

            Edge edge = new Edge(firstNode, clickedNode);
            model.addEdge(edge);

            view.setGhostEdgeStart(null);
            highlightAvailableNodes(null);

            firstNode = null;
            view.repaint();
        }
    }

    private void handleEmptySpaceClick() {
        if (firstNode != null) {
            firstNode.setSelected(false);
            view.repaint();
        }
        firstNode = null;
        view.setGhostEdgeStart(null);
    }

    /* -------------------- UTILS -------------------- */

    private Node getClickedNode(int x, int y) {
        for (Node n : model.getNodes()) {
            int nodeX = (int) n.getX();
            int nodeY = (int) n.getY();

            if (x >= nodeX - radius && x <= nodeX + radius &&
                    y >= nodeY - radius && y <= nodeY + radius) {
                return n;
            }
        }
        return null;
    }

    private void highlightAvailableNodes(Node selected) {
        Map<Node, Set<Node>> neighbours = model.getNeighbourList();
        Set<Node> available = new HashSet<>();

        if (selected != null) {
            for (Node n : neighbours.keySet()) {
                if (!(neighbours.get(n).contains(selected) || selected == n)) {
                    available.add(n);
                }
            }
            view.highlightAvailable(available);
        } else {
            view.highlightAvailable(new HashSet<>());
        }
    }
}
