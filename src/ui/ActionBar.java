package ui;

import helpz.Constants;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.text.DecimalFormat;

import static main.GameStates.*;

public class ActionBar extends Bar {

    private MyButton bMenu, bPause;
    private Playing playing;

    private MyButton[] towerButtons;
    private Tower selectedTower, displayedTower;
    private MyButton sellTower, upgradeTower;

    private DecimalFormat formatter;

    private int gold = 100;
    private int lives = 25;
    private boolean showTowerCost;
    private int towerCostType;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bPause = new MyButton("Pause", 2, 682, 100, 30);

        towerButtons = new MyButton[3];
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);
        }

        sellTower = new MyButton("Sell", 420, 702, 80, 25);
        upgradeTower = new MyButton("Upgrade", 545, 702, 80, 25);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bPause.draw(g);

        for (MyButton b : towerButtons) {
            g.setColor(Color.gray);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        drawButtons(g);

        drawDisplayedTower(g);

        drawWaveInfo(g);

        drawGoldAmount(g);

        if (showTowerCost)
            drawTowerCosts(g);

        if (playing.isGamePaused()) {
            g.setColor(Color.black);
            g.drawString("Game is paused!", 110, 790);
        }

        g.setColor(Color.black);
        g.drawString("Lives: " + lives, 110, 750);
    }

    private void drawTowerCosts(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(280, 650, 120, 50);
        g.setColor(Color.black);
        g.drawRect(280, 650, 120, 50);

        g.drawString("" + getTowerCostName(), 285, 670);
        g.drawString("Cost: " + getTowerCostCost() + "g", 285, 695);

        if (isTowerCostsMoreThanCurrentGold()) {
            g.setColor(new Color(94, 3, 3));
            g.drawString("Not enough gold!", 260, 725);
        }
    }

    private boolean isTowerCostsMoreThanCurrentGold() {
        return getTowerCostCost() > gold;
    }

    private String getTowerCostName() {
        return Constants.Towers.getName(towerCostType);
    }

    private int getTowerCostCost() {
        return Constants.Towers.getTowerCost(towerCostType);
    }

    private void drawGoldAmount(Graphics g) {
        g.drawString("Gold " + gold, 110, 725);
    }

    private void drawWaveInfo(Graphics g) {

        g.setColor(Color.black);
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    private void drawWaveTimerInfo(Graphics g) {
        if (playing.getWaveManager().isWaveTimerStarted()) {
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = formatter.format(timeLeft);

            g.drawString("Time left: " + formattedText, 425, 750);
        }
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave " + (current + 1) + " / " + size, 425, 770);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies left " + remaining, 425, 790);
    }

    private void drawDisplayedTower(Graphics g) {
        if (displayedTower != null) {
            g.setColor(Color.gray);
            g.fillRect(410, 645, 220, 85);
            g.setColor(Color.black);
            g.drawRect(410, 645, 220, 85);
            g.drawRect(420, 650, 50, 50);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 420, 650, 50, 50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("" + Constants.Towers.getName(displayedTower.getTowerType()), 480, 660);
            g.drawString("ID: " + displayedTower.getId(), 480, 675);
            g.drawString("Tier " + displayedTower.getTier(), 560, 660);
            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);

            sellTower.draw(g);
            drawButtonFeedback(g, sellTower);

            if (displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
                upgradeTower.draw(g);
                drawButtonFeedback(g, upgradeTower);
            }

            if (sellTower.isMouseOver()) {
                g.setColor(new Color(94, 3, 3));
                g.drawString("Sell for " + getSellAmount(displayedTower) + "g", 480, 695);
            } else if (upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)) {
                g.setColor(Color.blue);
                g.drawString("Upgrade for " + getUpgradeAmount(displayedTower) + "g", 480, 695);
            }
        }
    }

    private int getUpgradeAmount(Tower displayedTower) {
        return (int) (Constants.Towers.getTowerCost(displayedTower.getTowerType()) * 0.3f);
    }

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
        upgradeCost *= 0.5f;
        return Constants.Towers.getTowerCost(displayedTower.getTowerType()) / 2 + upgradeCost;
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2, displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2, (int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
            playing.getGame().stopMusic();
        } else if (bPause.getBounds().contains(x, y)) {
            togglePause();
        } else {
            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTowerClicked();
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
                    upgradeTowerClicked();
                    return;
                }
            }
        }
        for (MyButton b : towerButtons) {
            if (b.getBounds().contains(x, y)) {
                if (!isGoldEnoughForTower(b.getId()))
                    return;

                selectedTower = new Tower(0, 0, -1, b.getId());
                playing.setSelectedTower(selectedTower);
                return;
            }
        }
    }

    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());

        if (playing.isGamePaused()) {
            bPause.setText("Unpause");
        } else {
            bPause.setText("Pause");
        }
    }

    public void removeOneLife() {
        lives--;
        if (lives <= 0) {
            SetGameState(GAME_OVER);
            playing.getGame().stopMusic();
        }
    }

    private void upgradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold -= getUpgradeAmount(displayedTower);
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        gold += Constants.Towers.getTowerCost(displayedTower.getTowerType()) / 2;

        int upgradeCost = (displayedTower.getTier() - 1) * getUpgradeAmount(displayedTower);
        upgradeCost *= 0.5f;
        gold += upgradeCost;

        displayedTower = null;
    }

    private boolean isGoldEnoughForTower(int towerType) {
        return gold >= Constants.Towers.getTowerCost(towerType);
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        showTowerCost = false;
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);

        for (MyButton b : towerButtons) {
            b.setMouseOver(false);
        }
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if (bPause.getBounds().contains(x, y))
            bPause.setMouseOver(true);
        else {
            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMouseOver(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgradeTower.setMouseOver(true);
                    return;
                }
            }
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
                    return;
                }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if (bPause.getBounds().contains(x, y))
            bPause.setMousePressed(true);
        else {
            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMousePressed(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgradeTower.setMousePressed(true);
                    return;
                }
            }
        }
        for (MyButton b : towerButtons)
            if (b.getBounds().contains(x, y)) {
                b.setMousePressed(true);
                return;
            }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bPause.resetBooleans();
        for (MyButton b : towerButtons)
            b.resetBooleans();
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();
    }

    public void displayTower(Tower t) {
        displayedTower = t;
    }

    public void payForTower(int towerType) {
        this.gold -= Constants.Towers.getTowerCost(towerType);
    }

    public void addGold(int reward) {
        this.gold += reward;
    }

    public int getLives() {
        return lives;
    }

    public void resetEverything() {
        lives = 25;
        towerCostType = 0;
        showTowerCost = false;
        gold = 100;
        selectedTower = null;
        displayedTower = null;
    }
}
