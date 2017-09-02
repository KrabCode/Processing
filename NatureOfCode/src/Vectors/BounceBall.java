package Vectors;

import processing.core.PApplet;
import processing.core.PVector;

public class BounceBall {

    private PApplet p;

    private PVector pos;
    private PVector spd;
    private PVector acc;

    private float speedLimit;
    private float accMod;

    private float r;

    private PVector center;
    private float boundary;

    public BounceBall(PApplet p, PVector center, PVector initialSpeed,
                      float speedLimit, float accModifier, float r, float boundary)
    {
        this.p = p;
        this.center  = center;
        this.speedLimit = speedLimit;
        this.spd = initialSpeed;
        this.acc = new PVector(0,0);
        this.pos = new PVector(center.x, center.y);
        this.accMod = accModifier;
        this.boundary = boundary;
        this.r = r;

        p.ellipseMode(p.CENTER);
    }

    public void draw()
    {
        spd.add(acc);
        spd.limit(speedLimit);
        pos.add(spd);
        collisionCheck();
        p.ellipse(pos.x, pos.y, r, r);
    }

    public void collisionCheck()
    {
        if(PVector.dist(center, pos) > boundary)
        {
            p.println("center " + center + "pos " +  pos + " crossed boundary " + boundary);

            float targetX = center.x;
            float dx = targetX - pos.x;
            float accChangeX = dx * accMod;

            float targetY = center.y;
            float dy = targetY - pos.y;
            float accChangeY = dy * accMod;

            acc.add(new PVector(accChangeX, accChangeY));
        }
    }

}
