package dev.bartel.chip8;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Emulator extends ApplicationAdapter {
    private Cpu cpu;
    private Memory memory;
    private Display display;
    private Input input;
    private Sound sound;

    private Stage mainStage;
    private Table rootTable;
    private Chip8ScreenActor gameScreenActor;
    private InstructionSet instructionSet;

    private Drawable createColorDrawable(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    @Override
    public void create() {

        memory = new Memory();
        display = new Display();
        sound = new Sound();
        input = new Input();
        instructionSet = new Chip8InstructionSet();
        cpu = new Cpu(instructionSet,memory, display, input, sound);
        mainStage = new Stage(new FitViewport(64*11-1,48*11));

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setBackground(createColorDrawable(Color.DARK_GRAY));
        rootTable.setName("rootTable");

        gameScreenActor = new Chip8ScreenActor();
        gameScreenActor.setVideoBuffer(display.getVideoBuffer());

        rootTable.add(gameScreenActor).expand().top();


        mainStage.addActor(rootTable);
        //mainStage.setDebugAll(true);
    }

    @Override
    public void render() {
        cpu.cycle();
        mainStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        mainStage.getViewport().update(width,height,true);
    }

    @Override
    public void dispose() {
    }
}

