package colorShifts;

public class shiftGreenBlue implements colorShiftBase{

    @Override
    public int shiftPixel(int inPixel) {
        int r = (inPixel >> 16) & 0xFF;
        int g = (inPixel >> 8) & 0xFF;
        int b = inPixel & 0xFF;
        r = Math.min(r + 50, 255);
        g = Math.min(g + 50, 255);
        b = Math.min(b + 50, 255);
        return (0xFF << 24) | (r << 16) | (b << 8) | (g);
    }

    @Override
    public String getName() {
        return "Shift Green-Blue";
    }
}
