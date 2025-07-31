package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftNotify implements ColorShiftBase {

    @Override
    public int shiftPixel(int inPixel) {
        return ~inPixel;
    }

    @Override
    public String getName() {
        return "NOT-ify";
    }
}
