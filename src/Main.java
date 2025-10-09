public class Main {
    public static void main(String[] args) {
        GraphModel model = new GraphModel();
        GraphView view = new GraphView();

        GraphController controller = new GraphController(model, view);
    }
}
