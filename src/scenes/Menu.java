package scenes;

import main.Game;
import ui.MyButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {

    private MyButton bPlaying, bSettings, bQuit, bEdit;

    public Menu(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {

        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        bPlaying = new MyButton("Play", x, y, w, h);
        bEdit = new MyButton("Edit", x, y + yOffset, w, h);
        bSettings = new MyButton("Settings", x, y + yOffset * 2, w, h);
        bQuit = new MyButton("Quit", x, y + yOffset * 3, w, h);
    }

    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)) {
            SetGameState(PLAYING);
            game.playMusic();
        }

        if (bEdit.getBounds().contains(x, y)) {
            SetGameState(EDIT);
        }

        if (bSettings.getBounds().contains(x, y)) {
            SetGameState(SETTINGS);
        }

        if (bQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMouseOver(true);
        }

        bEdit.setMouseOver(false);
        if (bEdit.getBounds().contains(x, y)) {
            bEdit.setMouseOver(true);
        }

        bSettings.setMouseOver(false);
        if (bSettings.getBounds().contains(x, y)) {
            bSettings.setMouseOver(true);
        }

        bQuit.setMouseOver(false);
        if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bPlaying.getBounds().contains(x, y)) {
            bPlaying.setMousePressed(true);
        }

        if (bEdit.getBounds().contains(x, y)) {
            bEdit.setMousePressed(true);
        }

        if (bSettings.getBounds().contains(x, y)) {
            bSettings.setMousePressed(true);
        }

        if (bQuit.getBounds().contains(x, y)) {
            bQuit.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        bPlaying.resetBooleans();
        bEdit.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();
    }

    private void drawButtons(Graphics g) {
        bPlaying.draw(g);
        bEdit.draw(g);
        bSettings.draw(g);
        bQuit.draw(g);
    }
}
