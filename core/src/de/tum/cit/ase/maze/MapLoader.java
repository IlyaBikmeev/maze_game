package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class MapLoader {

    private final MazeRunnerGame game;

    public MapLoader(MazeRunnerGame game) {
        this.game = game;
    }

    public List<GameObject> loadMap(int level) {
        Properties properties = new Properties();
        FileHandle fileHandle = Gdx.files.internal(
            String.format("levels/level-%d.properties", level)
        );

        List<GameObject> res = new LinkedList<>();
        try {
            properties.load(fileHandle.reader());
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
            gameObjects.add(GameObject.of(game, x, y, type));
        }
    }
}
