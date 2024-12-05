package enemies;

import managers.EnemyManager;

import static helpz.Constants.Enemies.BAT;

public class Bat extends Enemy{

    public Bat(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, BAT, enemyManager);
    }
}
