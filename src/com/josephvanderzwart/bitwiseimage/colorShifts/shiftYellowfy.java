package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftYellowfy implements ColorShiftBase {

    @Override
    public int shiftPixel(int inPixel) {
        int r = (inPixel >> 16) & 0xFF;
        int g = (inPixel >> 8) & 0xFF;
        int b = inPixel & 0xFF;
        int powerlevel = 10;
        r = Math.min(r + powerlevel, 255);
        g = Math.min(g + powerlevel, 255);
        b = Math.max(b - powerlevel, 0);
        return (0xFF << 24) | (r << 16) | (g << 8) | (b);
    }

    @Override
    public String getName() {
        return "Yellowfy";
    }
}
