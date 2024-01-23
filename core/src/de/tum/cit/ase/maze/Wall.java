package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wall extends GameObject {
    private Sprite sprite;

    public Wall(MazeRunnerGame game, float x, float y, float width, float height) {
        super(game, x, y, width, height);
        loadSprite();
    }

    private void loadSprite() {
        sprite = new Sprite(
            new Texture(Gdx.files.internal("test.png"))
        );
        sprite.setPosition(x, y);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.draw(sprite.getTexture(), x, y);
    }
}
