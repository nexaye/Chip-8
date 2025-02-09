package dev.bartel.chip8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cpu {
    //8-bit registers (V0-VF)
    private int[] registers = new int[16];

    //16-bit registers (PC, I, SP)
    private int pc;
    private int i;
    private int sp;

    //8-bit delay timers
    private byte delayTimer;
    private byte soundTimer;

    //CPU dependencies
    private  Memory memory;
    private Display display;
    private Input input;
    private Sound sound;

    public Cpu(Memory memory, Display display, Input input, Sound sound){
        this.pc = Memory.ROM_START_LOCATION;
        this.i = (short) 0x0000;
        this.sp = (short) 0x0000;

        this.memory = memory;
        this.display = display;
        this.input = input;
        this.sound = sound;
    }

    public void cycle(){
        int opcode = fetch();
        decode(opcode);

    }

    private int fetch(){
        int opcode = this.memory.getInstruction(this.pc);
        this.pc += 2;
        return opcode;
    }

    private void decode(int opcode){
        int firstNibble = opcode & 0xF000;
        int lastNibble = opcode & 0x000F;

        int register,value;


        switch(firstNibble){
            case 0x0000:
                switch(lastNibble){
                    case 0x000E:
                        break;
                    default:
                        clearScreen();
                        break;
                }
                break;
            case 0x1000:
                int addr = opcode & 0x0FFF;
                jump(addr);
                break;
            case 0x6000:
                register = (opcode & 0x0F00) >> 8;
                value = opcode & 0x00FF;
                setRegister(register, value);
                break;
            case 0x7000:
                register = opcode & 0x0F00;
                value = opcode & 0x00FF;
                addIntermediate(register, value);
                break;
            case 0xA000:
                value = opcode & 0x0FFF;
                setIndexRegister(value);
                break;
            case 0xD000:
                int vX = opcode & 0x0F00;
                int vY = opcode & 0x00F0;
                int nibble = opcode & 0x000F;
                draw(vX,vY,nibble);
                break;
        }
    }

    private void clearScreen(){
            var iterator = Arrays.stream(this.display.getVideoBuffer()).iterator();
            while(iterator.hasNext())
            {
                Arrays.fill(iterator.next(), false);
            }
            this.display.setDrawFlag(true);
    }

    private void jump(int addr){
        this.pc = addr;
    }

    private void setRegister(int vX, int value){
        this.registers[vX] = value;
    }

    private void setIndexRegister(int value){
        this.i = value;
    }
    private void addIntermediate(int vX, int value){
        this.registers[vX] += value;
        if(this.registers[vX] > 255)
            this.registers[vX] -= 256;
    }

    private void draw(int vX, int vY, int nibble){
        int index = this.i;
        List<Integer> sprite = new ArrayList<Integer>();

        for(int i = 0; i < nibble; i++){
            sprite.add(memory.read(index+i));
        }
    }

    private void incrementPC(){
        pc += 2;
    }
    private void execute(){
        return;
    }
}
