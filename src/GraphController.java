import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphController {

    private GraphModel model;
    private GraphView view;

    public GraphController(GraphModel model, GraphView view) {
        this.model = model;
        this.view = view;

        model.addObserver(new GraphModelListener() {
            @Override
            public void modelChanged() {
                view.addNodes(model.getNodes());
            }
        });


        this.view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    Node n = new Node(e.getX(), e.getY());
                    model.addNode(n);
                }

            }
        });
    }
}
