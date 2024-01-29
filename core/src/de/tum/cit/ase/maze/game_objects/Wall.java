package de.tum.cit.ase.maze.game_objects;

import de.tum.cit.ase.maze.MazeRunnerGame;

import java.util.Random;

public class Wall extends StaticBlock implements Solid {

    public Wall(MazeRunnerGame game, float x, float y) {
        super(game, x, y, new Random().nextInt(8));
    }
}