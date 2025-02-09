package dev.bartel.chip8;

import java.util.Arrays;

public class Display {
    public static final int DISPLAY_WIDTH = 64;
    public static final int DISPLAY_HEIGHT = 32;

    private boolean[][] videoBuffer;
    private boolean drawFlag;

    public Display(){
        this.videoBuffer = new boolean[DISPLAY_HEIGHT][DISPLAY_WIDTH];
        this.drawFlag = false;
    }

    public boolean[][] getVideoBuffer(){
        return this.videoBuffer;
    }

    public void setDrawFlag(boolean flag){
        this.drawFlag = flag;
    }

    public boolean getDrawFlag(){
        return this.drawFlag;
    }

}
