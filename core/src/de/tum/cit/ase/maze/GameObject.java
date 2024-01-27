package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
    protected MazeRunnerGame game;

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    protected boolean visible;

    protected Rectangle bounds;

    public GameObject(MazeRunnerGame game,
                      float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.visible = true;
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

    public void remove() {
        this.visible = false;
    }

    public abstract void render(SpriteBatch batch, float delta);

    public static GameObject of(MazeRunnerGame game, float x, float y, int type) {
        switch (type) {
            case -1: return new Path(game, x * 64, y * 64);
            case 0: return new Wall(game, x * 64, y * 64);
            case 1: return new EntryPoint(game, x * 64, y * 64);
            case 2: return new Exit(game, x * 64, y * 64);
            case 3: return new Trap(game, x * 64, y * 64);

            case 5: return new Key(game, x * 64, y * 64);
        }

        throw new IllegalArgumentException();
    }
}
