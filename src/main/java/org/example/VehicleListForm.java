package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VehicleListForm extends JFrame {
    private JPanel mainPanel;
    private JTextArea textArea1;
    private JLabel titleLabel;

    public VehicleListForm(ArrayList<String> vehicleList) {
        setTitle("Araç Listesi");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(220, 235, 210));


        titleLabel = new JLabel("EKLENEN ARAÇLAR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);


        textArea1 = new JTextArea();
        textArea1.setFont(new Font("Arial", Font.PLAIN, 24));
        textArea1.setEditable(false);
        textArea1.setBackground(new Color(245, 245, 245));
        textArea1.setMargin(new Insets(20, 20, 20, 20));
        mainPanel.add(new JScrollPane(textArea1), BorderLayout.CENTER);

        int i = 1;
        StringBuilder sb = new StringBuilder();
        for (String item : vehicleList) {
            sb.append(i++).append(". ").append(item).append("\n");
        }
        textArea1.setText(sb.toString());

        add(mainPanel);
        setVisible(true);
    }
}










