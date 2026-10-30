package com.Aqeel.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;

public class BulletManager {
    // 1. Pool Queues for Object Recycling (Object Pool Pattern)
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

    // 2. Lists of Active Bullets on Screen
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    public BulletManager() {
        // TODO:
        // 1. Initialize playerBulletPool and enemyBulletPool as empty LinkedLists
        // 2. Initialize activePlayerBullets and activeEnemyBullets as empty ArrayLists
        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();
        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();
    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        return null; // We'll fill in the pool logic during CS
    }
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        return null; // We'll fill in the pool logic during CS
    }

    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    // Borrowing Player Bullet from Pool (5 Points)
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        // TODO:
        // 1. Check whether playerBulletPool is not empty.
        //    If so, poll from the pool. If not, create a new one using the Factory.
        // 2. Re-initialize the bullet.
        // 3. Add the bullet to the activePlayerBullets list.
        // 4. Return the bullet instance.
        if(!playerBulletPool.isEmpty()) {
            bullet = playerBulletPool.pull();
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage);
        }
        bullet.init(
            x, y, vx,vy,
            BulletType.AMULET,
            damage,
            true
        );

        activePlayerBullets.add(bullet);
        return bullet;
    }

    // Borrowing Enemy Bullet from Pool (5 Points)
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        // TODO:
        // 1. Check whether enemyBulletPool is not empty:
        //    If so, poll from the pool. If not, create a new one using the Factory.
        // 2. Re-initialize the bullet.
        // 3. Add the bullet to the activeEnemyBullets list
        // 4. Return the bullet instance
        if(!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.pull();
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage);
        }
        bullet.init(
            x, y, vx,vy,
            BulletType.DAMAKU,
            damage,
            true
        );

        activePlayerBullets.add(bullet);
        return bullet;
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        // 1. Player bullet recycling & movement (5 Points)
        // TODO:
        // - Copy-paste the iterator loop from updateAndClean() in Main.java
        // - Change the class referenced by the iterator to `Bullet` instead of T
        //   and change the referenced list to activePlayerBullets
        // - Change the check to whether the bullet is off-screen
        //   OR the bullet is already marked as destroyed
        //   - If YES:
        //     * Mark the bullet as destroyed (destroy)
        //     * Safely remove the bullet from the active bullet list
        //     * Return the bullet to the pool queue
        Iterator<Bullet> activePlayerBullets = activePlayerBullets.iterator();
        while (playeriterator.hasNext()) {
            Bullet bullet = playerIterator.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                bullet.isDestroy();
                playerIterator.remove(); // Safe removal using Iterator!
                playerBulletPool.offer(bullet);
            }
        }

        // 2. Enemy bullet recycling & movement (5 Points)
        // TODO:
        // - Follow the exact same procedure as above for activeEnemyBullets
        // - Enemy bullets that go off-screen or are destroyed are returned to enemyBulletPool via offer()
        Iterator<Bullet> EnemyIterator = EnemyIterator();
        while (EnemyIterator.hasNext()) {
            Bullet bullet = EnemyIterator.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                bullet.isDestroy();
                EnemyIterator.remove(); // Safe removal using Iterator!
                playerBulletPool.offer(bullet);
            }
        }
    }
    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    // Helper methods to access pools and active lists
    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }
}
