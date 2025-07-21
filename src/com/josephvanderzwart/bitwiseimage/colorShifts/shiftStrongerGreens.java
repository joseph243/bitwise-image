package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftStrongerGreens implements colorShiftBase{

    @Override
    public int shiftPixel(int inPixel) {
        int greenOnly = (inPixel >> 8) & 0xFF;
        greenOnly = Math.min(greenOnly + 50, 255);
        greenOnly = greenOnly << 8;
        int stripGreen = inPixel & 0xFFFF_00FF;
        return greenOnly | stripGreen;
    }

    public int shiftPixel(int inPixel, int inPowerLevel)
    {
        int greenOnly = (inPixel >> 8) & 0xFF;
        greenOnly = Math.min(greenOnly + inPowerLevel, 255);
        greenOnly = greenOnly << 8;
        int stripGreen = inPixel & 0xFFFF_00FF;
        return greenOnly | stripGreen;
    }

    @Override
    public String getName() {
        return "Stronger Greens";
    }
}
