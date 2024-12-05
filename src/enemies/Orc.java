package enemies;

import managers.EnemyManager;

import static helpz.Constants.Enemies.ORC;

public class Orc extends Enemy{

    public Orc(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, ORC, enemyManager);
    }
}
