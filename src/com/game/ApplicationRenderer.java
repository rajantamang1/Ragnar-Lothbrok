package com.game;

import javax.swing.*;
import java.awt.*;

public class ApplicationRenderer{

    JPanel titlePanel;
    JPanel backgroundPanel;
    JLabel titleLabel;
    JLabel backgroundLabel;
    JFrame window;
    Container con;
    Font titleFont = new Font("Times New Roman", Font.BOLD,70);

    ImageIcon backgroundImage;


    public static void main(String[] args) {
        new ApplicationRenderer();
    }

    public ApplicationRenderer() {

        //backgroundImage = new ImageIcon("C:\\Users\\sanju\\Desktop\\Capstone\\Ragnar-lothbrok\\src\\rag.jpg");


        window = new JFrame();
        window.setSize(1050, 920);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);

        window.setContentPane(new JLabel(backgroundImage));
        //window.setSize(1020, 800);

        con = window.getContentPane();

        titlePanel = new JPanel();
        titlePanel.setBounds(200, 100, 920, 125);
        //titlePanel.setBackground(Color.black);
        titlePanel.setOpaque(false);

        titleLabel = new JLabel("RAGNAR LOTHBROK");
        titleLabel.setForeground(Color.lightGray);
        titleLabel.setFont(titleFont);

        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(0, 0, window.getWidth(), window.getHeight());
        backgroundPanel.setBackground(Color.black);


        backgroundLabel = new JLabel();
        backgroundLabel.setSize(backgroundPanel.getWidth(),backgroundPanel.getHeight());


        backgroundImage = new ImageIcon(new ImageIcon("C:\\Users\\sanju\\Desktop\\Capstone\\Ragnar-lothbrok\\src\\rag.jpg")
                .getImage().getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH));
        backgroundLabel.setIcon(backgroundImage);

        titlePanel.add(titleLabel);
        backgroundPanel.add(backgroundLabel);
        con.add(titlePanel);
        con.add(backgroundPanel);
        window.setVisible(true);
    }

   /* public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        System.out.println("painting...............");
        // Draw the previously loaded image to Component.
        g2D.drawImage(backgroundImage, 0, 0, null);

        // Draw sprites, and other things.
        // ....
    }*/
}