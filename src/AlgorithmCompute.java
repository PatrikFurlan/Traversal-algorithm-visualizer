import java.lang.reflect.Array;
import java.util.*;

public class AlgorithmCompute {

    private String algorithm;
    private Node from;
    private Node to;
    private Map<Node, Set<Node>> neighboursList;

    public AlgorithmCompute() {

    }

    public ArrayList<Node> simulate(String algorithm, String from, String to, Map<Node, Set<Node>> neighboursList) {
        this.algorithm = algorithm;
        this.neighboursList = neighboursList;

        // Find from and to nodes
        for (Node n : neighboursList.keySet()) {
            if (n.getLabel().equals(from)) {
                this.from = n;
            } else if (n.getLabel().equals(to)) {
                this.to = n;
            }
        }

        return switch (algorithm) {
            case "DFS" -> dfs(this.from, this.to);
            case "BFS" -> bfs();
            default -> new ArrayList<>();
        };
    }

    public ArrayList<Node> dfs(Node current, Node end) {
        Set<Node> visited = new HashSet<>();
        ArrayList<Node> path = new ArrayList<>();
        if (dfsRec(current, end, visited, path)) {
            return path;
        }
        return new ArrayList<>();
    }

    private boolean dfsRec(Node current, Node end, Set<Node> visited, ArrayList<Node> res) {
        visited.add(current);
        res.add(current);

        if (current.equals(end)) {
            return true;
        }
        for (Node n : neighboursList.get(current)) {
            if (!visited.contains(n)) {
                if (dfsRec(n, end, visited, res)) {
                    return true;
                }
            }
        }
        res.remove(res.size() - 1);
        return false;
    }

    public ArrayList<Node> bfs() {
        return new ArrayList<>();
    }
}
