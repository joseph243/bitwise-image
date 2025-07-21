package com.josephvanderzwart.bitwiseimage.colorShifts;

public class shiftAddFiveThousand implements ColorShiftBase {

    @Override
    public int shiftPixel(int inPixel) {
        return inPixel + 5000;
    }

    @Override
    public String getName() {
        return "Add Five Thousand";
    }
}
