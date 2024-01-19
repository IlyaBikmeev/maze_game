package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Player extends Actor {
    private float speed = 100f;
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("coin.png")));

    public Player() {
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveBy(-speed * delta, 0);
        } else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveBy(0, speed * delta);
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveBy(speed * delta, 0);
        } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveBy(0, -speed * delta);
        }

        sprite.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

}
