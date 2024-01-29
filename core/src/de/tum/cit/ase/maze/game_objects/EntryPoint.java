package de.tum.cit.ase.maze.game_objects;

import de.tum.cit.ase.maze.MazeRunnerGame;

/**
 * The class represents the entry point the player spawns at
 */
public class EntryPoint extends StaticBlock {
    public EntryPoint(MazeRunnerGame game, float x, float y) {
        super(game, x, y, 50);
    }
}
