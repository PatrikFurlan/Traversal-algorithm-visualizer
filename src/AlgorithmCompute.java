import java.util.*;

public class AlgorithmCompute {

    private String algorithm;
    private Node from;
    private Node to;
    private Map<Node, Set<Node>> neighboursList;

    public AlgorithmCompute() {

    }

    public ArrayList<ArrayList<Node>> simulate(String algorithm, String from, String to, Map<Node, Set<Node>> neighboursList) {
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
            //case "BFS" -> bfs(this.from, this.to);
            default -> new ArrayList<>();
        };
    }

    public ArrayList<ArrayList<Node>> dfs(Node current, Node end) {
        ArrayList<Node> visited = new ArrayList<>();
        ArrayList<Node> path = new ArrayList<>();
        if (dfsRec(current, end, visited, path)) {
            System.out.println(visited);
            ArrayList<ArrayList<Node>> resultsTuple = new ArrayList<>(); // package results (path, visited) to a "tuple"
            resultsTuple.add(path);
            resultsTuple.add(visited);

            return resultsTuple;
        }
        return new ArrayList<>();
    }

    private boolean dfsRec(Node current, Node end, List<Node> visited, ArrayList<Node> res) {
        visited.add(current);
        res.add(current);

        if (current.equals(end)) {
            return true;
        }
        for (Node n : neighboursList.get(current)) {
            if (!visited.contains(n)) {
                if (dfsRec(n, end, visited, res)) {
                    return true;
                } else visited.add(current); // If unsuccessful the node backtracks to the node it originated from
            }
        }
        res.removeLast();
        return false;
    }

//    private boolean dfsRec(Node current, Node end, List<Node> visited, ArrayList<Node> res) {
//        visited.add(current);
//        res.add(current);
//
//        if (current.equals(end)) {
//            return true;
//        }
//        for (Node n : neighboursList.get(current)) {
//            if (!visited.contains(n)) {
//                if (dfsRec(n, end, visited, res)) {
//                    return true;
//                }
//            }
//        }
//        res.remove(res.size() - 1);
//        return false;
//    }

    public Object[] bfs(Node current, Node end) {
        Set<Node> visited = new HashSet<>();
        ArrayList<Node> path = new ArrayList<>();

        Queue<Node> q = new ArrayDeque<>();
        q.add(current);

        while (!q.isEmpty()) {
            Node front = q.remove(); // Dequeue first element
            visited.add(front); // First element is visited

            for (Node n : neighboursList.get(front)) {  // Add all UNVISITED neighbours to the queue
                if (!visited.contains(n)) {
                    q.add(n);
                }
            }



        }
        return new Object[]{new ArrayList<>()};
    }


}
