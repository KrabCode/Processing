package Randomness;

import processing.core.PApplet;

public class Perlin {
    PApplet p;
    public Perlin(PApplet p)
    {
        this.p = p;
    }

    float x,y = 0;


    public void draw()
    {
        y += 0.001;
        float f = p.noise(y);

        p.println(f);
        p.noStroke();
        p.fill(255);
        p.rect(x += 0.2, p.map(f, 0,1,0, p.height), 20,20);
    }
}
