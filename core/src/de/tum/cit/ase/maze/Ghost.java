package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class Ghost extends Player {
    public Ghost(MazeRunnerGame game, float x, float y) {
        super(game, x, y);
        direction = 3;
        game.getGameObjects().add(new Path(game, x, y));
    }

    @Override
    protected void loadAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal("ghost.png"));
        Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class);

        int frameWidth = 64;
        int frameHeight = 64;

        for(int i = 0; i < 3; ++i) {
            walkFrames.add(
                new TextureRegion(walkSheet, i * frameWidth, 0, frameWidth, frameHeight)
            );
        }
        animations.add(new Animation<>(0.1f, walkFrames));
    }

    @Override
    protected void drawAnimation(SpriteBatch batch) {
        batch.draw(
            animations.get(0).getKeyFrame(animationTime, true),
            x,
            y,
            64,
            64
        );
    }

    @Override
    protected void handleInput(float delta) {
        float deltaX = 0f;
        float deltaY = 0f;

        if(direction == 2) {
            deltaY = speed * delta;
            animationTime += delta;
        }
        else if(direction == 0) {
            deltaY = -speed * delta;
            animationTime += delta;
        }
        else if(direction == 1) {
            deltaX = -speed * delta;
            animationTime += delta;
        }
        else if(direction == 3) {
            deltaX = speed * delta;
            animationTime += delta;
        }

        setPosition(x + deltaX, y + deltaY);
        if(collidesWithWall()) {
            setPosition(x - deltaX, y - deltaY);
            chooseRandomDirection();
        }
    }

    private void chooseRandomDirection() {
        int prev = direction;
        while((direction = new Random().nextInt(0, 4)) == prev){

        }
        System.out.println("Random direction chosen: " + direction);
    }
}
