package main;

import helpz.LoadSave;
import managers.TileManager;
import scenes.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;

public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;


    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;
    private GameOver gameOver;

    private TileManager tileManager;

    public Game() {

        LoadSave.createFolder();
        createDefaultLevel();

        initClasses();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("TDGame");
        add(gameScreen);
        pack();
        setVisible(true);
    }

    private void initClasses() {
        tileManager = new TileManager();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
        gameOver = new GameOver(this);
    }

    private void start() {
        gameThread = new Thread(this) {
        };

        gameThread.start();
    }

    private void updateGame() {
        switch (GameStates.gameState) {
            case PLAYING -> {
                playing.update();
            }
            case MENU -> {
            }
            case SETTINGS -> {
            }
            case EDIT -> {
                editing.update();
            }
        }
    }

    public static void main(String[] args) {

        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();

    }

    private void createDefaultLevel() {
        int[] arr = new int[400];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }

        LoadSave.createLevel(arr);
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while (true) {

            now = System.nanoTime();
            //Render
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }
            //Update
            if (now - lastUpdate > timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS= " + frames + " | UPS= " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    //Getters and setters
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Settings getSettings() {
        return settings;
    }

    public Editing getEditor() {
        return editing;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    /////////////////////////////////////////////////////////////////////////////
    //music
    /////////////////////////////////////////////////////////////////////////////
    private Clip musicClip;
    private FloatControl volumeControl;

    public void playMusic() {
        new Thread(() -> {
            try {
                AudioInputStream audioStream = LoadSave.getMusic();
                musicClip = AudioSystem.getClip();
                musicClip.open(audioStream);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
                musicClip.start();

                volumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(-20.0f); // Set default volume

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setVolume(float volume) {
        // Volume range is typically -80.0 (mute) to 6.0 (max volume)
        if (volumeControl != null) {
            volumeControl.setValue(volume);
        }
    }

    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }
}
