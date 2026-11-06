package com.Aqeel.frontend.systems;

import com.Aqeel.frontend.objects.GameObject;
import com.Aqeel.frontend.objects.Player;
import com.Aqeel.frontend.objects.bullets.Bullet;
import com.Aqeel.frontend.objects.enemies.Enemy;
import com.Aqeel.frontend.objects.items.Item;
import com.Aqeel.frontend.objects.items.ItemType;
import com.Aqeel.frontend.systems.BulletManager;
import com.Aqeel.frontend.systems.EntityFactory;

import java.util.ArrayList;
import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        // 1. Mediator checks Player Bullets vs Enemies (Core Hitbox)
        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;

            for (GameObject entity : entities) {
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                        System.out.println("[CollisionReferee] Player Bullet hit " + enemy.getName() + " for " + bullet.getDamage() + " DMG!");
                        boolean defeated = enemy.takeDamage(bullet.getDamage());
                        if (defeated) {
                            player.addScore(enemy.getScoreValue());
                            Item drop = EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER);
                            newDrops.add(drop);
                            System.out.println("[CollisionReferee] Defeated " + enemy.getName() + "! Dropped POWER item at (" + enemy.getX() + ", " + enemy.getY() + ")");
                        }
                        bullet.destroy(); // Mark bullet for recycling by BulletManager
                    }
                }
            }
        }

        // 2. Mediator checks Enemy Bullets vs Player (Core Hitbox vs Graze Hitbox)
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) {
            if (bullet.isDestroyed()) continue;

            if (!player.isDestroyed()) {
                // Priority A: Direct Core Hitbox Collision (Damage & Life Loss)
                if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                    // TODO 1: Wrap the contents of this block with an if (!player.isInvulnerable()) check,
                    if (!player.isInvulnerable()) {
                        System.out.println("[CollisionReferee] CORE HIT! Enemy Bullet hit " + player.getName() + " for " + bullet.getDamage() + " DMG!");
                        // TODO 2: Replace player.takeDamage(bullet.getDamage()) with player.takeDamage(100, bulletManager, entities)
                        player.takeDamage(100, bulletManager, entities);
                        bullet.destroy(); // Mark bullet for recycling by BulletManager
                        // TODO 3: Add break at the end
                        break;
                    }
                    // Priority B: Graze Hitbox Collision (Passes close to player without hitting core)
                    else if (!bullet.hasBeenGrazed() && bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                        bullet.setGrazed(true);
                        // TODO 4: Replace player.addScore(50) with a call to the player's addGraze() method
                        player.addGraze();
                        System.out.println("[CollisionReferee] GRAZE! " + player.getName() + " grazed an enemy bullet (+50 pts)!");
                    }
                }
            }

        // 3. Mediator checks Player vs Items and Enemies (Core vs Graze)
        for (GameObject entity : entities) {
            if (entity.isDestroyed()) continue;

            if (entity instanceof Item item) {
                // Items collected via Core or Graze hitbox
                if (player.getCoreHitbox().overlaps(item.getCoreHitbox()) || player.getGrazeHitbox().overlaps(item.getCoreHitbox())) {
                    System.out.println("[CollisionReferee] Player collected item: " + item.getItemType());
                    player.collectItem(item);
                }
            } else if (entity instanceof Enemy enemy) {
                if (player.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                    // TODO 5: Wrap the contents of this block with `if (!player.isInvulnerable())`
                    if (!player.isInvulnerable()) {
                    System.out.println("[CollisionReferee] CORE COLLISION! Player collided with " + enemy.getName() + "!");
                    // TODO 6: Replace `player.takeDamage(10)` with `player.takeDamage(100, bulletManager, entities)`
                        player.takeDamage(100, bulletManager, entities)
                }
            }
        }

        entities.addAll(newDrops);
    }
}
