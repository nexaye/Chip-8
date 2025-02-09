package dev.bartel.chip8;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class Memory {
    public static final int STACK_SIZE_LIMIT = 16;
    public static final int RAM_SIZE_LIMIT = 4096;
    public static final int FONT_START_LOCATION = 0x050;
    public static final int ROM_START_LOCATION = 0x200;

    private final int[] memory = new int[RAM_SIZE_LIMIT];
    private final Stack<Short> stack = new Stack<>();

    public Memory(){
        initFont();
        try {
            initROM();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void initFont(){
        int[] font = new int[]{
            0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
            0x20, 0x60, 0x20, 0x20, 0x70, // 1
            0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
            0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
            0x90, 0x90, 0xF0, 0x10, 0x10, // 4
            0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
            0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
            0xF0, 0x10, 0x20, 0x40, 0x40, // 7
            0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
            0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
            0xF0, 0x90, 0xF0, 0x90, 0x90, // A
            0xF0, 0x90, 0xF0, 0x90, 0xF0, // B
            0xF0, 0x80, 0x80, 0x80, 0xF0, // C
            0xF0, 0x90, 0x90, 0x90, 0xF0, // D
            0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
            0xF0, 0x80, 0xF0, 0x80, 0x80  // F
        };
        //copy the font data into memory from 0x050-0x09F
        System.arraycopy(font, 0, this.memory, FONT_START_LOCATION, font.length);
    }
    private void initROM() throws IOException{
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Logo.ch8");
        if (inputStream == null) throw new IOException("ROM not found");
        byte[] romContent = inputStream.readAllBytes();
        int[] romContent2 = new int[romContent.length];
        for(int i = 0; i<romContent.length;i++){
            romContent2[i] = romContent[i] & 0xFF;
        }
        System.arraycopy(romContent2, 0, this.memory, ROM_START_LOCATION, romContent.length);
    }

    public void write(int addr, byte value) {
        memory[addr] = value;
    }

    public int read(int addr){
        return memory[addr];
    }

    public int getInstruction(int pc){
        int highByte = memory[pc];
        int lowByte = memory[pc+1];
        //Instruction are 16-Bit and will be composed of the Bytes of memory[pc] and memory[pc+1]
        //Chip-8 uses a big-endian structure MSB come first in memory followed by LSB
        return ((highByte << 8) | (lowByte & 0xFF));
    }

    public void pushStack(short address){
        if(stack.size() >= STACK_SIZE_LIMIT){
            return;
        }
        stack.push(address);
    }

    public short popStack(){

        return stack.pop();
    }
}







