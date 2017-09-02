package Vectors;

import processing.core.PApplet;
import processing.core.PVector;

public class BallManager {

    Ball[] balls;
    int ballCount;
    PApplet p;

    /**
     * Just a reference to the main surface needed because of the IDEA ide.
     * @param p
     */
    public BallManager(PApplet p)
    {
        this.p = p;
    }

    public void setup()
    {
        ballCount = 6;
        balls = new Ball[ballCount];
        for(int i = 0; i < ballCount; i++)
        {
            Ball b = new Ball(p);
            b.setup(new PVector(3,3), i);
            balls[i] = b;
        }
    }

    public void draw()
    {
        for(int i = 0; i < ballCount; i++)
        {
            balls[i].update();
            balls[i].draw();
        }
    }
}
