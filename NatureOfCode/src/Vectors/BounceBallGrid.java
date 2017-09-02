package Vectors;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class BounceBallGrid {

    PApplet p;
    List<BounceBall> balls;

    public BounceBallGrid(PApplet p, float padding, float size, float leash,
                          PVector initialSpeed,float speedLimit, float accModifier, float xoff, float yoff){
        this.p = p;
        balls = new ArrayList<>();


        for(int y = -5; y < p.height/padding+5; y++)
        {
            for(int x = -5; x < p.width/padding+5; x++){
                BounceBall ball = new BounceBall(p,
                        new PVector(
                                x*padding + xoff * x,
                                y*padding + yoff * y),
                        initialSpeed,
                        speedLimit,
                        accModifier,
                        size,
                        leash
                );
                balls.add(ball);
            }
        }
    }


    public void draw()
    {
        p.colorMode(PConstants.RGB, 100, 100, 100);
        int i = 0;
        for (BounceBall ball : balls) {
            i+=1;
            p.noStroke();
            p.fill( 50 + p.sin(i)*50, p.sin(i)*50,50 + p.cos(i)*50, 50);
            ball.draw();
        }
    }


}
