package enemies;

import helpz.Constants;

import java.awt.*;

import static helpz.Constants.Direction.*;

public abstract class Enemy {

    protected float x, y;
    protected Rectangle bounds;
    protected int health, maxHealth;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive = true;
    protected int slowTickLimit = 120;
    protected int slowTick = slowTickLimit;


    public Enemy(float x, float y, int id, int enemyType) {
        this.x = x;
        this.y = y;
        this.ID = id;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDir = 1;
        setStartHealth();
    }

    private void setStartHealth() {
        health = helpz.Constants.Enemies.GetStartHealth(enemyType);
        maxHealth = health;
    }

    public void slow() {
        slowTick = 0;
    }

    public void hurt(int dmg) {
        this.health -= dmg;
        if (this.health <= 0) {
            alive = false;
        }
    }

    public void move(float speed, int dir) {
        lastDir = dir;
        if (slowTick < slowTickLimit) {
            slowTick++;
            speed *= 0.25f;
        }
        switch (dir) {
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }

        updateHitBox();
    }

    private void updateHitBox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public void setPos(int x, int y) {
        //for pos fix
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getID() {
        return ID;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDir() {
        return lastDir;
    }

    public float getHealthBarFloat() {
        return health / (float) maxHealth;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isSlowed() {
        return slowTick < slowTickLimit;
    }
}
