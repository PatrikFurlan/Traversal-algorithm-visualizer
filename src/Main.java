import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        GraphModel model = new GraphModel();
                GraphView view = new GraphView();
        SimulationPanel simPanel = new SimulationPanel();
        AlgorithmCompute algoCompute = new AlgorithmCompute();

        GraphController controller = new GraphController(model, view, simPanel, algoCompute);


        JFrame f = new JFrame();
        f.add(simPanel, BorderLayout.WEST);
        f.add(view, BorderLayout.CENTER);

        f.setSize(1000, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
