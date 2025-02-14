package dev.bartel.chip8;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public class Display {
    public static final int DISPLAY_WIDTH = 64;
    public static final int DISPLAY_HEIGHT = 32;
    @Getter
    private boolean[][] videoBuffer;
    @Getter @Setter
    private boolean drawFlag;

    public Display(){
        this.videoBuffer = new boolean[DISPLAY_HEIGHT][DISPLAY_WIDTH];
        this.drawFlag = false;
    }

}
