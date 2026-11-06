package com.Aqeel.frontend.objects.enemies;

import com.Aqeel.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Color;
import com.Aqeel.frontend.objects.patterns.shootingStrategy.ShootingPattern;
import com.Aqeel.frontend.objects.patterns.shootingStrategy.SpreadShot;

public class Boss extends Enemy {
    // TODO: Declare a private field named `shootingPattern` of type ShootingPattern
    //       and immediately initialize it with the `SpreadShot` we just created.
    //       Make sure speed is 200f, spread angle is 30f, and 3 bullets (num) are fired.
    private ShootingPattern shootingPattern = new SpreadShot(200f, 3, 30f);

    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    // TODO: Create the shootBullet(BulletManager bulletManager) method:
    //       If shootingPattern is not null and the boss hasn't been destroyed,
    //       execute shootingPattern at the boss's center cone coordinates (x + width / 2 - 8, y).
    public void shootBullet(BulletManager bulletManager) {
        if (shootingPattern != null && !isDestroyed()) {
            shootingPattern.execute(x + width / 2 - 8, y, bulletManager, false);
        }
    }

    // TODO: Provide a getter and setter for shootingPattern
    public ShootingPattern getShootingPattern() { return shootingPattern; }
    public void setShootingPattern(ShootingPattern shootingPattern) { this.shootingPattern = shootingPattern; }
}
