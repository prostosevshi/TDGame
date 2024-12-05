package enemies;

import managers.EnemyManager;

import static helpz.Constants.Enemies.WOLF;

public class Wolf extends Enemy{

    public Wolf(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, WOLF, enemyManager);
    }
}
