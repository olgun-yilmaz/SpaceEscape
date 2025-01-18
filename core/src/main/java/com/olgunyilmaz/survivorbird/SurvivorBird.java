package com.olgunyilmaz.survivorbird;

import static java.lang.Math.abs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SurvivorBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture ufo;
    Texture monster1;
    Texture monster2;
    Texture monster3;
    int numEnemies = 4;
    float [] monster1offSet = new float[numEnemies];
    float [] monster2offSet = new float[numEnemies];
    float [] monster3offSet = new float[numEnemies];



    float ufoX = 0;
    float monsterX = 0;
    float ufoY = 0;
    float velocity = 0;


    double gravity = 0.1;
    boolean isStarted = false;
    float [] enemyX= new float[numEnemies];
    float distance;
    float enemyVelocity = 2;
    Random random;

    Circle ufoCircle;
    ShapeRenderer shapeRenderer;
    Circle [] monsterCircles1;
    Circle [] monsterCircles2;
    Circle [] monsterCircles3;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        ufo = new Texture("ufo.png");
        monster1 = new Texture("monster.png");
        monster2 = new Texture("monster.png");
        monster3 = new Texture("monster.png");

        distance = Gdx.graphics.getWidth()/2;
        random = new Random();

        ufoX = Gdx.graphics.getWidth()/5;
        ufoY = Gdx.graphics.getHeight()/2;

        ufoCircle = new Circle();
        monsterCircles1 = new Circle[numEnemies];
        monsterCircles2 = new Circle[numEnemies];
        monsterCircles3 = new Circle[numEnemies];

        shapeRenderer = new ShapeRenderer();


        for (int i = 0; i < numEnemies; i++){
            monster1offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
            monster2offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
            monster3offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();

            enemyX[i] = Gdx.graphics.getWidth() - monster1.getWidth() / 2 + i * distance;

            monsterCircles1[i] = new Circle();
            monsterCircles2[i] = new Circle();
            monsterCircles3[i] = new Circle();
        }
    }

    @Override
    public void render() {
        batch.begin();

        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        if (!isStarted){
            monsterX = (int) (Gdx.graphics.getWidth() * 0.75);
            if (Gdx.input.justTouched()){
                isStarted = true;
                ufoY += velocity;
            }

        } else{
            if (ufoX < Gdx.graphics.getWidth()){
                ufoX += Gdx.graphics.getWidth()/200;
                monsterX -= Gdx.graphics.getWidth()/200;
            }else{
                ufoX = 0;
            }

            if (Gdx.input.justTouched()){
                velocity -= Gdx.graphics.getHeight()/500;
            }

            for (int i = 0; i < numEnemies; i++){

                if (enemyX[i] < - Gdx.graphics.getWidth()/10){
                    enemyX[i] += numEnemies * distance;

                    monster1offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
                    monster2offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
                    monster3offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();


                }else{
                    enemyX[i] -= enemyVelocity;
                }

                batch.draw(monster1,enemyX[i],monster1offSet[i],
                    monster1.getWidth()/10,monster1.getHeight()/10);
                batch.draw(monster2,enemyX[i],monster2offSet[i],
                    monster2.getWidth()/10,monster2.getHeight()/10);
                batch.draw(monster3,enemyX[i],monster3offSet[i],
                    monster3.getWidth()/10,monster3.getHeight()/10);

                monsterCircles1[i] = new Circle(enemyX[i] + monster1.getWidth()/20,
                    monster1offSet[i] + monster1.getWidth()/20, monster1.getWidth()/20);
                monsterCircles2[i] = new Circle(enemyX[i] + monster2.getWidth()/20,
                    monster2offSet[i] + monster2.getWidth()/20, monster2.getWidth()/20);
                monsterCircles3[i] = new Circle(enemyX[i] + monster3.getWidth()/20,
                    monster3offSet[i] + monster3.getWidth()/20, monster3.getWidth()/20);
            }

            if (monsterX < 0){
                monsterX = Gdx.graphics.getWidth();
            }


            if (ufoY > 0 || velocity < 0){
                velocity += gravity;
                ufoY -= velocity;
            }

        }
        batch.draw(ufo,ufoX,ufoY, ufo.getWidth()/10,ufo.getHeight()/10);

        batch.end();

        ufoCircle.set(ufoX + ufo.getWidth()/20,ufoY + ufo.getHeight()/20,ufo.getWidth()/20);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(ufoCircle.x,ufoCircle.y,ufoCircle.radius);


        for (int i = 0; i < numEnemies; i++){
            shapeRenderer.circle(enemyX[i],monster1offSet[i], monster1.getWidth()/10,monster1.getHeight()/10);
            shapeRenderer.circle(enemyX[i],monster2offSet[i], monster2.getWidth()/10,monster2.getHeight()/10);
            shapeRenderer.circle(enemyX[i],monster3offSet[i], monster3.getWidth()/10,monster3.getHeight()/10);

        }
        shapeRenderer.end();

    }

    @Override
    public void dispose() {
    }
}
