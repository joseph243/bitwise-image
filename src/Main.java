import java.io.File;
import java.io.OutputStream;
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
                    String path = "/home/joe/Pictures/land2024/camp4.JPG";
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
                case "test":
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

    private static void printInt(int a)
    {
        System.out.print(String.format("%32s", Integer.toBinaryString(a)).replace(' ', '0'));
        System.out.print(" : ");
        System.out.println(a);
    }
}
