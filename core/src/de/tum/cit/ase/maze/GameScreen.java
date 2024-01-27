package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    public final OrthographicCamera camera;
    private final BitmapFont font;

    private float dx = 0;
    private float dy = 0;

    private final float screenWidth = 18 * 64;
    private final float screenHeight = 10 * 64;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {
        this.game = game;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.75f;

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");
    }

    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {
        beforeActions();
        renderObjects(delta);
        postActions();
    }

    private void renderObjects(float delta) {
        game.getPlayer().render(game.getSpriteBatch(), delta);
        game.getGameObjects().forEach(obj -> obj.render(game.getSpriteBatch(), delta));
    }

    private void beforeActions() {
        checkEnd();
        handleInput();
        translateCamera();
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen


        // Set up and begin drawing with the sprite batch
        game.getSpriteBatch().setProjectionMatrix(camera.combined);
        camera.update(); // Update the camera

        game.getSpriteBatch().begin(); // Important to call this before drawing anything

    }

    private void checkEnd() {
        if(game.getPlayer().isDeath()) {
            game.showGameOver();
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }
    }

    private void translateCamera() {
        if(game.getPlayer().x - dx < 0) {
            dx -= screenWidth / 2;
            camera.translate(-screenWidth / 2, 0);
        }

        if(game.getPlayer().x - dx > screenWidth - game.getPlayer().width) {
            dx += screenWidth / 2;
            camera.translate(screenWidth / 2, 0);
        }

        if(game.getPlayer().y - dy < 0) {
            dy -= screenHeight / 2;
            camera.translate(0, -screenHeight / 2);
        }

        if(game.getPlayer().y - dy + game.getPlayer().height > screenHeight) {
            dy += screenHeight / 2;
            camera.translate(0, screenHeight / 2);
        }
    }

    private void postActions() {
        game.getSpriteBatch().end(); // Important to call this after drawing everything
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
