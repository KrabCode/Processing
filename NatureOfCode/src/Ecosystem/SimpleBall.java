package Ecosystem;

import processing.core.PApplet;
import processing.core.PVector;

public class SimpleBall {

    PApplet p;
    public SimpleBall(PApplet p, PVector pos)
    {
        this.p = p;
        this.pos = pos;
        spd = new PVector();
        acc = new PVector();
    }


    PVector pos;
    PVector spd;
    PVector acc;

    public void draw()
    {

    }

    public void applyForce(PVector force)
    {
        acc.add(force);

    }




    protected PVector getVectorFromAtoB(PVector A, PVector B)
    {
        return new PVector(B.x - A.x,  B.y- A.y);
    }
}
