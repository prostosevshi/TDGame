package objects;

import helpz.Constants;

import static helpz.Constants.Towers.*;

public class Tower {
    private int x, y, id, towerType, cdTick, dmg;
    private float range, cooldown;
    private int tier;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        tier = 1;
        setDefaultDmg();
        setDefaultRange();
        setDefaultCooldown();
    }

    private void setDefaultCooldown() {
        cooldown = Constants.Towers.getDefaultCooldown(towerType);
    }

    private void setDefaultRange() {
        range = Constants.Towers.getDefaultRange(towerType);
    }

    private void setDefaultDmg() {
        dmg = Constants.Towers.getStartDmg(towerType);
    }

    public void upgradeTower() {
        this.tier++;

        switch (towerType) {
            case ARCHER:
                dmg += 2;
                range += 20;
                cooldown -= 5;
                break;
            case CANNON:
                dmg += 5;
                range += 20;
                cooldown -= 15;
                break;
            case WIZARD:
                dmg += 0;
                range += 20;
                cooldown -= 10;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTowerType() {
        return towerType;
    }

    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void update() {
        cdTick++;
    }

    public boolean isCooldownOver() {
        return cdTick >= cooldown;
    }

    public void resetCooldown() {
        cdTick = 0;
    }

    public int getTier() {
        return tier;
    }
}
