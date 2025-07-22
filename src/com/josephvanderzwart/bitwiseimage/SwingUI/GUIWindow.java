package com.josephvanderzwart.bitwiseimage.SwingUI;

import com.josephvanderzwart.bitwiseimage.ImageEditor;
import com.josephvanderzwart.bitwiseimage.Main;
import com.josephvanderzwart.bitwiseimage.colorShifts.ColorShiftBase;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GUIWindow {

    private static final JFrame frame = new JFrame("BitWise Image Editor");
    private static final JPanel primaryPanel = new JPanel(new GridLayout(3,3));
    private static final JPanel imgPanel1 = new JPanel();
    private static final JPanel imgPanel2 = new JPanel();
    private static final JPanel imgPanel3 = new JPanel();
    private static final JPanel panel4 = new JPanel();
    private static final JPanel panel5 = new JPanel(new BorderLayout());
    private static final JPanel panel6 = new JPanel(new BorderLayout());
    private static final JPanel panel7 = new JPanel(new BorderLayout());
    private static final JPanel panel8 = new JPanel(new BorderLayout());
    private static final JPanel panel9 = new JPanel(new BorderLayout());
    private static final JTextField filePathInputField = new JTextField("/home/joe/Pictures/");
    private static final JLabel fileLoadResponse = new JLabel();
    private static final Dimension imageDimension = new Dimension(300,220);
    private static final Dimension appDimension = new Dimension(900,900);
    private static JButton resetButton = new JButton();
    private static JButton loadButton = new JButton();
    private static ArrayList<JButton> transformButtons = new ArrayList<>();

    public GUIWindow()
    {
        buildButtons();
        buildTransformButtons();
        setupGUIBase();
        setupGUIImagePanels();
        resetListOfActiveTransformations();
        setupGUITransformButtonsPanel();
        setupGUIExtraPanels();
        setupGUILoadPanel();
        setupGUIResetPanel();
        setupGUIFinish();
    }

    private void setupGUITransformButtonsPanel()
    {
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
        for (JButton b : transformButtons)
        {
            panel5.add(b);
        }
    }

    private void buildTransformButtons()
    {
        for (ColorShiftBase c : Main.getAvailableTransformations())
        {
            JButton button = new JButton(new AbstractAction(c.getName()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.addTransform(c.getName());
                }
            });
            transformButtons.add(button);
        }
    }

    private void buildButtons()
    {
        resetButton = new JButton(new AbstractAction("RESET") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.resetActions();
            }
        });
        loadButton = new JButton(new AbstractAction("LOAD IMAGE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadActions();
            }
        });
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

    private void setupGUIBase()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(appDimension);
        frame.setLocation(100,100);
        primaryPanel.setLayout(new GridLayout(3,3));
        frame.add(primaryPanel);
    }

    private void setupGUIImagePanels()
    {
        JLabel imageLabel1 = new JLabel("image 1 not loaded");
        JLabel imageLabel2 = new JLabel("image 2 not loaded");
        JLabel imageLabel3 = new JLabel("output image");
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
    }

    private void setupGUILoadPanel()
    {
        filePathInputField.setSize(250, 25);
        filePathInputField.setMaximumSize(new Dimension(250, 25));
        filePathInputField.setMinimumSize(new Dimension(250, 25));
        filePathInputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        fileLoadResponse.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        panel4.add(filePathInputField);
        panel4.add(loadButton);
        panel4.add(fileLoadResponse);
    }

    private void setupGUIExtraPanels()
    {
        panel7.add(new JLabel("7"), BorderLayout.CENTER);
        panel8.add(new JLabel("8"), BorderLayout.CENTER);
        panel9.add(new JLabel("9"), BorderLayout.CENTER);
    }

    private void setupGUIResetPanel()
    {
        panel7.add(resetButton, BorderLayout.SOUTH);
    }

    private void setupGUIFinish()
    {
        primaryPanel.add(imgPanel1);
        primaryPanel.add(imgPanel2);
        primaryPanel.add(imgPanel3);
        primaryPanel.add(panel4);
        primaryPanel.add(panel5);
        primaryPanel.add(panel6);
        primaryPanel.add(panel7);
        primaryPanel.add(panel8);
        primaryPanel.add(panel9);
    }

    private ImageIcon scaleImage(ImageIcon inImageIcon)
    {
        if (inImageIcon.getIconHeight() > imageDimension.height || inImageIcon.getIconWidth() > imageDimension.width)
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

    public void setListOfActiveTransformations(ArrayList<ColorShiftBase> inTransformations)
    {
        String textList = "<html>";
        for (ColorShiftBase c : inTransformations)
        {
            textList += c.getName();
            textList += "<br>";
        }
        textList += "</html>";
        JLabel label = new JLabel(textList);
        panel6.removeAll();
        panel6.add(label, BorderLayout.CENTER);
        panel6.revalidate();
        panel6.repaint();
    }

    public void resetListOfActiveTransformations()
    {
        JLabel label = new JLabel("");
        panel6.removeAll();
        panel6.add(label);
        panel6.revalidate();
        panel6.repaint();
    }

    public void killGUI()
    {
        frame.dispose();
    }

    private void loadActions()
    {
        filePathInputField.setVisible(true);
        boolean success = Main.loadImage(filePathInputField.getText());
        if (!success)
        {
            fileLoadResponse.setText("File load failed .... try again.");
        }
        else
        {
            fileLoadResponse.setText("");
        }
    }
}
