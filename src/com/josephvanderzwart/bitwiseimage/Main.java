package com.josephvanderzwart.bitwiseimage;

import com.josephvanderzwart.bitwiseimage.SwingUI.GUIWindow;
import com.josephvanderzwart.bitwiseimage.colorShifts.ColorShiftBase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<BufferedImage> images;
    private static ArrayList<String> imagesPaths;
    private static BufferedImage outputImage;
    private static ImageEditor editor = new ImageEditor();
    private static boolean debugMode = false;
    private static boolean saved = false;
    private static boolean transformed = false;
    private static GUIWindow gui;

    public static void main(String[] args) {
        boolean running = true;
        gui = new GUIWindow();
        images = new ArrayList<>();
        imagesPaths = new ArrayList<>();
        outputImage = null;
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
                        gui.killGUI();
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
                        updateGUIImages();
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
                        updateGUIImages();
                        transformed = true;
                        saved = false;
                    }
                    break;
                case "load":
                    System.out.print(">> source image? ");
                    String userPath = scanner.nextLine();
                    loadImage(userPath);
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
                        updateGUIImages();
                    }
                    else
                    {
                        System.out.println("no image is loaded!  use 'load' command.");
                    }
                    break;
                case "full":
                    fullTransformAction();
                    break;
                case "chaos":
                    if (!images.isEmpty())
                    {
                        System.out.println(">> starting 'chaos' transform.");
                        outputImage = editor.fullChaos(images.getFirst());
                        transformed = true;
                        saved = false;
                        System.out.println(">> 'chaos' transform complete.");
                        updateGUIImages();
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

    public static void fullTransformAction()
    {
        if (!images.isEmpty() && outputImage == null)
        {
            System.out.println(">> starting 'full' transform.");
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
    }

    public static void resetActions() {
        images.clear();
        imagesPaths.clear();
        outputImage = null;
        editor = new ImageEditor();
        debugMode = false;
        saved = false;
        transformed = false;
        updateGUIImages();
        gui.resetListOfActiveTransformations();
        System.out.println(">> data erased, app reset to new launch.");
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
                boolean valid = editor.selectTransformation(input);
                if (valid)
                {
                    gui.setListOfActiveTransformations(editor.getSelectedTransformations());
                }
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

    public static boolean loadImage(String inPath)
    {
        File inFile = null;
        String path = "";
        if (inPath.equalsIgnoreCase("t"))
        {
            path = "/home/joe/Pictures/memes/us.png";
        }
        else
        {
            path = inPath;
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
            return true;
        }
        else
        {
            System.out.println(">> load image failed for : " + path + ". You'll need to run 'load' again to proceed.");
            return false;
        }
    }

    private static void updateGUIImages()
    {
        if (images.isEmpty())
        {
            gui.clearInputImages();
        }
        if (!images.isEmpty())
        {
            gui.setImage1(imagesPaths.get(0));
        }
        if (images.size() > 1)
        {
            gui.setImage2(imagesPaths.get(1));
        }
        if (outputImage != null)
        {
            gui.setImageOutput(outputImage);
        }
        else
        {
            gui.clearOutputImage();
        }
    }

    public static ArrayList<ColorShiftBase> getAvailableTransformations()
    {
        return editor.getAvailableTransformations();
    }

    public static void addTransform(String inId)
    {
        editor.selectTransformation(inId);
        gui.setListOfActiveTransformations(editor.getSelectedTransformations());
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
