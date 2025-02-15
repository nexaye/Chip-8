package dev.bartel.chip8;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Setter;

public class Chip8ScreenActor extends Actor {
    private final Texture displayTexture;
    @Setter
    private boolean[][] videoBuffer;

    public Chip8ScreenActor(){
        displayTexture = new Texture(64*10,32*10, Pixmap.Format.RGBA8888);
        this.setWidth(64*10);
        this.setHeight(32*10);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        convertBufferToPixmap();
        batch.draw(displayTexture,getX(),getY());
    }

    private void convertBufferToPixmap(){
        Pixmap pixmap = new Pixmap(64*10,32*10,Pixmap.Format.RGBA8888);
        for(int y = 0; y<videoBuffer.length;y++){
            for(int x = 0; x<videoBuffer[y].length;x++){
                if(videoBuffer[y][x]){
                    pixmap.setColor(new Color(0x306230FF));
                    pixmap.fillRectangle(x*10,y*10,10,10);
                    continue;
                }
                pixmap.setColor(new Color(0x9bbc0fFF));
                pixmap.fillRectangle(x*10,y*10,10,10);
            }
        }

        displayTexture.draw(pixmap,0,0);
        pixmap.dispose();
    }
}
