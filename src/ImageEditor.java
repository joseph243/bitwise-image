import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class ImageEditor {

    private ArrayList<String> transformations;
    private final String ADDFIVETHOUSAND = "Add 5000";
    private final String BLUETORED = "Blue to Red";
    private final String BLUETOGREEN = "Blue to Green";
    private final String STRONGERREDS = "Stronger Reds";
    private final String GRAYSCALE = "Grayscale";
    private final String INTENSIFY = "Intensify";
    private final String SWITCHRB = "Switch RB";
    private final String SWITCHRG = "Switch RG";
    private final String SWITCHGB = "Switch GB";

    private ArrayList<String> selectedTransformations;

    public ImageEditor()
    {
        transformations = new ArrayList<>();
        transformations.add(GRAYSCALE);
        transformations.add(STRONGERREDS);
        transformations.add(BLUETOGREEN);
        transformations.add(BLUETORED);
        transformations.add(ADDFIVETHOUSAND);
        transformations.add(INTENSIFY);
        transformations.add(SWITCHGB);
        transformations.add(SWITCHRB);
        transformations.add(SWITCHRG);
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

    public BufferedImage orify(ArrayList<BufferedImage> inImages)
    {
        int xSize = 999999;
        int ySize = 999999;
        for (BufferedImage image : inImages)
        {
            if (image.getHeight() < ySize)
            {
                ySize = image.getHeight();
            }
            if (image.getWidth() < xSize)
            {
                xSize = image.getWidth();
            }
        }
        BufferedImage output = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < output.getWidth(); x++)
        {
            for (int y = 0; y < output.getHeight(); y++)
            {
                ArrayList<Integer> pixels = new ArrayList<Integer>();
                for (BufferedImage image : inImages)
                {
                    pixels.add(image.getRGB(x,y));
                }
                int after = (pixels.get(0) | pixels.get(1));
                output.setRGB(x,y,after);
                //TODO how to bit-OR for N instead of 2 ?
            }
        }
        return output;
    }

    public BufferedImage andify(ArrayList<BufferedImage> inImages)
    {
        int xSize = 999999;
        int ySize = 999999;
        for (BufferedImage image : inImages)
        {
            if (image.getHeight() < ySize)
            {
                ySize = image.getHeight();
            }
            if (image.getWidth() < xSize)
            {
                xSize = image.getWidth();
            }
        }
        BufferedImage output = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < output.getWidth(); x++)
        {
            for (int y = 0; y < output.getHeight(); y++)
            {
                ArrayList<Integer> pixels = new ArrayList<Integer>();
                for (BufferedImage image : inImages)
                {
                    pixels.add(image.getRGB(x,y));
                }
                int after = (pixels.get(0) & pixels.get(1));
                if (Main.isDebugMode())
                {
                    for (int p : pixels)
                    {
                        Main.printInt(p);
                    }
                    System.out.println("avg into: ");
                    Main.printInt(after);
                }
                output.setRGB(x,y,after);
                //TODO how to bit-OR for N instead of 2 ?
            }
        }
        return output;
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

    public BufferedImage full(BufferedImage image)
    {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
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
        }
        return output;
    }

    public BufferedImage fullChaos(BufferedImage image)
    {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); x++)
        {
            for (int y = 0; y < image.getHeight(); y++)
            {
                int before = image.getRGB(x,y);
                int after = doRandomTransformation(before);
                output.setRGB(x,y,after);
                if (Main.isDebugMode())
                {
                    Main.printInt(before);
                    Main.printInt(after);
                }
            }
        }
        return output;
    }

    private int doRandomTransformation(int in)
    {
        Random r = new Random();
        int random = r.nextInt(transformations.size());
        selectedTransformations.clear();
        selectedTransformations.add(transformations.get(random));
        return doAllSelectedTransformations(in);
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
                case INTENSIFY:
                    in = transformIntensify(in);
                    break;
                case SWITCHRB:
                    in = transformSwitchRB(in);
                    break;
                case SWITCHRG:
                    in = transformSwitchRG(in);
                    break;
                case SWITCHGB:
                    in = transformSwitchGB(in);
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

    private static int transformIntensify(int in)
    {
        int r = (in >> 16) & 0xFF;
        int g = (in >> 8) & 0xFF;
        int b = in & 0xFF;
        r = Math.min(r + 50, 255);
        g = Math.min(g + 50, 255);
        b = Math.min(b + 50, 255);
        return (0xFF << 24) | (r << 16) | (g << 8) | (b);
    }

    private static int transformSwitchRG(int in)
    {
        int r = (in >> 16) & 0xFF;
        int g = (in >> 8) & 0xFF;
        int b = in & 0xFF;
        r = Math.min(r + 50, 255);
        g = Math.min(g + 50, 255);
        b = Math.min(b + 50, 255);
        return (0xFF << 24) | (g << 16) | (r << 8) | (b);
    }

    private static int transformSwitchRB(int in)
    {
        int r = (in >> 16) & 0xFF;
        int g = (in >> 8) & 0xFF;
        int b = in & 0xFF;
        r = Math.min(r + 50, 255);
        g = Math.min(g + 50, 255);
        b = Math.min(b + 50, 255);
        return (0xFF << 24) | (b << 16) | (g << 8) | (r);
    }

    private static int transformSwitchGB(int in)
    {
        int r = (in >> 16) & 0xFF;
        int g = (in >> 8) & 0xFF;
        int b = in & 0xFF;
        r = Math.min(r + 50, 255);
        g = Math.min(g + 50, 255);
        b = Math.min(b + 50, 255);
        return (0xFF << 24) | (r << 16) | (b << 8) | (g);
    }
}
