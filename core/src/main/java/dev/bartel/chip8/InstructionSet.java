package dev.bartel.chip8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InstructionSet {
    protected final Map<Integer, Instruction> instructionMap = new HashMap<>();

    public Instruction getInstruction(int opcode){
        int mostSignificantNibble = (opcode & 0xF000) >> 12;

        return switch (mostSignificantNibble) {
            // X__X Instructions: first and last nibble determines instruction
            case 0x0, 0x5, 0x8 -> instructionMap.get(opcode & 0xF00F);
            // X___ Instruction: only last nibble determines instruction
            case 0x1, 0x2, 0x3, 0x4, 0x6, 0x7, 0x9, 0xA, 0xB, 0xC, 0xD -> instructionMap.get(opcode & 0xF000);
            // X_XX Instruction: first two and last nibble determines instruction (used for only 0xE and 0xF)
            default -> instructionMap.get(opcode & 0xF0FF);
        };
    }

    protected abstract void registerInstructions();
}
