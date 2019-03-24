package main.java;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import java.util.Random;
import java.lang.Math;

public class FishGame extends PApplet{
    public static void main(String[] args) {PApplet.main(FishGame.class.getCanonicalName());}

    public void settings() {
        size(800,600);
    }

    Random rand = new Random();
    int userX;
    int userY;
    int bubbleX;
    int bubbleY;
    int trashX = 800;
    int trashY = rand.nextInt(450) + 1;
    boolean right = true;
    int loops = 0;
    boolean spawnBubble = false;
    double distance;
    int trashSpeed = 5;
    int numBombs = 0;


    public void draw() {
        background(0,(loops/2),255-(loops/2));
        bubbleAnimation();
        user(userX,userY);
//        keyPressed();
        trash(trashX,trashY);

        int userCenterX = userX + 50;
        int userCenterY = userY + 50;
        int trashCenterX = trashX + 75;
        int trashCenterY = trashY + 75;

        distance = Math.sqrt((Math.pow((userCenterX - trashCenterX),2))+((Math.pow((userCenterY - trashCenterY),2))));

        if (distance < 100) {
            collision();
        }


        trashX -= trashSpeed;

        loops++;
    }


    public void user(int x, int y) {
        PImage user = loadImage("/home/brad/Code/FishGame/fish-filled.png");

        if (!right) {
            pushMatrix();
            translate(user.width,0);
            scale(-1, 1);
            image(user, -x, y, 100, 100);
            popMatrix();
        }else if(right) {
            image(user,x,y,100,100);
        }

    }

    public void bubble(int x, int y) {
        PImage bubbleUpload = loadImage("/home/brad/Code/FishGame/bubble.png");
        image(bubbleUpload,x,y,20,20);
    }
    public void bubbleAnimation() {
        if ((loops % 10) == 0) {
            bubbleX = userX;
            bubbleY = userY + 50;
            bubble(bubbleX,bubbleY);
            spawnBubble = true;
        }
        if (spawnBubble) {
            bubbleX -=5;
            bubble(bubbleX,bubbleY);
            if (bubbleX < -100) {
                spawnBubble = false;
            }
        }
    }
    public void trash(int x, int y) {
        PImage bottleUpload = loadImage("bottle.png");
        image(bottleUpload,x,y,150,150);

        if (x < -150) {
            trashX = 1000;
            trashY = rand.nextInt(450) + 1;
            numBombs += 1;
            if (numBombs % 5 == 0) {
                trashSpeed += 2;
            }
        }
    }
    public void collision() {
        PImage explosion = loadImage("algae.png");
        image(explosion,trashX-75,trashY-75,325,250);
        int score = millis() / 1000;
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
            fill(0);
//            rect(250,200,300,250);
            rect(250,200,300,250);
        textSize(40);
        fill(255,0,0);
        text("GAME OVER",(width/2)-125,height/2);
        textSize(30);
        fill(0,255,0);
        text(score + " Seconds",(width/2)-75,(height/2) + 100);
        noLoop();
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == UP) {
                userY -= 16;
            }
            if (keyCode == DOWN) {
                userY += 16;
            }
            if (keyCode == RIGHT) {
                userX += 16;
                right = true;
            }
            if (keyCode == LEFT) {
                userX -= 16;
                right = false;
            }
        }
        if (userX<0) {
            userX = 0;
        }
        if (userX>700) {
            userX = 700;
        }
        if (userY < 0) {
            userY = 0;
        }
        if (userY > 500) {
            userY = 500;
        }
    }

}
