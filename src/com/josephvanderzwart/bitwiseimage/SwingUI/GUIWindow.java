package com.josephvanderzwart.bitwiseimage.SwingUI;

import com.josephvanderzwart.bitwiseimage.Main;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class GUIWindow {

    private static final JFrame frame = new JFrame("BitWise Image Editor");
    private static final JPanel imgPanel1 = new JPanel();
    private static final JPanel imgPanel2 = new JPanel();
    private static final JPanel imgPanel3 = new JPanel();

    public GUIWindow()
    {
        setupNewGUI();
    }

    public void clearInputImages()
    {
        imgPanel1.remove(0);
        imgPanel2.remove(0);
        JLabel imageLabel1 = new JLabel("image 1");
        JLabel imageLabel2 = new JLabel("image 2");
        imgPanel1.add(imageLabel1);
        imgPanel2.add(imageLabel2);
        imgPanel1.revalidate();
        imgPanel1.repaint();
        imgPanel2.revalidate();
        imgPanel2.repaint();
    }

    public void clearOutputImage()
    {
        imgPanel3.remove(0);
        JLabel imageLabel = new JLabel("output image");
        imgPanel3.add(imageLabel);
        imgPanel3.revalidate();
        imgPanel3.repaint();
    }

    public void setImage1(String inPath)
    {
        imgPanel1.remove(0);
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(inPath);
        imageIcon = scaleImage(imageIcon);
        imageLabel.setIcon(imageIcon);
        imgPanel1.add(imageLabel);
        imgPanel1.revalidate();
        imgPanel1.repaint();
    }

    public void setImage2(String inPath)
    {
        imgPanel2.remove(0);
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(inPath);
        imageIcon = scaleImage(imageIcon);
        imageLabel.setIcon(imageIcon);
        imgPanel2.add(imageLabel);
        imgPanel2.revalidate();
        imgPanel2.repaint();
    }

    public void setImageOutput(BufferedImage inImage)
    {
        imgPanel3.remove(0);
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(inImage);
        imageIcon = scaleImage(imageIcon);
        imageLabel.setIcon(imageIcon);
        imgPanel3.add(imageLabel);
        imgPanel3.revalidate();
        imgPanel3.repaint();
    }

    private void setupNewGUI()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton resetButton = new JButton(new AbstractAction("RESET") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.resetActions();
            }
        });
        JLabel imageLabel1 = new JLabel("image 1");
        JLabel imageLabel2 = new JLabel("image 2");
        JLabel imageLabel3 = new JLabel("output image");
        Dimension imageDimension = new Dimension(300,220);
        Border line = BorderFactory.createLineBorder(new Color(0,0,0));
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border compound = BorderFactory.createCompoundBorder(padding, line);
        imgPanel1.setPreferredSize(imageDimension);
        imgPanel1.setBorder(compound);
        imgPanel2.setPreferredSize(imageDimension);
        imgPanel2.setBorder(compound);
        imgPanel3.setPreferredSize(imageDimension);
        imgPanel3.setBorder(compound);
        imgPanel1.add(imageLabel1);
        imgPanel2.add(imageLabel2);
        imgPanel3.add(imageLabel3);
        frame.getContentPane().add(imgPanel1, BorderLayout.WEST);
        frame.getContentPane().add(imgPanel2, BorderLayout.EAST);
        frame.getContentPane().add(imgPanel3);
        frame.getContentPane().add(resetButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(960,480));
        frame.setLocation(100,100);
    }

    private ImageIcon scaleImage(ImageIcon inImageIcon)
    {
        if (inImageIcon.getIconHeight() > 220 || inImageIcon.getIconWidth() > 300)
        {
            Image i = inImageIcon.getImage();
            Image scaled = i.getScaledInstance(278, 200, Image.SCALE_DEFAULT);
            return new ImageIcon(scaled);
        }
        else
        {
            return inImageIcon;
        }
    }

    public void killGUI()
    {
        frame.dispose();
    }
}
