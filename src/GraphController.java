import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.*;

public class GraphController {

    private final GraphModel model;

    // View layer
    private final GraphView view;
    private final SimulationPanel simPanel;

    // Control layer
    private final AlgorithmCompute algoCompute;

    private Node firstNode = null;
    private Node draggedNode = null;

    private int radius = 30;
    private boolean simulation = false;
    private int simulationStep = 1;
    private ArrayList<Node> algorithmPath;
    private ArrayList<Node> algorithmVisited;
    private ArrayList<Node> edgeNodes = new ArrayList<>(); // A tuple holding both edge nodes (from, to)

    // Algorithm backtracking jazz
    private Node branchEnd = null; // Starting node of an alternative path branch in DFS and others (when ready)
    private Color branchColor = Color.RED;
    private Stack<Node> branchNodesStack = new Stack<>();

    public GraphController(GraphModel model, GraphView view, SimulationPanel simPanel, AlgorithmCompute algoCompute) {
        this.model = model;
        this.view = view;
        this.simPanel = simPanel;
        this.algoCompute = algoCompute;

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
        addSimulationPanelListeners();
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
                        if (clicked == null && !simulation) {
                            handleCreateNode(e);
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if (!simulation) {
                        handleRightClick(e);
                    }
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
        if (Node.getNodesDropped() < 26) {
            Node n = new Node(e.getX(), e.getY());
            model.addNode(n);
        }
    }

    private void handleRightClick(MouseEvent e) {
        if (!simulation) {
            Node clickedNode = getClickedNode(e.getX(), e.getY());

            if (clickedNode != null) {
                handleNodeClick(clickedNode);
            } else {
                handleEmptySpaceClick();
            }
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
            firstNode.setSelected(true, Color.RED);

            highlightAvailableNodes(firstNode);

            view.repaint();
        } else if (firstNode != clickedNode) {
            firstNode.setSelected(false, Color.BLACK);

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
            firstNode.setSelected(false, Color.BLACK);
            view.repaint();
        }
        firstNode = null;
        highlightAvailableNodes(null);
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

    /* -------------------- SIMULATION PANEL CONTROLS -------------------- */

    private void addSimulationPanelListeners() {
        simPanel.addStartBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!simulation) startSimulation();
                else nextSimulationStep();

            }
        });

        simPanel.addClearListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearGraph();
            }
        });
    }

    public void clearGraph() {
        simulation = false;

        simulationStep = 1;
        simPanel.getStartBtn().setText("Start");
        unselectNodes();
        unselectEdges();

        edgeNodes = new ArrayList<>();
        model.getNeighbourList().clear();
        Node.setNodesDropped(0);
        model.getNodes().clear();
        model.getEdges().clear();
        model.notifyObservers();
        view.repaint();
    }

    public void startSimulation() {
        simulation = true;

        Map<Node, Set<Node>> neighbourList = model.getNeighbourList();
        String from = simPanel.getFromText().toUpperCase();
        String to = simPanel.getToText().toUpperCase();
        String algorithm = simPanel.getAlgorithm();

        ArrayList<ArrayList<Node>> algorithmTuple = algoCompute.simulate(algorithm, from, to, neighbourList);

        algorithmPath = algorithmTuple.get(0);      // Unpackage path and visited from the algorithms tuple
        algorithmVisited = algorithmTuple.get(1);

        System.out.println(algorithmPath);
        nextSimulationStep();
    }

    public void nextSimulationStep() {
//        if (simulationStep <= algorithmPath.size()) {
//            simPanel.getStartBtn().setText(String.format("Step (%d / %d)", simulationStep, algorithmPath.size()));
//
//            // Color selected node in view
//            algorithmPath.get(simulationStep - 1).setSelected(true);
//
//            // Color selected edges
//            edgeNodes.add(algorithmPath.get(simulationStep - 1));
//
//            if (simulationStep >= 2) {
//                Node from = edgeNodes.get(0);
//                Node to = edgeNodes.get(1);
//                selectEdge(from, to);
//
//                edgeNodes.remove(from);
//            }
//            simulationStep++;
//            view.repaint();
//        } else {
//            unselectNodes();
//            unselectEdges();
//
//            edgeNodes = new ArrayList<>();
//            simulation = false;
//            algorithmPath = new ArrayList<>();
//            simulationStep = 1;
//            simPanel.getStartBtn().setText("Start");
//        }

        if (simulationStep <= algorithmVisited.size()) {
            simPanel.getStartBtn().setText(String.format("Step (%d / %d)", simulationStep, algorithmVisited.size()));

            // Get current node (node on this step)
            Node curr = algorithmVisited.get(simulationStep - 1);
            Node prev;

            boolean isOnPath = algorithmPath.contains(curr);
            branchColor = isOnPath ? Color.RED : Color.BLUE;

            // If the current node isnt on the final path, switch color to BLUE. (NOT STRICTLY NECESSARY FOR NOW). When current isn't on the final path, push it to the nodes stack and color it BLUE.
            // If the stack already contains the current node, then that means that we have backtracked into it. Pop the current node and unselect it.

            if (simulationStep >= 2) {
               prev = algorithmVisited.get(simulationStep - 2);
            }

            if (!isOnPath) {
                if (branchNodesStack.contains(curr)) { // Backtracking
                    branchNodesStack.pop().setSelected(false, Color.BLACK);
                    //Unselect nodes and edges (Also pop edges)

                } else {
                    branchNodesStack.push(curr);
                    curr.setSelected(true, Color.BLUE);


                }
            } else {
                // When you converge back into a branch clear stack and unselect all BLUE nodes
                // TODO This is not optimal since the last element still stays in the stack. This should be resolved by a smarter approach to popping
                unselectNodes(Color.blue);
                branchNodesStack = new Stack<>();

                curr.setSelected(true, Color.RED);
            }

            simulationStep++;
            view.repaint();
        } else {
            unselectNodes();
            unselectEdges();

            branchEnd = null; // Reset branch color and start when branches converge
            branchColor = Color.RED;

            branchNodesStack = new Stack<>();

            edgeNodes = new ArrayList<>();
            simulation = false;
            algorithmPath = new ArrayList<>();
            simulationStep = 1;
            simPanel.getStartBtn().setText("Start");
        }
    }

    private void selectEdge(Node from, Node to) {
        for (Edge e : model.getEdges()) {
            if (e.getFrom().equals(from) && e.getTo().equals(to) || e.getFrom().equals(to) && e.getTo().equals(from)) {
                e.setSelected(true, Color.RED);
            }
        }
    }

    private void selectEdge(Node from, Node to, Color color) {
        for (Edge e : model.getEdges()) {
            if (e.getFrom().equals(from) && e.getTo().equals(to) || e.getFrom().equals(to) && e.getTo().equals(from)) {
                e.setSelected(true, color);
            }
        }
    }

    private void unselectEdges() {
        for (Edge e : model.getEdges()) {
            e.setSelected(false, Color.BLACK);
        }
    }

    private void unselectEdges(Color color) {
        for (Edge e : model.getEdges()) {
            if (e.getColor().equals(color)) {
                e.setSelected(false, Color.BLACK);
            }
        }
    }

    private void unselectNodes() {
        for (Node n : model.getNodes()) {
            n.setSelected(false, Color.BLACK); // Unselect all nodes after algorithm is done (color black)
            view.repaint();
        }
    }

    private void unselectNodes(Color color) {
        for (Node n : model.getNodes()) {
            if (n.getColor().equals(color)){
                n.setSelected(false, Color.BLACK); // Unselect nodes of a specified color
            }
            view.repaint();
        }
    }
}
