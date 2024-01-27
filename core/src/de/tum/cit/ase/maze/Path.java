package de.tum.cit.ase.maze;

import java.util.Random;

public class Path extends StaticBlock {
    public Path(MazeRunnerGame game, float x, float y) {
        super(game, x, y, new Random().nextInt(64, 66));
    }
}
