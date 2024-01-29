package de.tum.cit.ase.maze.game_objects;

import de.tum.cit.ase.maze.MazeRunnerGame;

/**
 * The class represents a static trap. Whenever the player collides it, he gets damaged.
 */
public class Trap extends StaticBlock {
    public Trap(MazeRunnerGame game, float x, float y) {
        super(game, x, y, 74);
    }
}
