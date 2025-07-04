import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        String outputFolder = "/home/joe/Pictures/bitwise";
        File inFile = null;
        File outFile = null;

        System.out.println("~~Bitwise Image Editor~~");
        while (running) {
            System.out.print("enter command:");
            String input = scanner.nextLine();

            switch (input) {
                case "q":
                    running = false;
                    System.out.println("quitting.");
                    break;
                case "load":
                    System.out.print(">> new file name? ");
                    String targetName = scanner.nextLine();
                    String path = "/home/joe/Pictures/memes/us.png";
                    System.out.println(">> input file:" + path);
                    inFile = new File(path);
                    outFile = new File(outputFolder + "/" + targetName + ".png");
                    System.out.println(">> input file exists: " + inFile.exists());
                    System.out.println(">> output file:" + outFile.getAbsolutePath());

                    try
                    {
                        System.out.println(">> output file create: " + outFile.createNewFile());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println(">> file load complete.");
                    break;
                case "bit":
                    System.out.println(">>bit");
                    Bitwise.bitHalf(inFile, outFile);
                    break;
            }
        }

    }
}
