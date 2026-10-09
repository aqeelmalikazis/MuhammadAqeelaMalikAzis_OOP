package com.Aqeel.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                // TODO: Call each entity's .render() method with the SpriteBatch as its argument.
                entity.render(batch);
            }
        }
        batch.end();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    public void create() {
        // TODO 1:
        // When initializing the renderer, create a SpriteBatch and store it in batch.
        // LibGDX hint: new SpriteBatch().
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();


        // TODO 2:
        // Initialize the fairy and entities lists as empty ArrayLists.
        fairy = new ArrayList<>();

        // TODO 3:
        // Before creating entities, get the AssetManager instance and call init().
        entities = new ArrayList<>();

        AssetManager.getInstance().init();

        // TODO 4:
        // Update how all entities are created! Follow the table and create Player, Fairy, Boss, and Item
        // using the appropriate EntityFactory methods.
        // Add both Fairies to the fairy list using add(...).
        player = new Player(280, 40, "Reimu Hakurei", 100, 15, 3);

        fairy = new Fairy(150, 380, "Stage 1 Fairy", 20);

        boss = new Boss(380, 400, "Cirno (Stage 2 Boss)" 150);

        powerItem = new Item(200, 450, 16, 16, 8f, ItemType.POWER, 500L);
        pointItem = new Item(320, 400, 12, 12. 120f, ItemType.POINT);

        // TODO 5:
        // Add all the objects you have just created to entities.
        entities.add(Player);
        entities.add(fairy);
    }

}
