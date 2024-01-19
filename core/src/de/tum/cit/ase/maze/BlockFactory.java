package de.tum.cit.ase.maze;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlockFactory {

    public Actor create(float x, float y, BlockType blockType) {
        if(blockType.equals(BlockType.WALL)) {
            return new Wall(x, y);
        }
        else if(blockType.equals(BlockType.DIRT)) {
            return new Dirt(x, y);
        }

        throw new RuntimeException(":(((");
    }
}
