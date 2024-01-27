package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class MapLoader {

    private final MazeRunnerGame game;
    private final int[][] map = new int[72][40];

    private static final int MAX_WIDTH = 72;
    private static final int MAX_HEIGHT = 40;

    public MapLoader(MazeRunnerGame game) {
        this.game = game;
        for (int i = 0; i < MAX_WIDTH; i++) {
            for(int j = 0; j < MAX_HEIGHT; ++j) {
                map[i][j] = -1;
            }
        }
    }

    public List<GameObject> fromFile(FileHandle file) {
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

    private void processProperties(Properties properties,
                                   List<GameObject> gameObjects) {
        for(Object key : properties.keySet()) {
            String[] coordinates = ((String) key).split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            int type = Integer.parseInt(
                properties.getProperty((String)key)
            );
            map[x][y] = type;
            gameObjects.add(GameObject.of(game, x, y, type));
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
