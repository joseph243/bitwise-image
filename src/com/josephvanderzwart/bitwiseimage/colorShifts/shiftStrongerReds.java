package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftStrongerReds implements ColorShiftBase {

    @Override
    public int shiftPixel(int inPixel) {
        int redOnly = (inPixel >> 16) & 0xFF;
        redOnly = Math.min(redOnly + 50, 255);
        redOnly = redOnly << 16;
        int stripRed = inPixel & 0xFF00_FFFF;
        return redOnly | stripRed;
    }

    public int shiftPixel(int inPixel, int inPowerLevel)
    {
        int redOnly = (inPixel >> 16) & 0xFF;
        redOnly = Math.min(redOnly + inPowerLevel, 255);
        redOnly = redOnly << 16;
        int stripRed = inPixel & 0xFF00_FFFF;
        return redOnly | stripRed;
    }

    @Override
    public String getName() {
        return "Stronger Reds";
    }
}
