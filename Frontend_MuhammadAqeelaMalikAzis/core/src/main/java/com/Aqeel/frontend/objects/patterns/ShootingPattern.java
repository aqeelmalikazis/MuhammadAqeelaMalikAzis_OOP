package com.Aqeel.frontend.objects.patterns.shootingStrategy;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}
