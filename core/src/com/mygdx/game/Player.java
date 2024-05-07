package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

    Vector2 velocity = new Vector2();

    public float speed = 60 * 2;

    private TiledMapTileLayer collisionLayer;

    private String blockedKey = "blocked";


    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        setSize(getWidth(), getHeight());
    }


    public void draw(Batch spriteBatch)
    {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public void update(float delta)
    {
        float  prevX = getX();
        float  prevY = getY();

        setX(getX() + velocity.x * delta);

        boolean collideX = false, collideY = false;
        if(velocity.x < 0)
        {
            collideX = collidesLeft();
        }else if(velocity.x > 0)
        {
            collideX = collidesRight();
        }

        if(collideX)
        {
            setX(prevX);
            velocity.x = 0;

        }
         setY(getY() +  velocity.y * delta);

        if(velocity.y < 0)
        {
            collideY = collidesBottom();
        }
        else if(velocity.y > 0)
        {
            collideY = collidesTop();
        }


        if(collideY)
        {
            setY(prevY);
            velocity.y = 0;
        }


    }

    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
    }

    public boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += (float)collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += (float) collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += (float) collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;
    }

    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += (float) collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {


        switch(keycode){
            case Input.Keys.W:
                velocity.y +=speed;
                break;
            case Input.Keys.A:
                velocity.x-=speed;
                break;
            case Input.Keys.D:
                velocity.x += speed;
                break;
            case Input.Keys.S:
                velocity.y -=speed;
                break;

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        switch(keycode){
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.y = 0;
                break;
            case Input.Keys.D:
            case Input.Keys.A:
                velocity.x = 0;
                break;

        }
        return true;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
