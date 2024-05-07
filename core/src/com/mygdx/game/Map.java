package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.Spliterator;

public class Map implements Screen {
    private TiledMap tiledMap;

    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private OrthographicCamera camera;

    private Player player;

    @Override
    public void show()
    {
        TmxMapLoader loader = new TmxMapLoader();
        tiledMap = loader.load("funmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new OrthographicCamera();
        player = new Player(new Sprite(new Texture("Character.png")), (TiledMapTileLayer)  tiledMap.getLayers().get(1));

        camera.zoom = .8f;
        Gdx.input.setInputProcessor(player);

    }

    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.position.set(player.getX() +  player.getWidth() / 2, player.getY() + player.getHeight()/2,0);
            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            tiledMapRenderer.getBatch().begin();
            player.draw(tiledMapRenderer.getBatch());
            tiledMapRenderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
