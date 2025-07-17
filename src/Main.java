import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame f = new JFrame();

        f.add(new GraphPanel());
        f.setBounds(300, 300, 700, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
