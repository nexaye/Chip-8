package dev.bartel.chip8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Chip8InstructionSet extends InstructionSet {

    Random rand = new Random();

    public Chip8InstructionSet(){
        registerInstructions();
    }

    @Override
    protected void registerInstructions() {
        //0x00E0 - CLS | Clear the display
        instructionMap.put(0x0000, (cpu, opcode) -> {
            Display display = cpu.getDisplay();
            var iterator = Arrays.stream(display.getVideoBuffer()).iterator();
            while(iterator.hasNext())
            {
                Arrays.fill(iterator.next(), false);
            }
            display.setDrawFlag(true);
        });

        //0x00EE - RET | Return from a subroutine
        instructionMap.put(0x000E, (cpu, opcode) -> {
            Memory mem = cpu.getMemory();
            mem.popStack();
            cpu.decreaseSP();
        });
        //0x1nnn - JP addr | Jump to location nnn
        instructionMap.put(0x1000, (cpu, opcode) -> {
            int addr = opcode & 0x0FFF;
            cpu.setPc(addr);
        });
        //2nnn - CALL addr | Call subroutine at nnn
        instructionMap.put(0x2000, (cpu, opcode) -> {
            Memory mem = cpu.getMemory();
            cpu.increaseSP();
            mem.pushStack(cpu.getPc());
            cpu.setPc(opcode & 0x0FFF);

        });
        //3xkk - SE Vx, byte | Skip next instruction if Vx = kk
        instructionMap.put(0x3000, (cpu, opcode) -> {
            int registerX = opcode & 0x0F00;
            int xValue = cpu.getRegisterValue(registerX);
            int compareValue = opcode & 0x00FF;

            if(xValue == compareValue)
                cpu.incrementPC();

        });
        //4xkk - SNE Vx, byte | Skip next instruction if Vx != kk
        instructionMap.put(0x4000, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int xValue = cpu.getRegisterValue(registerX);
            int compareValue = opcode & 0x00FF;

            if(xValue != compareValue)
                cpu.incrementPC();
        });
        //5xy0 - SE Vx, Vy | Skip next instruction if Vx = Vy
        instructionMap.put(0x5000, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int xValue = cpu.getRegisterValue(registerX);
            int yValue = cpu.getRegisterValue(registerY);

            if(xValue == yValue)
                cpu.incrementPC();
        });

        //6xkk - LD Vx, byte | Set Vx = kk
        instructionMap.put(0x6000, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int xValue = opcode & 0x00FF;
            cpu.setRegisterValue(registerX,xValue);
        });

        //7xkk - ADD Vx, byte | Set Vx = Vx + kk
        instructionMap.put(0x7000, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int xValue = opcode & 0x00FF;
            int currentRegisterValue = cpu.getRegisterValue(registerX);
            if(xValue + currentRegisterValue > 255)
                xValue -= 256;
            cpu.setRegisterValue(registerX,currentRegisterValue + xValue);
        });

        //8xy0 - LD Vx, Vy | Set Vx = Vy
        instructionMap.put(0x8000, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;
            cpu.setRegisterValue(registerX, cpu.getRegisterValue(registerY));
        });

        //8xy1 - OR Vx, Vy | Set Vx = Vx OR Vy
        instructionMap.put(0x8001, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int xValue =  cpu.getRegisterValue(registerX);
            int yValue =  cpu.getRegisterValue(registerY);
            cpu.setRegisterValue(registerX, xValue | yValue);
        });

        //8xy2 - AND Vx, Vy | Set Vx = Vx AND Vy
        instructionMap.put(0x8002, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int xValue =  cpu.getRegisterValue(registerX);
            int yValue =  cpu.getRegisterValue(registerY);
            cpu.setRegisterValue(registerX, xValue & yValue);
        });

        //8xy3 - XOR Vx, Vy | Set Vx = Vx XOR Vy
        instructionMap.put(0x8003, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int xValue =  cpu.getRegisterValue(registerX);
            int yValue =  cpu.getRegisterValue(registerY);
            cpu.setRegisterValue(registerX, xValue ^ yValue);
        });

        //8xy4 - ADD Vx, Vy | Set Vx = Vx + Vy, set VF = carry
        instructionMap.put(0x8004, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int xValue =  cpu.getRegisterValue(registerX);
            int yValue =  cpu.getRegisterValue(registerY);
            if(xValue + yValue > 255){
                yValue -= 256;
                cpu.setRegisterValue(0xF, 0x1);
            }
            else{
                cpu.setRegisterValue(0xF, 0x0);
            }

            cpu.setRegisterValue(registerX, xValue + yValue);
        });

        //8xy5 - SUB Vx, Vy | Set Vx = Vx - Vy, set VF = NOT borrow
        instructionMap.put(0x8005, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int xValue =  cpu.getRegisterValue(registerX);
            int yValue =  cpu.getRegisterValue(registerY);
            //@todo will not work as an underflow will occur fix later (prob just shift by 28 when xValue > yValue)
            cpu.setRegisterValue(0xF, xValue > yValue ? 0x1 : 0x0);
            cpu.setRegisterValue(registerX, xValue - yValue);
        });

        //8xy6 - SHR Vx {, Vy} | Set Vx = Vx SHR 1
        //@todo well there are two implementations this above or Set Vx = Vy = Vy SHR 1 -> make it toggleable
        instructionMap.put(0x8006, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int yValue = cpu.getRegisterValue(registerY);
            cpu.setRegisterValue(0xF, yValue & 0x0001);
            cpu.setRegisterValue(registerX, yValue >> 1);
        });

        //8xy7 - SUBN Vx, Vy | Set Vx = Vy - Vx, set VF = NOT borrow
        instructionMap.put(0x8007, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int xValue =  cpu.getRegisterValue(registerX);
            int yValue =  cpu.getRegisterValue(registerY);
            //@todo will not work as an underflow will occur fix later (prob just shift by 28 when yValue > xValue)
            cpu.setRegisterValue(0xF, yValue > xValue ? 0x1 : 0x0);
            cpu.setRegisterValue(registerX, yValue - xValue);
        });

        //8xyE - SHL Vx {, Vy} | Set Vx = Vx SHL 1
        //@todo well there are two implementations this above or Set Vx = Vy = Vy SHL 1 -> make it toggleable
        instructionMap.put(0x800E, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int yValue = cpu.getRegisterValue(registerY);
            cpu.setRegisterValue(0xF, (yValue & 0x8000) >> 15);
            cpu.setRegisterValue(registerX, yValue << 1);
        });

        //9xy0 - SNE Vx, Vy | Skip next instruction if Vx != Vy
        instructionMap.put(0x9000, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int registerY = (opcode & 0x00F0) >> 4;

            int xValue = cpu.getRegisterValue(registerX);
            int yValue = cpu.getRegisterValue(registerY);

            if(xValue != yValue)
                cpu.incrementPC();
        });

        //Annn - LD I, addr | Set I = nnn.
        instructionMap.put(0xA000, (cpu, opcode) -> {
            int addr = opcode & 0x0FFF;
            cpu.setI(addr);
        });

        //Bnnn - JP V0, addr | Jump to location nnn + V0
        instructionMap.put(0xB000, (cpu, opcode) -> {
            int addr = opcode & 0x0FFF;
            int offset = cpu.getRegisterValue(0x0);
            cpu.setPc(addr + offset);
        });

        //Cxkk - RND Vx, byte | Set Vx = random byte AND kk
        instructionMap.put(0xC000, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int randValue = rand.nextInt(0,255);
            int value = opcode & 0x00FF;

            cpu.setRegisterValue(registerX,randValue & value);
        });

        //Dxyn - DRW Vx, Vy, nibble | Display n-byte sprite starting at memory location I at (Vx, Vy), set VF = collision
        instructionMap.put(0xD000, (cpu, opcode) -> {
            int vX = (opcode & 0x0F00) >> 8;
            int vY = (opcode & 0x00F0) >> 4;
            int byteAmount = opcode & 0x000F;

            int xStartCoordinate = cpu.getRegisterValue(vX) % 64;
            int yStartCoordinate = cpu.getRegisterValue(vY) % 32;

            var videoBuffer = cpu.getDisplay().getVideoBuffer();

            cpu.setRegisterValue(0xF, 0x0);

            for(int yOffset = 0; yOffset < byteAmount; yOffset++){
                int sprite = cpu.getMemory().read(cpu.getI()+yOffset);
                for(int xOffset = 0; xOffset < 8; xOffset++){
                    //@TODO find fancier way to set v15 (should only be set if on pixel is turned off)
                    boolean currentSpriteBit = ((sprite >> (7 - xOffset)) & 0x1) == 1;
                    boolean temp = videoBuffer[yStartCoordinate + yOffset][xStartCoordinate + xOffset];
                    videoBuffer[yStartCoordinate + yOffset][xStartCoordinate + xOffset] ^= currentSpriteBit;

                    if(temp != (temp ^ currentSpriteBit)){
                        cpu.setRegisterValue(0xF, 0x1);
                    }
                }
            }
            cpu.getDisplay().setDrawFlag(true);
        });
        //Ex9E - SKP Vx | Skip next instruction if key with the value of Vx is pressed
        instructionMap.put(0xE09E, (cpu, opcode) -> {

        });

        //ExA1 - SKNP Vx | Skip next instruction if key with the value of Vx is not pressed
        instructionMap.put(0xE0A1, (cpu, opcode) -> {

        });

        //Fx07 - LD Vx, DT | Set Vx = delay timer value
        instructionMap.put(0xF007, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int delayTimerValue = cpu.getDelayTimer();
            cpu.setRegisterValue(registerX, delayTimerValue);
        });

        //Fx0A - LD Vx, K | Wait for a key press, store the value of the key in Vx
        instructionMap.put(0xF01A, (cpu, opcode) -> {

        });

        //Fx15 - LD DT, Vx | Set delay timer = Vx
        instructionMap.put(0xF015, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int xValue = cpu.getRegisterValue(registerX);
            cpu.setDelayTimer(xValue);
        });

        //Fx18 - LD ST, Vx | Set sound timer = Vx
        instructionMap.put(0xF018, (cpu, opcode) -> {
            int registerX = (opcode & 0x0F00) >> 8;
            int xValue = cpu.getRegisterValue(registerX);
            cpu.setSoundTimer(xValue);
        });

        //Fx1E - ADD I, Vx | Set I = I + Vx
        instructionMap.put(0xF01E, (cpu, opcode) -> {
            //@todo overflow protection later
            int registerX = (opcode & 0x0F00) >> 8;
            int xValue = cpu.getRegisterValue(registerX);
            cpu.setI(cpu.getI() + xValue);
        });

        //Fx29 - LD F, Vx | Set I = location of sprite for digit Vx
        instructionMap.put(0xF029, (cpu, opcode) -> {

        });

        //Fx33 - LD B, Vx | Store BCD representation of Vx in memory locations I, I+1, and I+2
        instructionMap.put(0xF033, (cpu, opcode) -> {

        });

        //Fx55 - LD [I], Vx | Store registers V0 through Vx in memory starting at location I
        instructionMap.put(0xF055, (cpu, opcode) -> {

        });

        //Fx65 - LD Vx, [I] | Read registers V0 through Vx from memory starting at location I
        instructionMap.put(0xF065, (cpu, opcode) -> {

        });
    }
}

