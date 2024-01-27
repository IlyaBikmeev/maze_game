package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class StaticBlock extends GameObject {
    protected static Texture objectsSheet =
        new Texture(Gdx.files.internal("basictiles.png"));

    private TextureRegion texture;
    private int blockWidth;
    private int blockHeight;

    public StaticBlock(MazeRunnerGame game, float x, float y, int blockNumber) {
        super(game, x, y, 64, 64);

        this.blockWidth = 16;
        this.blockHeight = 16;

        int blockX = blockNumber % 8 * blockWidth;
        int blockY = blockNumber / 8 * blockHeight;

        texture = new TextureRegion(objectsSheet, blockX, blockY, 16, 16);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        if(visible) {
            batch.draw(texture, x, y, width, height);
        }
    }
}
