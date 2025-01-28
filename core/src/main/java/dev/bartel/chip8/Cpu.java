package dev.bartel.chip8;

public class Cpu {
    //8-bit registers (V0-VF)
    private byte[] registers = new byte[16];

    //16-bit registers (PC, I, SP)
    private short pc;
    private short i;
    private short sp;

    //8-bit delay timers
    private byte delayTimer;
    private byte soundTimer;

    //CPU dependencies
    private Memory memory;
    private Display display;
    private Input input;
    private Sound sound;

    public Cpu(Memory memory, Display display, Input input, Sound sound){
        this.pc = (short) 0x0200;
        this.memory = memory;
        this.display = display;
        this.input = input;
        this.sound = sound;
    }

    public void cycle(){
        short opcode = fetch();
        decode(opcode);
        execute();
    }

    private short fetch(){

        return (short)0xFFFF;

    }

    private void decode(short opcode){
        return;
    }

    private void execute(){
        return;
    }
}
