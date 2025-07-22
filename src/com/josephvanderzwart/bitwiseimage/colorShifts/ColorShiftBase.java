package com.josephvanderzwart.bitwiseimage.colorShifts;

public interface ColorShiftBase {
    public abstract int shiftPixel(int inPixel);
    public abstract String getName();
}
