package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private List<Animation<TextureRegion>> animations;  //array for animations for each direction
    private float speed = 200f;
    private float animationTime;
    private int direction = 0;

    public Player(MazeRunnerGame game,
                  float x, float y) {
        super(game, x, y, 64, 128);
        this.animations = new ArrayList<>();
        this.loadAnimation();
    }

    private void loadAnimation() {
        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));

        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4;
        int nDirections = 4;

        for (int y = 0; y < nDirections; ++y) {
            Array<TextureRegion> walkFrames = new Array<>(TextureRegion.class);
            for (int col = 0; col < animationFrames; ++col) {
                walkFrames.add(
                    new TextureRegion(
                        walkSheet,
                        col * frameWidth,
                        frameHeight * y,
                        frameWidth,
                        frameHeight)
                );
            }
            animations.add(new Animation<>(0.1f, walkFrames));
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        handleInput(delta);
        drawAnimation(batch);
    }


    private void handleInput(float delta) {
        float deltaX = 0f;
        float deltaY = 0f;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            deltaY = speed * delta;
            animationTime += delta;
            direction = 2;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            deltaY = -speed * delta;
            animationTime += delta;
            direction = 0;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            deltaX = -speed * delta;
            animationTime += delta;
            direction = 3;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            deltaX = speed * delta;
            animationTime += delta;
            direction = 1;
        }

        setPosition(x + deltaX, y + deltaY);
        if(collides()) {
            setPosition(x - deltaX, y - deltaY);
        }
    }

    private boolean collides() {
        return game.getGameObjects().stream()
            .anyMatch(this::collidesWith);
    }

    private void drawAnimation(SpriteBatch batch) {
        batch.draw(
            animations.get(direction).getKeyFrame(animationTime, true),
            x,
            y,
            64,
            128
        );
    }
}
