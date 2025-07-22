package com.josephvanderzwart.bitwiseimage;

import com.josephvanderzwart.bitwiseimage.colorShifts.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class ImageEditor {

    private ArrayList<ColorShiftBase> selectedTransformations;

    private ArrayList<ColorShiftBase> availabletransformations;

    public ImageEditor()
    {
        selectedTransformations = new ArrayList<>();
        availabletransformations = new ArrayList<>();
        availabletransformations.add(new shiftAddFiveThousand());
        availabletransformations.add(new shiftGrayscale());
        availabletransformations.add(new shiftIntensify());
        availabletransformations.add(new shiftStrongerBlues());
        availabletransformations.add(new shiftStrongerReds());
        availabletransformations.add(new shiftStrongerGreens());
        availabletransformations.add(new shiftSwitchRedBlue());
        availabletransformations.add(new shiftSwitchRedGreen());
        availabletransformations.add(new shiftSwitchGreenBlue());
    }

    public boolean selectTransformation(String inId) {
        boolean found = false;
        for (ColorShiftBase t : availabletransformations)
        {
            if (t.getName().equalsIgnoreCase(inId))
            {
                selectedTransformations.add(t);
                found = true;
                break;
            }
        }
        if (!found)
        {
            System.out.println(inId + " is not a valid transformation.");
        }
        return found;
    }

    public void listAvailableTransformations()
    {
        for (ColorShiftBase t : availabletransformations)
        {
            System.out.print(t.getName() + ", ");
        }
        System.out.println();
    }

    public ArrayList<ColorShiftBase> getAvailableTransformations()
    {
        return availabletransformations;
    }

    public ArrayList<ColorShiftBase> getSelectedTransformations()
    {
        return selectedTransformations;
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
        int random = r.nextInt(availabletransformations.size());
        selectedTransformations.clear();
        selectedTransformations.add(availabletransformations.get(random));
        return doAllSelectedTransformations(in);
    }

    private int doAllSelectedTransformations(int in)
    {
        for (ColorShiftBase t : selectedTransformations)
        {
            in = t.shiftPixel(in);
        }
        return in;
    }
}
