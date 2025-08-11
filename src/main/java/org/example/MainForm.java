package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private JButton addVehicle;
    private JButton removeVehicle;
    private JButton viewSlots;
    private JButton searchButton;
    private JPanel panel1;
    private JLabel labelName;
    private JLabel labelPlate;
    private JLabel eventLabel;
    private JTextField nameField;
    private JTextField plateField;
    private JLabel labelTitle;
    private JLabel timeLabel;
    private ArrayList<String> vehicleList = new ArrayList<>();

    public MainForm() {
        setTitle("My GUI Form");
        setSize(800, 600);
        panel1 = new JPanel();
        setContentPane(panel1);
        panel1.setLayout(null);
        panel1.setBackground(new Color(142,99,217));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font biggerFont = new Font("Arial", Font.BOLD, 20);
        Font welcomeFont = new Font("Arial", Font.BOLD, 25);

        timeLabel = new JLabel();
        timeLabel.setBounds(600,450,300,25);
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel1.add(timeLabel);

        labelTitle = new JLabel("Welcome, user.");
        labelTitle.setBounds(300, 60, 600, 50);
        labelTitle.setFont(welcomeFont);
        panel1.add(labelTitle);
        labelTitle.setForeground(Color.BLACK);

        labelPlate = new JLabel("Enter Plate:");
        labelPlate.setBounds(45, 150, 200, 50);
        labelPlate.setFont(biggerFont);
        panel1.add(labelPlate);
        labelPlate.setForeground(Color.BLACK);

        plateField = new JTextField();
        plateField.setBounds(170, 150, 415, 50);
        plateField.setFont(biggerFont);
        plateField.setBackground(new Color(188,211,105));
        plateField.setForeground(Color.BLACK);
        panel1.add(plateField);

        addVehicle = new JButton("Add Vehicle");
        addVehicle.setBounds(170, 250, 200, 60);
        addVehicle.setFont(biggerFont);
        panel1.add(addVehicle);
        addVehicle.setBackground(new Color(188,211,105));

        removeVehicle = new JButton("Remove Vehicle");
        removeVehicle.setBounds(385, 250, 200, 60);
        removeVehicle.setFont(biggerFont);
        panel1.add(removeVehicle);
        removeVehicle.setBackground(new Color(188,211,105));

        viewSlots = new JButton("View Slots");
        viewSlots.setBounds(170, 320, 415, 60);
        viewSlots.setFont(biggerFont);
        panel1.add(viewSlots);
        viewSlots.setBackground(new Color(188,211,105));

        searchButton = new JButton("Search");
        searchButton.setBounds(600, 150, 100, 50);
        searchButton.setFont(new Font("Arial", Font.ITALIC, 17));
        panel1.add(searchButton);
        searchButton.setBackground(new Color(188,211,105));

        eventLabel = new JLabel("");
        eventLabel.setBounds(45, 350, 500, 30);
        eventLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        eventLabel.setForeground(Color.BLACK);
        panel1.add(eventLabel);

        Timer clockTimer = new Timer(1000, e -> {
            java.time.LocalTime now = java.time.LocalTime.now();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
            timeLabel.setText("Saat: " + now.format(formatter));
        });
        clockTimer.start();

        addVehicle.addActionListener(e -> {
            String name = nameField.getText().trim();
            String plate = plateField.getText().trim();

            if (!name.isEmpty() && !plate.isEmpty()) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate = today.format(formatter);
                vehicleList.add(name + " - " + plate + " - " + formattedDate);
                eventLabel.setText("Araç eklendi: " + name + " - " + plate + " - " + formattedDate);

            } else {
                eventLabel.setText("Lütfen tüm alanları doldurun.");
            }
        });

        SearchPanel searchPanel = new SearchPanel(vehicleList);
        searchPanel.setBounds(20, 400, 750, 150);
        panel1.add(searchPanel);

        int shortcutKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_S, shortcutKey), "saveAction");

        this.getRootPane().getActionMap().put("saveAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPanel.saveToFile();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainForm();
    }
}
