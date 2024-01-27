package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;

import java.util.LinkedList;
import java.util.List;

/**
 * The MazeRunnerGame class represents the core of the Maze Runner game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class MazeRunnerGame extends Game {
    // Screens
    private MenuScreen menuScreen;
    private GameScreen gameScreen;

    // Sprite Batch for rendering
    private SpriteBatch spriteBatch;

    // UI Skin
    private Skin skin;

    private Player player;

    private List<GameObject> gameObjects;

    private Music backgroundMusic;
    private Music gameMusic;

    private boolean mapLoaded;

    private MapLoader mapLoader = new MapLoader(this);

    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
    }

    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch(); // Create SpriteBatch
        skin = new Skin(Gdx.files.internal("craft/craftacular-ui.json")); // Load UI skin
        // Play some background music
        // Background sound
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("game_music.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);

        goToMenu(); // Navigate to the menu screen
    }

    public void loadMap(FileHandle mapFile) {
        player = new Player(this, 0f, 0f);
        gameObjects = new LinkedList<>();
        gameObjects.addAll(mapLoader.fromFile(mapFile));


        GameObject entryPoint = gameObjects.stream()
            .filter(obj -> obj instanceof EntryPoint)
            .findAny().orElseThrow(() -> new RuntimeException("There's no entry point in the map"));

        player.setPosition(entryPoint.getX(), entryPoint.getY());
        mapLoaded = true;
    }

    private void clear() {
        gameObjects = null;
        mapLoaded = false;
        player = null;
    }

    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        backgroundMusic.play();
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }

    /**
     * Switches to the game screen.
     */
    public void goToGame() {
        backgroundMusic.stop();
        gameMusic.play();

        if(!mapLoaded) {
            return;
        }

        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
        if (menuScreen != null) {
            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
        }
    }

    public void showVictory() {
        gameMusic.stop();
        this.setScreen(new VictoryScreen(this));
    }

    public void showGameOver() {
        gameMusic.stop();
        this.setScreen(new GameOverScreen(this));
    }

    /**
     * Cleans up resources when the game is disposed.
     */
    @Override
    public void dispose() {
        getScreen().hide(); // Hide the current screen
        getScreen().dispose(); // Dispose the current screen
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin
    }

    // Getter methods
    public Skin getSkin() {
        return skin;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Player getPlayer() {
        return player;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
