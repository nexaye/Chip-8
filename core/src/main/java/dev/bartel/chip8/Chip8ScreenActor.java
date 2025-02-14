package dev.bartel.chip8;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Setter;

public class Chip8ScreenActor extends Actor {
    private Texture displayTexture;
    @Setter
    private boolean[][] videoBuffer;

    public Chip8ScreenActor(){
        displayTexture = new Texture(64*11-1,32*11, Pixmap.Format.RGBA8888);
        this.setWidth(64*11-1);
        this.setHeight(32*11);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        convertBufferToPixmap();
        batch.draw(displayTexture,getX(),getY());
    }

    private void convertBufferToPixmap(){
        Pixmap pixmap = new Pixmap(64*11-1,32*11,Pixmap.Format.RGBA8888);
        for(int y = 0; y<videoBuffer.length;y++){
            for(int x = 0; x<videoBuffer[y].length;x++){
                if(videoBuffer[y][x]){
                    pixmap.setColor(1,1,1,1);
                    pixmap.fillRectangle(x*11,y*11,10,10);
                    continue;
                }
                pixmap.setColor(0.15f,0.15f,0.15f,1);
                pixmap.fillRectangle(x*11,y*11,10,10);
            }
        }

        displayTexture.draw(pixmap,0,0);
        pixmap.dispose();
    }
}
