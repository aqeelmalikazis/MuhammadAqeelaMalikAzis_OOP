package com.Aqeel.frontend;

import com.Aqeel.frontend.commands.InputHandler;
import com.Aqeel.frontend.ui.GameHUD;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.Aqeel.frontend.objects.GameObject;
import com.Aqeel.frontend.objects.Player;
import com.Aqeel.frontend.objects.enemies.Boss;
import com.Aqeel.frontend.objects.enemies.Fairy;
import com.Aqeel.frontend.objects.items.Item;
import com.Aqeel.frontend.objects.items.ItemType;
import com.Aqeel.frontend.systems.AssetManager;
import com.Aqeel.frontend.systems.BulletManager;
import com.Aqeel.frontend.systems.CollisionReferee;
import com.Aqeel.frontend.systems.EntityFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;

    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;
    private float enemyShootTimer = 0f;

    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fairy = new ArrayList<>();
        entities = new ArrayList<>();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        // TODO: Instantiate new objects for inputHandler and gameHUD
        inputHandler = new InputHandler();
        gameHUD = new GameHUD();

        // 1. Centralized Asset Registration (AssetManager Singleton + Flyweight)
        AssetManager.getInstance().init();

        // 2. Instantiate entities via Factory Pattern (EntityFactory)
        // TODO: Adjust entity instantiations to the new playfield coordinates:
        player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        // TODO: Register gameHUD as an Observer on the player object!
        // Hint: player.registerObserver(gameHUD);
        player.registerObserver(gameHUD);

        entities.add(player);
        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        // TODO: Register the entities list to inputHandler for bullet conversion during a Bomb!
        // Hint: inputHandler.setEntities(entities);
        inputHandler.setEntities(entities);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 1. Check Player Bullet shooting input (Key Z -> BulletManager Object Pool)
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            player.shootBullet(bulletManager);
        }

        // 2. Periodic Enemy Action Scheduler
        updateEnemyScheduler(delta);

        // 3. Generic update & safe removal of standard entities
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 4. Object Pool Update (BulletManager)
        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 5. Mediator Pattern Collision Resolution (CollisionReferee - Core vs Graze detection)
        collisionReferee.resolveCollisions(player, entities, bulletManager);

        // 6. Clear screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 7. Render active entity sprites and pooled bullets with SpriteBatch
        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }
        bulletManager.render(batch);
        batch.end();
    }

    // Periodic Enemy Action Scheduler
    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            boss.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }

    private void restartGame() {
        System.out.println("[Main] Restarting game...");
        entities.clear();
        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();
        entities.add(player);
        player.resetGame();

        fairy.clear();
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
    }


    // Generic Instance Method with Bounded Type Parameter <T extends GameObject>
    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                System.out.println("Removed via Generic Iterator: " + entity.getClass().getSimpleName());
                iterator.remove(); // Safe removal using Iterator!
            }
        }
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        AssetManager.getInstance().dispose();
    }
}
