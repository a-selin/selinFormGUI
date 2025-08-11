package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

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

        JToolBar toolBar = new JToolBar();

        JMenuItem view = new JMenuItem("View Slots");
        JMenuItem addMenuItem = new JMenuItem("Add Slot");
        JMenuItem remove = new JMenuItem("Remove Slot");
        JMenuItem exit = new JMenuItem("Exit");

        JMenu files = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");
        JMenuItem settings = new JMenuItem("Settings");
        files.add(view);
        edit.add(addMenuItem);
        edit.add(remove);
        help.add(exit);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(files);
        menuBar.add(edit);
        menuBar.add(help);
        menuBar.setBackground(new Color(188,211,105));
        toolBar.setBackground(new Color(188, 211, 105));

        addMenuItem.addActionListener(e -> {
            String plate = plateField.getText().trim();
            if (!plate.isEmpty()) {
                vehicleList.add(plate);
                savePlateToFile(plate);
                plateField.setText("");
            }
        });

        remove.addActionListener(e -> {
            String plate = plateField.getText().trim();
            if (plate.isEmpty()) {
                return;
            }
            boolean removed = removePlateFromFile(plate);
            if (removed) {
                vehicleList = readPlatesFromFile();
            }
        });

        view.addActionListener(e -> {
            ArrayList<String> list = readPlatesFromFile();
            showVehicleList(list);
        });

        exit.addActionListener(e -> {
            System.exit(0);
        });



        panel1.setLayout(new BorderLayout());
        panel1.add(menuBar, BorderLayout.NORTH);
        panel1.add(toolBar, BorderLayout.SOUTH);


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

        searchButton.addActionListener(e -> openSearchWindow());

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


    private void openSearchWindow() {
        JFrame searchFrame = new JFrame("Search Vehicles");
        searchFrame.setSize(400, 400);
        searchFrame.setLocationRelativeTo(this);
        searchFrame.setLayout(null);

        JTextField searchField = new JTextField();
        searchField.setBounds(20, 20, 250, 30);
        searchFrame.add(searchField);

        JButton searchActionButton = new JButton("Search");
        searchActionButton.setBounds(280, 20, 90, 30);
        searchFrame.add(searchActionButton);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> resultList = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(resultList);
        scrollPane.setBounds(20, 70, 350, 250);
        searchFrame.add(scrollPane);

        searchActionButton.addActionListener(ev -> {
            String query = searchField.getText().trim();
            model.clear();

            if (query.isEmpty()) return;

            ArrayList<String> list = readPlatesFromFile();
            for (String plate : list) {
                if (matchesPattern(plate, query)) {
                    model.addElement(plate);
                }
            }
        });

        searchFrame.setVisible(true);
    }


    private boolean matchesPattern(String plate, String pattern) {
        String lowerPlate = plate.toLowerCase();
        String lowerPattern = pattern.toLowerCase();

        if (lowerPattern.startsWith("%") && lowerPattern.endsWith("%")) {
            String keyword = lowerPattern.substring(1, lowerPattern.length() - 1);
            return lowerPlate.contains(keyword);
        } else if (lowerPattern.startsWith("%")) {
            String keyword = lowerPattern.substring(1);
            return lowerPlate.endsWith(keyword);
        } else if (lowerPattern.endsWith("%")) {
            String keyword = lowerPattern.substring(0, lowerPattern.length() - 1);
            return lowerPlate.startsWith(keyword);
        } else {
            return lowerPlate.equals(lowerPattern);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainForm::new);
    }
}
