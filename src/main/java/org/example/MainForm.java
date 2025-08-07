package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;


public class MainForm extends JFrame {
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

        timeLabel = new JLabel();
        timeLabel.setBounds(600,450,300,25);
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel1.add(timeLabel);


        labelTitle = new JLabel("Hoşgeldiniz, lütfen adınızı ve plaka numaranızı giriniz.");
        labelTitle.setBounds(60, 30, 600, 50);
        labelTitle.setFont(biggerFont);
        panel1.add(labelTitle);
        labelTitle.setForeground(Color.BLACK);

        labelName = new JLabel("Enter name: ");
        labelName.setBounds(60, 120, 200, 50);
        labelName.setFont(biggerFont);
        panel1.add(labelName);
        labelName.setForeground(Color.BLACK);

        nameField = new JTextField();
        nameField.setBounds(280, 120, 400, 50);
        nameField.setFont(biggerFont);
        panel1.add(nameField);
        nameField.setBackground(new Color(188,211,105));
        nameField.setForeground(Color.BLACK);

        labelPlate = new JLabel("Enter Plate:");
        labelPlate.setBounds(60, 200, 200, 50);
        labelPlate.setFont(biggerFont);
        panel1.add(labelPlate);
        labelPlate.setForeground(Color.BLACK);

        plateField = new JTextField();
        plateField.setBounds(280, 200, 400, 50);
        plateField.setFont(biggerFont);
        panel1.add(plateField);
        plateField.setBackground(new Color(188,211,105));
        plateField.setForeground(Color.BLACK);

        searchButton = new JButton("Ekle");
        searchButton.setBounds(280, 300, 200, 60);
        searchButton.setFont(biggerFont);
        panel1.add(searchButton);
        searchButton.setBackground(new Color(188,211,105));

        eventLabel = new JLabel(" Events: ");
        eventLabel.setBounds(60, 400, 600, 50);
        eventLabel.setFont(biggerFont);
        panel1.add(eventLabel);
        eventLabel.setForeground(Color.BLACK);

        Timer clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.time.LocalTime now = java.time.LocalTime.now();
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
                timeLabel.setText("Saat: " + now.format(formatter));
            }
        });
        clockTimer.start();


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventLabel.setText("Butona tıklandı ve text değeri eklendi");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String plate = plateField.getText();

                if (!name.isEmpty() && !plate.isEmpty()) {
                    vehicleList.add(name + " - " + plate);
                    eventLabel.setText("Butona tıklandı ve araç eklendi");
                    new VehicleListForm(vehicleList);
                } else {
                    eventLabel.setText("Lütfen tüm alanları doldurun.");
                }
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainForm();
    }
}
