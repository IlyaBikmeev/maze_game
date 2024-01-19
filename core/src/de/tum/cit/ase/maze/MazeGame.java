package de.tum.cit.ase.maze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class MazeGame extends ApplicationAdapter {

    private Stage stage;
    private BlockFactory blockFactory = new BlockFactory();

    @Override
    public void create() {

        stage = new Stage(new FillViewport(300, 300));

        new MapLoader().loadMap(stage);
        Gdx.input.setInputProcessor(stage);
        Player player = new Player();
        System.out.println(String.format("Screen viewport: %d : %d", stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight()));
        stage.addActor(player);
        float y = 0f;

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void zoomCamera(float value) {
        OrthographicCamera camera = (OrthographicCamera) stage.getCamera();

        float currentZoom = camera.zoom;
        float newZoom = MathUtils.clamp(currentZoom + value, 0.1f, 2.0f);

        float zoomRatio = newZoom / currentZoom;

        camera.position.x *= zoomRatio;
        camera.position.y *= zoomRatio;


        camera.update();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
