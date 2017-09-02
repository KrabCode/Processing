package Ecosystem;

import processing.core.PApplet;
import processing.core.PVector;

public class Shark extends SimpleBall {


    public Shark(PApplet p, PVector pos) {
        super(p, pos);
    }


    @Override
    public void draw() {
        super.draw();

        PVector mouse = new PVector(p.mouseX, p.mouseY);
        PVector accChange = getVectorFromAtoB(pos,mouse);

        float mag = accChange.mag();
        accChange = accChange.normalize();
        accChange = accChange.mult(0.001f);

        acc.add(accChange);
        spd.add(acc);
        spd.limit(3);
        pos.add(spd);

        p.noStroke();
        p.fill(150,20);
        p.ellipse(pos.x, pos.y, 20, 20);
    }
}

