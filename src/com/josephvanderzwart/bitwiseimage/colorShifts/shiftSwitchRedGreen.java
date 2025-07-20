package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftSwitchRedGreen implements colorShiftBase{

    @Override
    public int shiftPixel(int inPixel) {
        int r = (inPixel >> 16) & 0xFF;
        int g = (inPixel >> 8) & 0xFF;
        int b = inPixel & 0xFF;
        return (0xFF << 24) | (g << 16) | (r << 8) | (b);
    }

    @Override
    public String getName() {
        return "Switch Red-Green";
    }
}
