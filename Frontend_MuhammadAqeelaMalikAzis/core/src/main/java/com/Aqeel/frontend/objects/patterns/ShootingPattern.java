package com.Aqeel.frontend.objects.patterns.shootingStrategy;

import com.Aqeel.frontend.systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}
