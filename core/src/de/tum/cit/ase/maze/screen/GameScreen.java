package de.tum.cit.ase.maze.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.ase.maze.MazeRunnerGame;
import de.tum.cit.ase.maze.game_objects.Player;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    public final OrthographicCamera camera;
    private final BitmapFont font;

    private float translatedX = 0;
    private float translatedY = 0;

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

    /**
     * Helper method for render all the objects
     * @param delta - amount of time from the last frame
     */
    private void renderObjects(float delta) {
        game.getGameObjects().
            stream()
            .filter(obj -> obj.isVisible() && !(obj instanceof Player))
            .forEach(obj -> obj.render(game.getSpriteBatch(), delta));
        game.getPlayer().render(game.getSpriteBatch(), delta);
        game.getGameObjects().stream()
            .filter(obj -> obj instanceof Player)
            .forEach(obj -> obj.render(game.getSpriteBatch(), delta));
    }

    /**
     * Is called before the rendering
     */
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

    /**
     * Checking whether the player looses or wins the game
     */
    private void checkEnd() {
        if(game.getPlayer().isDeath()) {
            game.showGameOver();
            Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("death.wav"));
            deathSound.play();
        }
    }

    /**
     * Handle user input
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }
    }

    /**
     * Is called for translate camera when the user gets closer to the borders of the viewport
     */
    private void translateCamera() {
        if(game.getPlayer().getX() - translatedX < screenWidth * 0.1f) {
            translatedX -= screenWidth / 2;
            camera.translate(-screenWidth / 2, 0);
        }

        if(game.getPlayer().getX() - translatedX > 0.9 * (screenWidth - game.getPlayer().getWidth())) {
            translatedX += screenWidth / 2;
            camera.translate(screenWidth / 2, 0);
        }

        if(game.getPlayer().getY() - translatedY < screenHeight * 0.1f) {
            translatedY -= screenHeight / 2;
            camera.translate(0, -screenHeight / 2);
        }

        if(game.getPlayer().getY() - translatedY + game.getPlayer().getHeight() > 0.9 * screenHeight) {
            translatedY += screenHeight / 2;
            camera.translate(0, screenHeight / 2);
        }
    }

    /**
     * Is called after rendering all the objects
     */
    private void postActions() {
        drawHud();
        game.getSpriteBatch().end(); // Important to call this after drawing everything
    }

    private void drawHud() {
        BitmapFont font = new FreeTypeFontGenerator(
            Gdx.files.internal("OpenSans.ttf")
        ).generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter());

        font.setColor(Color.BLACK);
        font.getData().setScale(2);

        String info = String.format("Health: %d, Key found: %s", game.getPlayer().getHealth(), game.getPlayer().hasKey());

        font.draw(game.getSpriteBatch(), info, 0.6f * screenWidth + translatedX,
            0.95f * screenHeight + translatedY);
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
