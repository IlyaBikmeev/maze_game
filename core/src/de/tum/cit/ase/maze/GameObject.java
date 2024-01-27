package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected MazeRunnerGame game;

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    protected Rectangle bounds;

    public GameObject(MazeRunnerGame game,
                      float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        setPosition(x, y);
        this.game = game;
    }

    public boolean collidesWith(GameObject other) {
        return bounds.overlaps(other.bounds);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.setBounds(x, y, width, height);
    }

    protected void setBounds(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public abstract void render(SpriteBatch batch, float delta);
}
