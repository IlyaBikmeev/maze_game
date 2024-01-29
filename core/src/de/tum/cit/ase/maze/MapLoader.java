package de.tum.cit.ase.maze;

import com.badlogic.gdx.files.FileHandle;
import de.tum.cit.ase.maze.game_objects.GameObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * This class is used for loading map from files.
 */
public class MapLoader {

    private final MazeRunnerGame game;

    //2d array for storing the map.
    private final int[][] map = new int[MAX_WIDTH][MAX_HEIGHT];

    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 100;

    public MapLoader(MazeRunnerGame game) {
        this.game = game;
    }

    /**
     * Clears all the map. -1 represents empty cell.
     */
    private void clear() {
        for (int i = 0; i < MAX_WIDTH; i++) {
            for(int j = 0; j < MAX_HEIGHT; ++j) {
                map[i][j] = -1;
            }
        }
    }

    /**
     * Loads the map from file.
     * @param file - file to load from.
     * @return List of loaded game objects.
     */
    public List<GameObject> fromFile(FileHandle file) {
        clear();
        Properties properties = new Properties();

        List<GameObject> res = new LinkedList<>();
        try {
            properties.load(file.reader());
            processProperties(properties, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Helper method for load the map
     * @param properties - Proceeded properties
     * @param gameObjects - List where to store all the objects.
     */
    private void processProperties(Properties properties,
                                   List<GameObject> gameObjects) {
        for(Object key : properties.keySet()) {

            try {
                String[] coordinates = ((String) key).split(",");
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                int type = Integer.parseInt(
                    properties.getProperty((String) key)
                );
                map[x][y] = type;
                gameObjects.add(GameObject.of(game, x, y, type));
            } catch (Exception ignored) {}
        }

        for(int x = 0; x < MAX_WIDTH; ++x) {
            for(int y = 0; y < MAX_HEIGHT; ++y) {
                if(map[x][y] == -1) {
                    gameObjects.add(GameObject.of(game, x, y, -1));
                }
            }
        }
    }
}
