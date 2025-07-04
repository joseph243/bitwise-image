import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


public class Bitwise {

    private Bitwise()
    {

    }

    public static void bitHalf(File inFile, File targetFile)
    {
        System.out.println("~~ bitwise working bit...");
        BufferedImage after = null;
        BufferedImage before = null;
        try
        {
            before = ImageIO.read(inFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        after = new BufferedImage(before.getWidth(), before.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < before.getWidth(); x++)
        {
            for (int y = 0; y < before.getHeight(); y++)
            {
                if (x < before.getWidth() / 2)
                {
                    after.setRGB(x,y,transformStrongerReds(before.getRGB(x,y)));
                }
                else
                {
                    after.setRGB(x,y,before.getRGB(x,y));
                }

            }
        }
        try
        {
            ImageIO.write(after, "png", targetFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static int transformThou(int in)
    {
        return in + 5000;
    }

    //format:  32 bit int > A R G B:  8,8,8,8
    private static int transformBlueToRed(int in)
    {
        System.out.println(Integer.toBinaryString(in));
        return in << 16;
    }

    private static int transformBlueToGreen(int in)
    {
        System.out.println(Integer.toBinaryString(in));
        return in << 8;
    }

    private static int transformStrongerReds(int in)
    {
        System.out.println(Integer.toBinaryString(in));
        int redOnly = (in >> 16) & 0xFF;
        redOnly = Math.min(redOnly + 50, 255);
        redOnly = redOnly << 16;
        int stripRed = in & 0xFF00_FFFF;
        int output = redOnly | stripRed;
        System.out.println(Integer.toBinaryString(output));
        return output;
    }
}
