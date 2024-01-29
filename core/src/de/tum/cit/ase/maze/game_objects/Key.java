package de.tum.cit.ase.maze.game_objects;

import de.tum.cit.ase.maze.MazeRunnerGame;

/**
 * The class represents the key the player should pick up before he reaches the exit.
 */
public class Key extends StaticBlock {

    public Key(MazeRunnerGame game, float x, float y) {
        super(game, x, y, 82);
        game.getGameObjects().add(new Path(game, x, y));
    }
}
