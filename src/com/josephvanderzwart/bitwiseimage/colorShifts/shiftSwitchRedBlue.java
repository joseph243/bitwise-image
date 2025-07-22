package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftSwitchRedBlue implements ColorShiftBase {

    @Override
    public int shiftPixel(int inPixel) {
        int r = (inPixel >> 16) & 0xFF;
        int g = (inPixel >> 8) & 0xFF;
        int b = inPixel & 0xFF;
        return (0xFF << 24) | (b << 16) | (g << 8) | (r);
    }

    @Override
    public String getName() {
        return "Switch Red-Blue";
    }
}
