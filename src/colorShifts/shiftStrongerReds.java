package colorShifts;

public class shiftStrongerReds implements colorShiftBase{

    @Override
    public int shiftPixel(int inPixel) {
        int redOnly = (inPixel >> 16) & 0xFF;
        redOnly = Math.min(redOnly + 150, 255);
        redOnly = redOnly << 16;
        int stripRed = inPixel & 0xFF00_FFFF;
        return redOnly | stripRed;
    }

    @Override
    public String getName() {
        return "Stronger Reds";
    }
}
