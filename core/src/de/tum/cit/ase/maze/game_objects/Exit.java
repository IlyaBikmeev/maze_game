package de.tum.cit.ase.maze.game_objects;

import de.tum.cit.ase.maze.MazeRunnerGame;

/**
 * The class represents the exit player should find in order to win the game
 */
public class Exit extends StaticBlock {

    public Exit(MazeRunnerGame game, float x, float y) {
        super(game, x, y, 48);
    }
}
