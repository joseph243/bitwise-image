import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<BufferedImage> images;
    private static ArrayList<String> imagesPaths;
    private static BufferedImage outputImage;
    private static ImageEditor editor;
    private static boolean debugMode = false;
    private static final JFrame frame = new JFrame("BitWise Image Editor");
    private static final JPanel imgPanel1 = new JPanel();
    private static final JPanel imgPanel2 = new JPanel();
    private static final JPanel imgPanel3 = new JPanel();
    private static boolean saved = false;
    private static boolean transformed = false;

    public static void main(String[] args) {
        boolean running = true;
        setupNewGUI();
        images = new ArrayList<>();
        imagesPaths = new ArrayList<>();
        outputImage = null;
        editor = new ImageEditor();
        while (running) {
            printMenu(images.size(), transformed, saved);
            String input = scanner.nextLine();
            switch (input) {
                case "q", "quit", "x", "exit":
                    if (!images.isEmpty() && !saved)
                    {
                        System.out.print(">> Warning!!! Unsaved Work.  Really quit?  y/n: ");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("y"))
                        {
                            System.out.println(" Changes Discarded.");
                            running = false;
                        }
                    }
                    else
                    {
                        running = false;
                    }
                    if (!running)
                    {
                        System.out.println("quitting.");
                        frame.dispose();
                    }
                    break;
                case "orify":
                    if (images.size() < 2)
                    {
                        System.out.println(">>must have 2 images loaded to perform 'OR' on them.");
                    }
                    else
                    {
                        outputImage = editor.orify(images);
                        System.out.println(">>OR-ify transformation complete.");
                        transformed = true;
                        saved = false;
                    }
                    break;
                case "andify":
                    if (images.size() < 2)
                    {
                        System.out.println(">>must have 2 images loaded to perform 'AND' on them.");
                    }
                    else
                    {
                        outputImage = editor.andify(images);
                        System.out.println(">>AND-ify transformation complete.");
                        transformed = true;
                        saved = false;
                    }
                    break;
                case "load":
                    loadImage();
                    break;
                case "debug":
                    debugMode = !debugMode;
                    if (debugMode)
                    {
                        System.out.println("Debug Mode ON.");
                    }
                    break;
                case "save":
                    if (outputImage != null)
                    {
                        saved = saveImage();
                    }
                    else
                    {
                        System.out.println("no image is loaded!  use 'load' command.");
                    }
                    if (saved)
                    {
                        images.addFirst(outputImage);
                        imagesPaths.addFirst("Transformed Image");
                        System.out.println("Save Image Success!  Image remains in buffer for further changes.");
                        System.out.println("Use 'reset' to clear buffer.");
                    }
                    break;
                case "half":
                    if (!images.isEmpty())
                    {
                        System.out.println(">> starting 'half' transform on first loaded image.");
                        selectTransformations();
                        outputImage = editor.half(images.getFirst());
                        transformed = true;
                        saved = false;
                        System.out.println(">> 'half' transform complete.");
                    }
                    else
                    {
                        System.out.println("no image is loaded!  use 'load' command.");
                    }
                    break;
                case "full":
                    if (!images.isEmpty())
                    {
                        System.out.println(">> starting 'full' transform.");
                        selectTransformations();
                        outputImage = editor.full(images.getFirst());
                        transformed = true;
                        saved = false;
                        System.out.println(">> 'full' transform complete.");
                        updateGUIImages();
                    }
                    else
                    {
                        System.out.println("no image is loaded!  use 'load' command.");
                    }
                    break;
                case "chaos":
                    if (!images.isEmpty())
                    {
                        System.out.println(">> starting 'chaos' transform.");
                        outputImage = editor.fullChaos(images.getFirst());
                        transformed = true;
                        saved = false;
                        System.out.println(">> 'chaos' transform complete.");
                    }
                    else
                    {
                        System.out.println("no image is loaded!  use 'load' command.");
                    }
                    break;
                case "t":
                    testNumbers();
                    break;
                case "reset":
                    resetActions();
                    break;
                case "list":
                    System.out.println("Loaded Images:");
                    for (String path : imagesPaths)
                    {
                        System.out.println("~~" + path);
                    }
                    System.out.println("Available Transformations:");
                    editor.listAvailableTransformations();
                    break;
                default:
                    System.out.println("----------------------------------------");
                    System.out.println("               HELP MENU");
                    System.out.println("t : test integers.");
                    System.out.println("half: begin half image transform.");
                    System.out.println("full: begin full image transform.");
                    System.out.println("orify: merge 2 images.");
                    System.out.println("chaos: begin full chaos image transform.");
                    System.out.println("load: load image to be transformed.");
                    System.out.println("save: save transformed image to disk.");
                    System.out.println("debug: toggle debug mode.");
                    System.out.println("list: list available transformations.");
                    System.out.println("reset: erase all changes and start over.");
                    System.out.println("q, quit, x, exit: Quit this application.");
                    System.out.println("----------------------------------------");
                    break;
            }
            try
            {
                Thread.sleep(250);
            }
            catch (Exception e)
            {
                //do nothing.
            }
        }

    }

    private static void resetActions() {
        images.clear();
        imagesPaths.clear();
        outputImage = null;
        editor = new ImageEditor();
        debugMode = false;
        saved = false;
        transformed = false;
        updateGUIImages();
        System.out.println(">> data erased, app reset to new launch.");
    }

    private static void setupNewGUI()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton resetButton = new JButton(new AbstractAction("RESET") {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetActions();
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

    private static void selectTransformations()
    {
        String input = "";
        System.out.println("selecting transformations.  type names from this list, or 'done' to continue: ");
        editor.listAvailableTransformations();
        while (!input.equalsIgnoreCase("done"))
        {
            System.out.print(">> add transformation: ");
            input = scanner.nextLine();
            if (!input.equalsIgnoreCase("done"))
            {
                editor.selectTransformation(input);
            }
        }
    }

    private static void testNumbers() {
        System.out.print(">>bitwise test value:");
        String a = scanner.nextLine();
        int aa = 0;
        try
        {
            aa = Integer.parseInt(a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        printInt(aa);
        System.out.println("shifted right 1:");
        printInt(aa >> 1);
        System.out.println("shifted right 16 no mask");
        printInt(aa >> 16);
        System.out.println("AND 0xFF:");
        printInt(aa  & 0xFF );
        System.out.println("OR 0xFF:");
        printInt(aa | 0xFF);
    }

    private static void loadImage()
    {
        File inFile = null;
        System.out.print(">> source image? ");
        String userPath = scanner.nextLine();
        String path = "";
        if (userPath.equalsIgnoreCase("t"))
        {
            path = "/home/joe/Pictures/memes/us.png";
        }
        else
        {
            path = userPath;
        }
        inFile = new File(path);
        try
        {
            images.add(ImageIO.read(inFile));
            imagesPaths.add(path);
            updateGUIImages();
            saved = false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (inFile.exists() && !images.isEmpty())
        {
            System.out.println(">> input file exists: " + path);
            System.out.println(">> load image success: " + images.getFirst().getWidth() + " x " + images.getFirst().getHeight() + " pixels.");
        }
        else
        {
            System.out.println(">> load image failed for : " + path + ". You'll need to run 'load' again to proceed.");
        }
    }

    private static void updateGUIImages()
    {
        if (images.isEmpty())
        {
            imgPanel1.remove(0);
            imgPanel2.remove(0);
            JLabel imageLabel1 = new JLabel("image 1");
            JLabel imageLabel2 = new JLabel("image 2");
            imgPanel1.add(imageLabel1);
            imgPanel2.add(imageLabel2);
        }
        if (!images.isEmpty())
        {
            imgPanel1.remove(0);
            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon(imagesPaths.get(0));
            imageLabel.setIcon(imageIcon);
            imgPanel1.add(imageLabel);
        }
        if (images.size() > 1)
        {
            imgPanel2.remove(0);
            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon(imagesPaths.get(1));
            imageLabel.setIcon(imageIcon);
            imgPanel2.add(imageLabel);
        }
        if (outputImage != null)
        {
            imgPanel3.remove(0);
            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon(outputImage);
            imageLabel.setIcon(imageIcon);
            imgPanel3.add(imageLabel);
        }
        else
        {
            imgPanel3.remove(0);
            JLabel imageLabel = new JLabel("output image");
            imgPanel3.add(imageLabel);
        }
        imgPanel1.revalidate();
        imgPanel1.repaint();
        imgPanel2.revalidate();
        imgPanel2.repaint();
        imgPanel3.revalidate();
        imgPanel3.repaint();
    }

    private static boolean saveImage()
    {
        String outputFolder = "";
        System.out.print(">> output folder? ");
        String userTarget = scanner.nextLine();
        if (userTarget.equalsIgnoreCase("t"))
        {
            outputFolder = "/home/joe/Pictures/bitwise";
        }
        else
        {
            outputFolder = userTarget;
        }
        System.out.print(">> output file name? ");
        String fileName = scanner.nextLine();
        File outFile = new File(outputFolder + "/" + fileName + ".png");
        System.out.println(">> output file:" + outFile.getAbsolutePath());
        boolean result = false;
        try
        {
            result = outFile.createNewFile() && ImageIO.write(outputImage, "png", outFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (result)
        {
            System.out.println(">> save image complete at " + outFile.getAbsolutePath());
            return true;
        }
        return false;
    }

    public static void printInt(int a)
    {
        System.out.print(String.format("%32s", Integer.toBinaryString(a)).replace(' ', '0'));
        System.out.print(" : ");
        System.out.println(a);
    }

    public static boolean isDebugMode()
    {
        return debugMode;
    }

    public static void printMenu(int imageCount, boolean inTransformed, boolean inSaved)
    {
        System.out.println("~~Bitwise Image Editor~~");
        System.out.println("~~~~~" + imageCount + " Images Loaded~~~~");
        if (inTransformed)
        {
            System.out.println("~~Image Is Transformed~~");
            if (inSaved)
            {
                System.out.println("~~~Changes Are Saved!~~~");
            }
            else
            {
                System.out.println("~~~~~Unsaved Changes~~~~");
            }
        }
        else
        {
            System.out.println("~~~~~No Changes Yet~~~~~");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print("enter command: ");
    }
}
