package de.tum.cit.ase.maze.game_objects;


import de.tum.cit.ase.maze.MazeRunnerGame;

/**
 * The class represents the foot path
 */
public class Path extends StaticBlock {
    public Path(MazeRunnerGame game, float x, float y) {
        super(game, x, y, 65);
    }
}
