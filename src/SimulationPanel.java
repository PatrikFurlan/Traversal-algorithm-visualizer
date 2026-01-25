import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationPanel extends JPanel {

    // ----- Components that need listeners -----
    private JButton startBtn;
    private JButton clearBtn;
    private JComboBox<String> algoDropdown;
    private JTextField fromField;
    private JTextField toField;
    private JTable table;

    public SimulationPanel() {
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        setBackground(new Color(207, 207, 207));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // ----- Title Label -----
        JLabel titleLab = new JLabel("Graph Analyzer");
        titleLab.setFont(titleLab.getFont().deriveFont(Font.BOLD, 16f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        add(titleLab, gbc);

        // ----- Selected Algorithm Panel -----
        String[] algorithms = {"DFS", "BFS"};
        algoDropdown = new JComboBox<>(algorithms);

        JPanel algoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        algoPanel.add(new JLabel("Selected Algorithm:"));
        algoPanel.add(algoDropdown);

        gbc.gridy = 2;
        add(algoPanel, gbc);

        // ----- From - To JPanel -----
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        fromField = new JTextField(5);
        toField = new JTextField(5);

        JPanel fromToPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        fromToPanel.add(new JLabel("From:"));
        fromToPanel.add(fromField);
        fromToPanel.add(new JLabel("To:"));
        fromToPanel.add(toField);

        add(fromToPanel, gbc);

        // ------ Algorithm trace table -----
        String[] columns = {"Expanded", "Generated"};
        Object[][] data = {};
        table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // --- Buttons Row ---
        startBtn = new JButton("Start");
        clearBtn = new JButton("Clear");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnPanel.add(startBtn);
        btnPanel.add(clearBtn);

        gbc.gridy = 5;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(btnPanel, gbc);
    }

    // ----- Button Listeners -----
    public void addStartBtnListener(ActionListener l) {
        startBtn.addActionListener(l);
    }

    public void addClearListener(ActionListener l) {
        clearBtn.addActionListener(l);
    }

    // ----- Content getters -----
    public String getFromText() {
        return this.fromField.getText().trim();
    }

    public String getToText() {
        return this.toField.getText().trim();
    }

    public String getAlgorithm() {
        return (String) algoDropdown.getSelectedItem();
    }

    public JButton getStartBtn() {
        return startBtn;
    }

}
