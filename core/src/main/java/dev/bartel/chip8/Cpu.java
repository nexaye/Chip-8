package dev.bartel.chip8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Cpu {
    //8-bit (int as no unsigned byte exist) registers (V0-VF)
    private final int[] registers = new int[16];

    //16-bit (int as no unsigned byte exist) registers (PC, I, SP)
    @Setter @Getter
    private int pc;
    @Setter @Getter
    private int i;
    private int sp;

    //8-bit (int as no unsigned byte exist) delay timers
    @Setter @Getter
    private int delayTimer;
    @Setter @Getter
    private int soundTimer;

    //
    private int currentOpcode;
    private Instruction currentInstruction;
    //CPU dependencies
    @Setter @Getter
    private Memory memory;
    @Setter @Getter
    private Display display;
    @Setter @Getter
    private Input input;
    @Setter @Getter
    private Sound sound;
    private InstructionSet instructionSet;

    public Cpu(InstructionSet instructionSet,Memory memory, Display display, Input input, Sound sound){
        this.pc = Memory.ROM_START_LOCATION;
        this.i = (short) 0x0000;
        this.sp = (short) 0x0000;

        this.instructionSet = instructionSet;
        this.memory = memory;
        this.display = display;
        this.input = input;
        this.sound = sound;
    }

    public void cycle(){
        fetch();
        decode();
        execute();
    }

    private void fetch(){
        currentOpcode = memory.getInstruction(pc);
        incrementPC();
    }
    private void decode(){
        currentInstruction = instructionSet.getInstruction(currentOpcode);
    }

    private void execute(){
        currentInstruction.execute(this, currentOpcode);
    }

    public void setRegisterValue(int register, int value){
        registers[register] = value;
    }
    public int getRegisterValue(int register){
        return registers[register];
    }
    public void increaseSP(){sp += 1;}
    public void decreaseSP(){sp -= 1;}
    public void incrementPC(){
        pc += 2;
    }
}
