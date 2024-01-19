package de.tum.cit.ase.maze;

public enum BlockType {
    WALL(0),
    DIRT(1);

    int number;

    BlockType(int number) {
        this.number = number;
    }

    public static BlockType of(int number) {
        for(BlockType type : BlockType.values()) {
            if(type.number == number) {
                return type;
            }
        }
        throw new IllegalArgumentException();
    }
}
