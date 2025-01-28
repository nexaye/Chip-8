package dev.bartel.chip8;

public class Memory {
    private byte[] ram = new byte[4096];

    public Memory(){

    }
    public void write() {
        return;
    }

    public short read(short pc){
        byte opcode1 = ram[pc];
        byte opcode2 = ram[pc+1];
        //since an instruction is composed of 16-bit, combine the last two entries into one short
        return (short) ((opcode1 << 8) | (opcode2 & 0xFF)) ;
    }
}







