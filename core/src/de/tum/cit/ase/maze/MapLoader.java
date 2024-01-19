package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.IOException;
import java.util.Properties;

public class MapLoader {

    public void loadMap(Stage stage) {
        Properties properties = new Properties();
        FileHandle fileHandle = Gdx.files.internal("levels/level-1.properties");

        try {
            properties.load(fileHandle.reader());
            processProperties(properties, stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processProperties(Properties properties, Stage stage) {
        for(Object key : properties.keySet()) {
            String[] coordinates = ((String) key).split(",");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            int textureType = Integer.parseInt(
                properties.getProperty((String)key)
            );

            Actor actor = new BlockFactory().create(x * 16f, y * 16f, BlockType.of(textureType));
            stage.addActor(actor);
        }
    }

}
