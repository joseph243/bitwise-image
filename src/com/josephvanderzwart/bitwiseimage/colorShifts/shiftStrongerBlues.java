package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftStrongerBlues implements ColorShiftBase {

    @Override
    public int shiftPixel(int inPixel) {
        int blueOnly = inPixel & 0xFF;
        blueOnly = Math.min(blueOnly + 50, 255);
        int stripBlue = inPixel & 0xFFFF_FF00;
        return blueOnly | stripBlue;
    }

    public int shiftPixel(int inPixel, int inPowerLevel)
    {
        int blueOnly = inPixel & 0xFF;
        blueOnly = Math.min(blueOnly + inPowerLevel, 255);
        int stripBlue = inPixel & 0xFFFF_FF00;
        return blueOnly | stripBlue;
    }

    @Override
    public String getName() {
        return "Stronger Blues";
    }
}
