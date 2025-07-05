import java.awt.image.BufferedImage;


public class ImageEditor {

    private ImageEditor()
    {

    }

    public static BufferedImage half(BufferedImage image)
    {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                if (x < image.getWidth() / 2)
                {
                    output.setRGB(x,y,transformRscale(image.getRGB(x,y)));
                }
                else
                {
                    output.setRGB(x,y,image.getRGB(x,y));
                }
            }
        }
        return output;
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
        redOnly = Math.min(redOnly + 150, 255);
        redOnly = redOnly << 16;
        int stripRed = in & 0xFF00_FFFF;
        int output = redOnly | stripRed;
        System.out.println(Integer.toBinaryString(output));
        return output;
    }

    private static int transformRscale(int in)
    {
        return (in & 0x00FF0000) << 4;
    }
}
