import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static BufferedImage image = null;

    public static void main(String[] args) {
        boolean running = true;
        boolean saved = false;
        boolean transformed = false;
        System.out.println("~~Bitwise Image Editor~~");

        while (running) {
            boolean imageLoaded = (image != null);
            if (imageLoaded)
            {
                System.out.print("~~image loaded~~");
            }
            if (transformed)
            {
                System.out.print("~~image transformed~~");
            }
            if (saved)
            {
                System.out.print("~~image saved~~");
            }
            System.out.println();
            System.out.print("enter command:");
            String input = scanner.nextLine();
            switch (input) {
                case "q":
                case "quit":
                case "x":
                case "exit":
                    if (imageLoaded && !saved)
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
                        System.out.println("quitting.");
                    }
                    break;
                case "load":
                    loadImage();
                    break;
                case "save":
                    saved = saveImage();
                    break;
                case "half":
                    if (imageLoaded)
                    {
                        System.out.println(">> running 'half' transform.");
                        image = ImageEditor.half(image);
                        transformed = true;
                    }
                    else
                    {
                        System.out.println("no image is loaded!  use 'load' command.");
                    }
                    break;
                case "t":
                    testNumbers();
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
            image = ImageIO.read(inFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (inFile.exists() && image != null)
        {
            System.out.println(">> input file exists: " + path);
            System.out.println(">> load image success: " + image.getWidth() + " x " + image.getHeight() + " pixels.");
        }
        else
        {
            System.out.println(">> load image failed for : " + path + ". You'll need to run 'load' again to proceed.");
        }
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
            System.out.println(">> output file create: " + outFile.createNewFile());
            result = ImageIO.write(image, "png", outFile);
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

    private static void printInt(int a)
    {
        System.out.print(String.format("%32s", Integer.toBinaryString(a)).replace(' ', '0'));
        System.out.print(" : ");
        System.out.println(a);
    }
}
