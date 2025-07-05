import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class ImageEditor {

    private ArrayList<String> transformations;
    private final String ADDFIVETHOUSAND = "Add 5000";
    private final String BLUETORED = "Blue to Red";
    private final String BLUETOGREEN = "Blue to Green";
    private final String STRONGERREDS = "Stronger Reds";
    private final String GRAYSCALE = "Grayscale";

    private ArrayList<String> selectedTransformations;

    public ImageEditor()
    {
        transformations = new ArrayList<>();
        transformations.add(GRAYSCALE);
        transformations.add(STRONGERREDS);
        transformations.add(BLUETOGREEN);
        transformations.add(BLUETORED);
        transformations.add(ADDFIVETHOUSAND);
        selectedTransformations = new ArrayList<>();
    }

    public void selectTransformation(String inId)
    {
        if (transformations.contains(inId))
        {
            selectedTransformations.add(inId);
        }
        else
        {
            System.out.println(inId + " is not a valid transformation.");
        }
    }

    public void listAvailableTransformations()
    {
        for (String s : transformations)
        {
            System.out.print(s + ", ");
        }
        System.out.println();
    }

    public BufferedImage half(BufferedImage image)
    {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                if (x < image.getWidth() / 2)
                {
                    int before = image.getRGB(x,y);
                    int after = doAllSelectedTransformations(before);
                    output.setRGB(x,y,after);
                    if (Main.isDebugMode())
                    {
                        Main.printInt(before);
                        Main.printInt(after);
                    }
                }
                else
                {
                    output.setRGB(x,y,image.getRGB(x,y));
                }
            }
        }
        return output;
    }

    private int doAllSelectedTransformations(int in)
    {
        for (String t : selectedTransformations)
        {
            switch (t)
            {
                case ADDFIVETHOUSAND:
                    in = transformThou(in);
                    break;
                case BLUETORED:
                    in = transformBlueToRed(in);
                    break;
                case BLUETOGREEN:
                    in = transformBlueToGreen(in);
                    break;
                case STRONGERREDS:
                    in = transformStrongerReds(in);
                    break;
                case GRAYSCALE:
                    in = transformGrayscale(in);
                    break;
            }
        }
        return in;
    }

    private static int transformThou(int in)
    {
        return in + 5000;
    }

    //format:  32 bit int > A R G B:  8,8,8,8
    private static int transformBlueToRed(int in)
    {
        return in << 16;
    }

    private static int transformBlueToGreen(int in)
    {
        return in << 8;
    }

    private static int transformStrongerReds(int in)
    {
        int redOnly = (in >> 16) & 0xFF;
        redOnly = Math.min(redOnly + 150, 255);
        redOnly = redOnly << 16;
        int stripRed = in & 0xFF00_FFFF;
        return redOnly | stripRed;
    }

    private static int transformGrayscale(int in)
    {
        int r = (in >> 16) & 0xFF;
        int g = (in >> 8) & 0xFF;
        int b = in & 0xFF;
        int gray = (r + g + b) / 3;
        return (gray << 24) | (gray << 16) | (gray << 8) | (gray);
    }
}
