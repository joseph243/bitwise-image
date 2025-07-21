package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftSwitchGreenBlue implements ColorShiftBase {

    @Override
    public int shiftPixel(int inPixel) {
        int r = (inPixel >> 16) & 0xFF;
        int g = (inPixel >> 8) & 0xFF;
        int b = inPixel & 0xFF;
        return (0xFF << 24) | (r << 16) | (b << 8) | (g);
    }

    @Override
    public String getName() {
        return "Switch Green-Blue";
    }
}
