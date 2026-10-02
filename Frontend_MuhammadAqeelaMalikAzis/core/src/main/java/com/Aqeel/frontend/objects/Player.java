package com.Aqeel.frontend.objects;

import com.Aqeel.frontend.objects.bullets.Bullet;
import com.Aqeel.frontend.objects.enemies.Enemy;
import com.Aqeel.frontend.objects.items.Item;
import com.Aqeel.frontend.objects.items.ItemType;
import com.badlogic.gdx.graphics.Color;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score = 0;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }

    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        setHp(getHp() - damage);

        if (getHp() > 0) {
            System.out.println(getName() + " took " + damage + " damage! Remaining HP: " + getHp());
        } else {
            System.out.println(getName() + " took " + damage + " damage! Remaining HP: " + getHp());
            System.out.println(getName() + " has been defeated!");
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!");
        target.takeDamage(damage);
    }

    public Bullet shootBullet() {
        int damage = 10 + power;
        System.out.println(name + " shoots bullet dealing " + damage + " DMG!");
        // TODO: return a new Bullet positioned at the top-center of the Player
        // (x + width/2 - 4, y + height), with BulletType.AMULET as its type,
        // and the damage calculated above
        float bulletStartX = this.x + (this.width / 2) - 4;
        float bulletStartY = this.y + this.height;

        return new Bullet(bulletStartX, bulletStartY, BulletType.AMULET, damage);
    }


    public boolean isAlive() {
        return getHp() > 0;
    }

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp); // Clamp validation at 0
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpellCards() {
        return spellCards;
    }

    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
    }

    public long getScore() {
        return score;
    } // Score only gets a getter
    @Override
    public void update(float delta) {
        if (com.badlogic.gdx.Gdx.input != null) {
            // TODO: Check W / UP input   → y += speed * delta
            if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W) || com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
                setY(getY() + getSpeed() * delta);
            }
            // TODO: Check S / DOWN input → y -= speed * delta
            if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S) || com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
                setY(getY() - getSpeed() * delta);
            }
            // TODO: Check A / LEFT input → x -= speed * delta
            if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A) || com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
                setX(getX() - getSpeed() * delta);
            }
            // TODO: Check D / RIGHT input → x += speed * delta
            if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D) || com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
                setX(getX() + getSpeed() * delta);
            }
        }
    }
    public void collectItem(Item item) {
        ItemType type = item.getItemTypeEnum();
        if (item.isDestroyed()) return;
        if (type != null) {
            switch (type) {
                case POWER -> {
                    // 1. Increase power by type.getPowerBonus() via this.power
                    this.power += type.getPowerBonus();
                    // 2. Add score by item.getScoreValue() via addScore() (addScore() already automatically prints "gained X pts!")
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                    System.out.println(getName() + " collected POWER item! Power increased to " + this.power);
                }
                case POINT -> {
                    // 1. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 2. Print: [name] collected POINT item!
                    System.out.println(getName() + " collected POINT item!");
                }
                case BOMB -> {
                    // 1. Increase spellCards by 1
                    this.spellCards += 1;
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                    System.out.println(getName() + " collected BOMB item! SpellCards: " + this.spellCards);
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    setHp(getHp() + 20);
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                    System.out.println(getName() + " collected LIFE item! HP: " + getHp());
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(getName() + " collected " + item.getItemType() + "!");
        }
        item.destroy();
    }
    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is an Item
        if (other instanceof com.Aqeel.frontend.objects.items.Item) {
            // TODO: Print "Player touches items" then call collectItem((Item) other)
            System.out.println("Player touches items");
            collectItem((com.Aqeel.frontend.objects.items.Item) other);
        }
    }
}
