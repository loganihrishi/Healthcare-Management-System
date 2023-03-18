package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SaveDataGUI extends StartApplicationGUI{
    private JLabel result;
    private JButton mainMenu = new JButton("Click here to go back");
    private JPanel mainPanel = new JPanel();

    public SaveDataGUI() throws IOException {
        super();
        initialize();
    }

    private void initialize() {
        this.setTitle("Healthcare Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.WHITE);
        this.setSize(new Dimension(dimX, dimY));
        this.add(getMainPanel());
        this.setVisible(true);
    }

    private JPanel getMainPanel() {
        mainPanel.add(getResult());
        mainPanel.add(getMainMenu());
        return mainPanel;
    }

    private JLabel getResult() {
        try {
            patientFile.writePatientsToFile(patients);
            this.result = new JLabel("Data saved successfully!");
        } catch (IOException e) {
            this.result = new JLabel(e.getMessage());
        }
        return this.result;
    }

    private JButton getMainMenu() {
        mainMenu.setFont(new Font("Arial", Font.PLAIN, 20));
        this.mainMenu.setPreferredSize(new Dimension(500, 100));
        this.mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new StartApplicationGUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return mainMenu;
    }
}
