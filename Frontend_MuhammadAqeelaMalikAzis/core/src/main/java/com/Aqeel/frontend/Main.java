package com.Aqeel.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import com.Aqeel.frontend.objects.GameObject;
import com.Aqeel.frontend.objects.Player;
import com.Aqeel.frontend.objects.enemies.Boss;
import com.Aqeel.frontend.objects.enemies.Fairy;
import com.Aqeel.frontend.objects.items.Item;
import com.Aqeel.frontend.objects.items.ItemType;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input;
import java.util.Iterator;

import static com.badlogic.gdx.Input.Keys.T;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private List<GameObject> entities;
    private Player player;

    // TODO 1: Declare fields for Player, Fairy, Boss, Items, and List<GameObject>

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();

        // TODO 2: Instantiate Player (Red square) at (280, 40)
        player = new Player("Reimu Hakurei", 100, 15, 3);
        player.setSpeed(200f);

        // TODO 3: Instantiate Fairy (Pink square) at (150, 380)
        Fairy fairy = new Fairy("Stage 1 Fairy", 20);

        // TODO 4: Instantiate Boss (Blue square) at (380, 400)
        Boss cirno = new Boss("Cirno", 150);

        // TODO 5: Instantiate Items (White squares) with downward speeds
        Item powerItem = new Item(200, 450, 16, 16, 80f, ItemType.POWER, 500L);
        Item pointItem = new Item(320, 480, 12, 12, 120f, ItemType.POINT, 1000L);

        // TODO 6: Add all entities into the entities list polymorphically
        entities.add(player);
        entities.add(fairy);
        entities.add(cirno);
        entities.add(powerItem);
        entities.add(pointItem);
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        // 1. Polymorphic Update Loop
        for (GameObject obj : entities) {
            obj.update(delta);
        }

        // AABB Collision detection between every unique entity pair
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                // TODO: Check whether getCoreHitbox() of a and b overlap (use the .overlaps() method of Rectangle)
                if (a.getCoreHitbox().overlaps(b.getCoreHitbox())) {
                    // TODO: Call a.onCollision(b) and b.onCollision(a)
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }
        // ---------------------------------------------------------

        // 2. Clear Screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 3. Polymorphic Render Loop
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : entities) {
            obj.render(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // TODO 1: If the Z key was just pressed, add a new bullet from player.shootBullet()
        // to the entities list.
        // Clue: Gdx.input.isKeyJustPressed()
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            entities.add(player.shootBullet());
        }

        // TODO 2: Call updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
        // to update and clean up destroyed/off-screen entities.
        updateAndClean(
            entities;
            delta;
            Gdx.graphics.getWidth();
            Gdx.graphics.getHeight();
        );

        // 3. Collision detection between entities (skip entities that are already destroyed)
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                if (!a.isDestroyed() && !b.isDestroyed()) {
                    if (a.getCoreHitbox().overlaps(b.getCoreHitbox())) {
                        a.onCollision(b);
                        b.onCollision(a);
                    }
                }
            }
        }

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject entity : entities) {
            // TODO 3: Use an if statement to check whether the entity has not been destroyed (!entity.isDestroyed()).
            // If so, call entity.render(shapeRenderer);
            entity.render(shapeRenderer);
        }
        shapeRenderer.end();
    }

}
