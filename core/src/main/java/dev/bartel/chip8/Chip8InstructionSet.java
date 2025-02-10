package dev.bartel.chip8;

import java.util.HashMap;
import java.util.Map;

public class Chip8InstructionSet extends InstructionSet {

    private final Map<Integer, Instruction> instructionMap = new HashMap<>();

    public Instruction getInstruction(short opcode){
        return null;
    }
}
