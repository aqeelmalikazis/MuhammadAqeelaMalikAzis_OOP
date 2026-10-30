package com.Aqeel.frontend.systems;

public class CollisionReferee {
    public void resolveCollisions() {
        // Hehe, leave this empty for now
        public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
            if (player == null || entities == null || bulletManager == null) return;

            List<GameObject> newDrops = new ArrayList<>();

            // Collision logic is divided into 3 stages
            //
            // TODO: Code for Question 3
            //
            // TODO: Code for Question 4
            //
            // TODO: Code for Question 5
            //
            // ...
            for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
                if (bullet.isDestroyed()) continue;

                for (GameObject entity : entities) {
                    if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                        if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                            // Core Business Logic:
                            // Enemy takes damage, player gets score, etc.
                            // Bullet gets marked to be destroyed
                            newDrops.add(EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER));
                        }
                    }
                }
            }

            // Other arbitration/coordination involving checks and manipulation against active entities ...

            // Safely merge newly spawned entities into active collection

            entities.addAll(newDrops);
        }

    }
}
