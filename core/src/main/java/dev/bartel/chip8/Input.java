package dev.bartel.chip8;

import com.badlogic.gdx.InputAdapter;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Input extends InputAdapter {
    Map<Integer, Integer> keyMap = new HashMap<>();

    boolean[] previousFrameKeys = new boolean[16];
    boolean[] currentFrameKeys = new boolean[16];
    boolean[] currentKeys = new boolean[16];
    public Input(){
        initKeyMapping();
    }

    private void initKeyMapping(){
        keyMap.put(com.badlogic.gdx.Input.Keys.NUM_1, 0x1);
        keyMap.put(com.badlogic.gdx.Input.Keys.NUM_2, 0x2);
        keyMap.put(com.badlogic.gdx.Input.Keys.NUM_3, 0x3);
        keyMap.put(com.badlogic.gdx.Input.Keys.NUM_4, 0xC);

        keyMap.put(com.badlogic.gdx.Input.Keys.Q, 0x4);
        keyMap.put(com.badlogic.gdx.Input.Keys.W, 0x5);
        keyMap.put(com.badlogic.gdx.Input.Keys.E, 0x6);
        keyMap.put(com.badlogic.gdx.Input.Keys.R, 0xD);

        keyMap.put(com.badlogic.gdx.Input.Keys.A, 0x7);
        keyMap.put(com.badlogic.gdx.Input.Keys.S, 0x8);
        keyMap.put(com.badlogic.gdx.Input.Keys.D, 0x9);
        keyMap.put(com.badlogic.gdx.Input.Keys.F, 0xE);

        keyMap.put(com.badlogic.gdx.Input.Keys.Z, 0xA);
        keyMap.put(com.badlogic.gdx.Input.Keys.X, 0x0);
        keyMap.put(com.badlogic.gdx.Input.Keys.C, 0xB);
        keyMap.put(com.badlogic.gdx.Input.Keys.V, 0xF);
    }

    public void setPreviousFrameKeys(){
        System.arraycopy(currentKeys, 0, previousFrameKeys, 0, 16);
    }

    public void setCurrentFrameKeys(){
        System.arraycopy(currentKeys, 0, currentFrameKeys, 0, 16);
    }

    public boolean checkDifference(int key){
        if(previousFrameKeys[key])
            return currentFrameKeys[key] != previousFrameKeys[key];
        return false;
    }

    public boolean isSpecificKeyPressed(int key){
        return currentKeys[key];
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!keyMap.containsKey(keycode))
            return false;
        currentKeys[keyMap.get(keycode)] = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(!keyMap.containsKey(keycode))
            return false;
        currentKeys[keyMap.get(keycode)] = false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for(int i = 0; i< currentKeys.length; i++){
            s.append(String.format("%01x", i)).append(": ").append(currentKeys[i]).append("\n");
        }
        return s.toString();
    }
}

