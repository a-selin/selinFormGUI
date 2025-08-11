package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SearchPanel extends JPanel {
    private JTextField dateField;
    private JButton searchButton;
    private JLabel statusLabel;
    private ArrayList<String> vehicleList;

    public SearchPanel(ArrayList<String> vehicleList) {
        this.vehicleList = vehicleList;

        setLayout(null);
        setBounds(20, 400, 750, 150);
        setBackground(new Color(200, 210, 240));

        JLabel dateLabel = new JLabel("Date (DD.MM.YYYY):");
        dateLabel.setBounds(20, 20, 200, 30);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(220, 20, 200, 30);
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(dateField);

        searchButton = new JButton("Search");
        searchButton.setBounds(430, 20, 100, 30);
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setBackground(new Color(180, 200, 120));
        add(searchButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(20, 70, 500, 30);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(Color.BLACK);
        add(statusLabel);

        searchButton.addActionListener(e -> searchByDate());
    }

    private void searchByDate() {
        String searchDate = dateField.getText().trim();
        if (vehicleList.isEmpty()) {
            statusLabel.setText("No records added yet.");
            return;
        }

        if (!searchDate.isEmpty()) {
            ArrayList<String> filtered = new ArrayList<>();
            for (String v : vehicleList) {
                String[] parts = v.split("-");
                if (parts.length >= 3) {
                    String datePart = parts[2].trim();
                    if (datePart.equals(searchDate)) {
                        filtered.add(v);
                    }
                }
            }
            if (!filtered.isEmpty()) {
                new VehicleListForm(filtered);
                statusLabel.setText(filtered.size() + " Record found.");
            } else {
                statusLabel.setText("No record found.");
            }
        } else {
            statusLabel.setText("Please enter date.");
        }
    }


  public void saveToFile() {
      try (FileWriter writer = new FileWriter("vehicles.txt")) {
            for (String v : vehicleList) {
                writer.write(v + "\n");
            }
            statusLabel.setText("Records saved to file.");
            System.out.println("Dosya kaydedildi: " + new java.io.File("vehicles.txt").getAbsolutePath());

        } catch (IOException ex) {
            statusLabel.setText("An error occurred while saving the file.");
            ex.printStackTrace();
        }
    }

}
