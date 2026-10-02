package com.Aqeel.frontend.objects.enemies;
import com.Aqeel.frontend.objects.Collidable;
import com.badlogic.gdx.graphics.Color;

public class Boss extends Enemy {
    public Boss(String name, int hp) {
        super(380, 400, 48, 48, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48, Color.BLUE, name, hp, 5000L);
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        if (other instanceof com.Aqeel.frontend.objects.Player) {
            // TODO: Print "Player touches boss"
            System.out.println("Player touches boss");
        }
    }
}
