package com.olgunyilmaz.survivorbird;

import static java.lang.Math.abs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SurvivorBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture ufo;
    Texture enemy1;
    Texture enemy2;
    Texture enemy3;

    float ufoX = 0;
    float ufoY = 0;
    float velocity = 0;
    double gravity = 0.1;
    boolean isStarted = false;




    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        ufo = new Texture("ufo.png");
        enemy1 = new Texture("enemy.png");
        enemy2 = new Texture("enemy.png");
        enemy3 = new Texture("enemy.png");

        ufoX = Gdx.graphics.getWidth()/5;
        ufoY = Gdx.graphics.getHeight()/2;
    }

    @Override
    public void render() {
        batch.begin();

        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        if (!isStarted){
            if (Gdx.input.justTouched()){
                isStarted = true;
                ufoY += velocity;
            }

        } else{

            if (ufoY > 0 || velocity < 0){
                velocity += gravity;
                ufoY -= velocity;
            }

            if (Gdx.input.justTouched()){
                velocity -= Gdx.graphics.getHeight()/500;
            }

        }
        batch.draw(ufo,ufoX,ufoY, ufo.getWidth()/10,ufo.getHeight()/10);

        batch.end();
    }

    @Override
    public void dispose() {
    }
}
