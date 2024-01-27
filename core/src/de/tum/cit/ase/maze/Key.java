package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Key extends StaticBlock {

    public Key(MazeRunnerGame game, float x, float y) {
        super(game, x, y, 82);
        game.getGameObjects().add(new Path(game, x, y));
    }
}
