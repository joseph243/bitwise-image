package colorShifts;

public class shiftGrayscale implements colorShiftBase{

    @Override
    public int shiftPixel(int inPixel) {
        int r = (inPixel >> 16) & 0xFF;
        int g = (inPixel >> 8) & 0xFF;
        int b = inPixel & 0xFF;
        int gray = (r + g + b) / 3;
        return (gray << 24) | (gray << 16) | (gray << 8) | (gray);
    }

    @Override
    public String getName() {
        return "Grayscale";
    }
}
