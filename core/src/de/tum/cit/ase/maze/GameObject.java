package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameObject {
    protected Sprite sprite;
    protected float x;
    protected float y;

    public GameObject(String fileName) {
        Texture texture = new Texture(Gdx.files.internal(fileName));
        this.sprite = new Sprite(texture);
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void update();
}
