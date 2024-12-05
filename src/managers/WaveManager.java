package managers;

import events.Wave;
import scenes.Playing;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WaveManager {

    Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private int enemySpawnTickLimit = 60 * 1;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex, waveIndex;
    private int waveTickLimit = 60 * 5;
    private int waveTick = 0;
    private boolean waveStartTimer;
    private boolean waveTickTimerOver;

    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    public void update() {
        if (enemySpawnTick < enemySpawnTickLimit)
            enemySpawnTick++;

        if (waveStartTimer) {
            waveTick++;
            if (waveTick >= waveTickLimit) {
                waveTickTimerOver = true;
            }
        }
    }

    public void increaseWaveIndex() {
        waveIndex++;
        waveTickTimerOver = false;
        waveStartTimer = false;
    }

    public int getNextEnemy() {
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    public void addRandomEnemies(ArrayList<Integer> enemyList) {
        Random random = new Random();
        int numEnemies = 10 + random.nextInt(21);  // 10 + (0 to 20) gives 10 to 30
        for (int i = 0; i < numEnemies; i++) {
            int enemyType = random.nextInt(4); // Random type between 0 and 3
            enemyList.add(enemyType);
        }
        waves.add(new Wave(enemyList));
    }

    private void createWaves() {
        ArrayList<Integer> enemyList = new ArrayList<>();

        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))));

        for (int i = 0; i < 100; i++) {
            addRandomEnemies(enemyList);
        }

        /*waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3, 3, 3, 3, 3, 0, 0, 0, 0, 0))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 0, 0, 0, 0, 0))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))));*/
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave() {
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public boolean isWaveTimerOver() {
        return waveTickTimerOver;
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    public float getTimeLeft() {
        float ticksLeft = waveTickLimit - waveTick;
        return ticksLeft / 60.0f;
    }

    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }

    public void reset() {
        waves.clear();
        createWaves();
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimerOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }
}
