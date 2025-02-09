package dev.bartel.chip8;

import java.util.HashMap;
import java.util.Map;

public interface InstructionSet {
    public Instruction getInstruction(short opcode);
}
