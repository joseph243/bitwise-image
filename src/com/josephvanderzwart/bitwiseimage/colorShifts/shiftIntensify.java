package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftIntensify implements colorShiftBase{

    @Override
    public int shiftPixel(int inPixel) {
        int r = (inPixel >> 16) & 0xFF;
        int g = (inPixel >> 8) & 0xFF;
        int b = inPixel & 0xFF;
        r = Math.min(r + 50, 255);
        g = Math.min(g + 50, 255);
        b = Math.min(b + 50, 255);
        return (0xFF << 24) | (r << 16) | (g << 8) | (b);
    }

    public int shiftPixel(int inPixel, int inPowerLevel)
    {
        int r = (inPixel >> 16) & 0xFF;
        int g = (inPixel >> 8) & 0xFF;
        int b = inPixel & 0xFF;
        r = Math.min(r + inPowerLevel, 255);
        g = Math.min(g + inPowerLevel, 255);
        b = Math.min(b + inPowerLevel, 255);
        return (0xFF << 24) | (r << 16) | (g << 8) | (b);
    }

    @Override
    public String getName() {
        return "Intensify";
    }
}
