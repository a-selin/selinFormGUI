package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.io.*;

public class MainForm extends JFrame {
    private static final String FILE_PATH = "vehicles.txt";

    private JButton addVehicle;
    private JButton removeVehicle;
    private JButton viewSlots;
    private JButton searchButton;
    private JPanel panel1;
    private JLabel labelPlate;
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
        panel1.setBackground(new Color(142, 99, 217));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font biggerFont = new Font("Arial", Font.BOLD, 20);
        Font welcomeFont = new Font("Arial", Font.BOLD, 25);

        timeLabel = new JLabel();
        timeLabel.setBounds(600, 450, 300, 25);
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel1.add(timeLabel);

        labelTitle = new JLabel("Welcome, user.");
        labelTitle.setBounds(300, 60, 600, 50);
        labelTitle.setFont(welcomeFont);
        labelTitle.setForeground(Color.BLACK);
        panel1.add(labelTitle);

        labelPlate = new JLabel("Enter Plate:");
        labelPlate.setBounds(45, 150, 200, 50);
        labelPlate.setFont(biggerFont);
        labelPlate.setForeground(Color.BLACK);
        panel1.add(labelPlate);

        plateField = new JTextField();
        plateField.setBounds(170, 150, 415, 50);
        plateField.setFont(biggerFont);
        plateField.setBackground(new Color(188, 211, 105));
        plateField.setForeground(Color.BLACK);
        panel1.add(plateField);

        addVehicle = new JButton("Add Vehicle");
        addVehicle.setBounds(170, 250, 200, 60);
        addVehicle.setFont(biggerFont);
        addVehicle.setBackground(new Color(188, 211, 105));
        panel1.add(addVehicle);

        removeVehicle = new JButton("Remove Vehicle");
        removeVehicle.setBounds(385, 250, 200, 60);
        removeVehicle.setFont(biggerFont);
        removeVehicle.setBackground(new Color(188, 211, 105));
        panel1.add(removeVehicle);

        viewSlots = new JButton("View Slots");
        viewSlots.setBounds(170, 320, 415, 60);
        viewSlots.setFont(biggerFont);
        viewSlots.setBackground(new Color(188, 211, 105));
        panel1.add(viewSlots);

        searchButton = new JButton("Search");
        searchButton.setBounds(600, 150, 100, 50);
        searchButton.setFont(new Font("Arial", Font.ITALIC, 17));
        searchButton.setBackground(new Color(188, 211, 105));
        panel1.add(searchButton);


        vehicleList = readPlatesFromFile();


        Timer clockTimer = new Timer(1000, e -> {
            java.time.LocalTime now = java.time.LocalTime.now();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
            timeLabel.setText("Saat: " + now.format(formatter));
        });
        clockTimer.start();


        addVehicle.addActionListener(e -> {
            String plate = plateField.getText().trim();
            if (!plate.isEmpty()) {
                vehicleList.add(plate);
                savePlateToFile(plate);
                plateField.setText("");
            }
        });


        removeVehicle.addActionListener(e -> {
            String plate = plateField.getText().trim();
            if (plate.isEmpty()) {
                return;
            }
            boolean removed = removePlateFromFile(plate);
            if (removed) {
                vehicleList = readPlatesFromFile();
            }
        });


        viewSlots.addActionListener(e -> {
            ArrayList<String> list = readPlatesFromFile();
            showVehicleList(list);
        });


        searchButton.addActionListener(e -> {
            String plate = plateField.getText().trim();
            if (plate.isEmpty()) return;

            ArrayList<String> list = readPlatesFromFile();
            boolean found = list.stream().anyMatch(s -> s.equalsIgnoreCase(plate));

        });

        setVisible(true);
    }


    private void savePlateToFile(String plate) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(plate);
            bw.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }


    private ArrayList<String> readPlatesFromFile() {
        ArrayList<String> list = new ArrayList<>();
        File f = new File(FILE_PATH);
        if (!f.exists()) {
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(line.trim());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }


    private boolean removePlateFromFile(String plate) {
        ArrayList<String> list = readPlatesFromFile();
        boolean removed = list.removeIf(s -> s.equalsIgnoreCase(plate));
        if (removed) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String s : list) {
                    bw.write(s);
                    bw.newLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return removed;
    }


    private void showVehicleList(ArrayList<String> list) {
        JFrame f = new JFrame("Vehicle List");
        f.setSize(400, 400);
        f.setLocationRelativeTo(this);
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String s : list) {
            model.addElement(s);
        }
        JList<String> jList = new JList<>(model);
        JScrollPane sp = new JScrollPane(jList);
        f.add(sp);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainForm::new);
    }
}
