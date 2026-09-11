package com.Aqeel.frontend;

public class Enemy extends GameObject {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected long scoreValue;

    public Enemy(String name, int hp) {
        super(200, 380, 32, 24, 0, Color.PINK)
        this.name = name;
        this.hp = hp;
        this.scoreValue = 100;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue) {
        super(x, y, width, height , 0, Color.RED)
        this.name = name;
        this.hp = hp;
        this.scoreValue = 100;
    }
    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        hp -= damage;

        // 2. HP must not go below 0.
        if (this.hp < 0) {
            this.hp = 0;
        }

        // 3. Display the current HP in the format: [EnemyName] took [damage] damage! HP: [currentHP]/[maxHP]
        System.out.println(this.name + "took" + damage + "damage! Remaining HP: " + hp + "/" + maxHp);

        // 4. If HP reaches 0, display that the Enemy has been defeated, in the format: [EnemyName] was defeated!
        if (this.hp == 0) {
            System.out.println(this.name + "was defeated!");
        }
    }
    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.println(this.name + "unleashes bullet barrage on " + player.name + "!");

        // 2. Call the Player's takeDamage() method using the given damage.
        player.takeDamage(damage);
    }
    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        return this.hp > 0;
    }



}
