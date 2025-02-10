package dev.bartel.chip8;

@FunctionalInterface
public interface Instruction {
    public void execute(Cpu cpu, int opcode);
}
