package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    protected List<Animation<TextureRegion>> animations;  //array for animations for each direction
    protected float speed = 200f;
    protected float animationTime;
    protected float damageTime;
    protected int direction = 0;

    protected int health = 3;
    protected int keysAmount = 0;

    public Player(MazeRunnerGame game,
                  float x, float y) {
        super(game, x, y, 64.0f / 1.7f, 32f);
        this.animations = new ArrayList<>();
        this.loadAnimation();
    }

    protected void loadAnimation() {
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

    public boolean isDeath() {
        return health <= 0;
    }

    public boolean hasKey() {
        return keysAmount > 0;
    }

    public int getHealth() {
        return health;
    }

    protected void handleInput(float delta) {
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
        if(collidesWithWall()) {
            setPosition(x - deltaX, y - deltaY);
        }

        if(collidesWithTrap()) {
            boolean damaged = damageTime == 0 || damageTime > 1;
            damageTime += delta;

            if(damaged) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
                sound.play(1.f);
                health--;

                if(isDeath()) {
                    Gdx.audio.newSound(Gdx.files.internal("die.mp3")).play();
                }
            }
            System.out.println("Collided with trap. Current health: " + health);
        } else {
            damageTime = 0;
        }

        if(collidesWithKey()) {
            ++keysAmount;
            game.getGameObjects().stream().filter(o -> o instanceof Key).forEach(GameObject::remove);
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
            sound.play(1.f);
        }

        if(collidesWithExit() && hasKey()) {
            game.showVictory();
            Gdx.audio.newSound(Gdx.files.internal("victory.mp3")).play();
        }
    }

    protected boolean collidesWithWall() {
        return game.getGameObjects().stream()
            .filter(obj -> obj instanceof Solid)
            .anyMatch(this::collidesWith);
    }

    protected boolean collidesWithTrap() {
        return game.getGameObjects().stream()
            .filter(obj -> obj instanceof Trap || obj instanceof Ghost)
            .anyMatch(this::collidesWith);
    }

    protected boolean collidesWithKey() {
        return game.getGameObjects().stream()
            .filter(obj -> obj instanceof Key)
            .filter(obj -> obj.visible)
            .anyMatch(this::collidesWith);
    }

    protected boolean collidesWithExit() {
        return game.getGameObjects().stream()
            .filter(obj -> obj instanceof Exit)
            .anyMatch(this::collidesWith);
    }

    protected void drawAnimation(SpriteBatch batch) {
        batch.draw(
            animations.get(direction).getKeyFrame(animationTime, true),
            x,
            y,
            64,
            128
        );
    }
}
