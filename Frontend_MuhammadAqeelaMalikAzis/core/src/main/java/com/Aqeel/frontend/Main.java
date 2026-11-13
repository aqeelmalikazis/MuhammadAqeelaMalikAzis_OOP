package com.Aqeel.frontend;

import com.Aqeel.frontend.commands.Command;
import com.Aqeel.frontend.commands.InputHandler;
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
import com.Aqeel.frontend.ui.GameHUD;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

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
    private InputHandler inputHandler;
    private GameHUD gameHUD;
    private float enemyShootTimer = 0f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fairy = new ArrayList<>();
        entities = new ArrayList<>();
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        inputHandler = new InputHandler();
        gameHUD = new GameHUD();

        AssetManager.getInstance().init();

        player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        player.registerObserver(gameHUD);

        entities.add(player);
        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        inputHandler.setEntities(entities);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // Restart only when the player is in the Game Over state.
        if (Gdx.input != null && Gdx.input.isKeyJustPressed(Input.Keys.R)
                && player != null && player.isGameOver()) {
            restartGame();
        }

        // Process input through the Command Pattern. Disable gameplay commands during Game Over.
        if (player != null && !player.isGameOver()) {
            List<Command> commands = inputHandler.handleInput(delta);
            for (Command command : commands) {
                command.execute(player, bulletManager);
            }
        }

        updateEnemyScheduler(delta);
        updateAndClean(entities, delta, 416, 560);
        bulletManager.update(delta, 416, 560);
        collisionReferee.resolveCollisions(player, entities, bulletManager);

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }
        bulletManager.render(batch);
        if (gameHUD != null) {
            gameHUD.render(batch);
        }
        batch.end();

        if (gameHUD != null) {
            gameHUD.renderBackground();
            if (player != null) {
                player.renderFocusIndicator(gameHUD.getShapeRenderer());
            }
        }
    }

    private void updateEnemyScheduler(float delta) {
        if (boss == null || boss.isDestroyed()) return;

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
        player.resetGame();
        entities.add(player);

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
        enemyShootTimer = 0f;
    }

    public <T extends GameObject> void updateAndClean(List<T> list, float delta,
                                                      float screenWidth, float screenHeight) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                iterator.remove();
            }
        }
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (gameHUD != null) {
            gameHUD.dispose();
        }
        AssetManager.getInstance().dispose();
    }
}
