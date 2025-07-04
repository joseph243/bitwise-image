import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.Buffer;


public class Bitwise {

    private Bitwise()
    {

    }

    public static void bit(File inFile, File targetFile)
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
            System.out.println("FUCK !!!! ITS ALL GONE WRONG !!!!");
            e.printStackTrace();
        }
        after = new BufferedImage(before.getWidth(), before.getHeight(), BufferedImage.TYPE_INT_RGB);
        try
        {
            ImageIO.write(after, "png", targetFile);
        }
        catch (Exception e)
        {
            System.out.println("YOU DIRTY BASTARD!  HE'S DONE IT AGAIN!");
            e.printStackTrace();
        }
    }
}
