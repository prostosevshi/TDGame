package inputs;

import main.Game;
import main.GameStates;
import scenes.Playing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.*;

public class KeyboardListener implements KeyListener {
    private Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(GameStates.gameState == EDIT) {
            game.getEditor().keyPressed(e);
        }
        else if(GameStates.gameState == PLAYING) {
            game.getPlaying().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
