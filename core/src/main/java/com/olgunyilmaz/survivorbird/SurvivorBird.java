package com.olgunyilmaz.survivorbird;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SurvivorBird extends ApplicationAdapter {
    DataSaver dataSaver;
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

    int score = 0;
    int highScore;
    int scoredEnemy = 0;


    double gravity = 0.1;
    int gameState = 0; // 0 : not started, 1 : started, 2 : finished
    float [] enemyX= new float[numEnemies];
    float distance;
    float enemyVelocity = 2;
    Random random;

    Circle ufoCircle;

    Circle [] monsterCircles1;
    Circle [] monsterCircles2;
    Circle [] monsterCircles3;

    BitmapFont font;
    BitmapFont gameOverFont;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        ufo = new Texture("ufo.png");
        monster1 = new Texture("monster.png");
        monster2 = new Texture("monster.png");
        monster3 = new Texture("monster.png");

        dataSaver = new DataSaver();
        highScore = dataSaver.fetchData();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(3);

        gameOverFont = new BitmapFont();
        gameOverFont.setColor(Color.WHITE);
        gameOverFont.getData().setScale(6);

        distance = Gdx.graphics.getWidth()/3;
        random = new Random();

        ufoX = Gdx.graphics.getWidth()/5;
        ufoY = Gdx.graphics.getHeight()/2;

        ufoCircle = new Circle();
        monsterCircles1 = new Circle[numEnemies];
        monsterCircles2 = new Circle[numEnemies];
        monsterCircles3 = new Circle[numEnemies];


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

        if (gameState == 0){// not started
            monsterX = (int) (Gdx.graphics.getWidth() * 0.75);
            if (Gdx.input.justTouched()){
                gameState = 1;
                ufoY += velocity;
            }

        } else if(gameState == 1){ // started

            if (enemyX[scoredEnemy] < ufoX){
                score ++;
                if (score > highScore){
                    updateHighScore();
                }
                if (scoredEnemy < numEnemies-1){
                    scoredEnemy = (scoredEnemy+1) % (numEnemies-1);
                }
            }

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


            if (ufoY > 0){
                velocity += gravity;
                ufoY -= velocity;
            } else if (ufoY < 0) {
                gameState = 2;
            }

        }else if(gameState == 2){ // finished
            gameOverFont.draw(batch,"GAME OVER",(int)(Gdx.graphics.getWidth()/2.7),Gdx.graphics.getHeight()/2);
            if (Gdx.input.justTouched()){
                gameState = 1; // restart
                ufoY = Gdx.graphics.getHeight()/2;

                for (int i = 0; i < numEnemies; i++){
                    monster1offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
                    monster2offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();
                    monster3offSet[i] = (random.nextFloat()) * Gdx.graphics.getHeight();

                    enemyX[i] = Gdx.graphics.getWidth() - monster1.getWidth() / 2 + i * distance;

                    monsterCircles1[i] = new Circle();
                    monsterCircles2[i] = new Circle();
                    monsterCircles3[i] = new Circle();
                }

                velocity = 0;
                score = 0;
                scoredEnemy = 0;
            }


        }
        batch.draw(ufo,ufoX,ufoY, ufo.getWidth()/10,ufo.getHeight()/10);

        font.draw(batch, "Score : "+score,(int)(Gdx.graphics.getWidth()*.05),100);
        font.draw(batch,"High Score : "+highScore,(int)(Gdx.graphics.getWidth()*.2), 100);

        batch.end();



        ufoCircle.set(ufoX + ufo.getWidth()/20,ufoY + ufo.getHeight()/20,ufo.getWidth()/20);


        for (int i = 0; i < numEnemies; i++){
            if (Intersector.overlaps(ufoCircle,monsterCircles1[i]) ||
                Intersector.overlaps(ufoCircle,monsterCircles2[i]) ||
                Intersector.overlaps(ufoCircle,monsterCircles3[i]) ){
                gameState = 2;
            }
        }

    }

    private void updateHighScore(){
        dataSaver.saveData(score);
        highScore = score;
    }

    @Override
    public void dispose() {
    }
}
