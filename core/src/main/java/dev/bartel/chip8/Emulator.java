package dev.bartel.chip8;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Emulator extends ApplicationAdapter {
    private Cpu cpu;
    private Memory memory;
    private Display display;
    private Input input;
    private Sound sound;

    @Override
    public void create() {
        //batch = new SpriteBatch();
        //image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        //batch.begin();
        //batch.draw(image, 140, 210);
        //batch.end();
    }

    @Override
    public void dispose() {
        //batch.dispose();
        //image.dispose();
    }
}
