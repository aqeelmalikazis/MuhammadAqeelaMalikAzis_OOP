package com.Aqeel.frontend.systems;

import com.Aqeel.frontend.objects.GameObject;
import com.Aqeel.frontend.objects.Player;
import com.Aqeel.frontend.objects.bullets.Bullet;
import com.Aqeel.frontend.objects.enemies.Enemy;
import com.Aqeel.frontend.objects.items.Item;
import com.Aqeel.frontend.objects.items.ItemType;

import java.util.ArrayList;
import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        // 1. Player bullets vs enemies.
        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;

            for (GameObject entity : entities) {
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                        System.out.println("[CollisionReferee] Player Bullet hit " + enemy.getName()
                                + " for " + bullet.getDamage() + " DMG!");
                        boolean defeated = enemy.takeDamage(bullet.getDamage());
                        if (defeated) {
                            player.addScore(enemy.getScoreValue());
                            Item drop = EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER);
                            newDrops.add(drop);
                            System.out.println("[CollisionReferee] Defeated " + enemy.getName()
                                    + "! Dropped POWER item at (" + enemy.getX() + ", " + enemy.getY() + ")");
                        }
                        bullet.destroy();
                        break;
                    }
                }
            }
        }

        // 2. Enemy bullets vs player: direct core hits take priority over grazes.
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) {
            if (bullet.isDestroyed()) continue;
            if (player.isDestroyed()) break;

            if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                if (!player.isInvulnerable()) {
                    System.out.println("[CollisionReferee] CORE HIT! Enemy Bullet hit " + player.getName()
                            + " for " + bullet.getDamage() + " DMG!");
                    player.takeDamage(100, bulletManager, entities);
                    bullet.destroy();
                    break;
                }
            } else if (!bullet.hasBeenGrazed()
                    && bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                bullet.setGrazed(true);
                player.addGraze();
                System.out.println("[CollisionReferee] GRAZE! " + player.getName()
                        + " grazed an enemy bullet (+50 pts)!");
            }
        }

        // 3. Player vs items and enemies.
        if (!player.isDestroyed()) {
            for (GameObject entity : entities) {
                if (entity == player || entity.isDestroyed()) continue;

                if (entity instanceof Item item) {
                    if (player.getCoreHitbox().overlaps(item.getCoreHitbox())
                            || player.getGrazeHitbox().overlaps(item.getCoreHitbox())) {
                        System.out.println("[CollisionReferee] Player collected item: " + item.getItemType());
                        player.collectItem(item);
                    }
                } else if (entity instanceof Enemy enemy) {
                    if (!player.isInvulnerable()
                            && player.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                        System.out.println("[CollisionReferee] CORE COLLISION! Player collided with "
                                + enemy.getName() + "!");
                        player.takeDamage(100, bulletManager, entities);
                    }
                }
            }
        }

        entities.addAll(newDrops);
    }
}
