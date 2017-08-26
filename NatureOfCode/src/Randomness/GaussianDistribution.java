package Randomness;

import processing.core.PApplet;

import java.util.Random;

public class GaussianDistribution {

    PApplet p;


    Random r ;
    float sd;
    float mean;

    public GaussianDistribution(PApplet p)
    {
        this.p = p;
        r = new Random();
        sd = 60;
        mean = p.width/2;
    }

    public void draw()
    {
        float todaysGauss = (float) r.nextGaussian();
        todaysGauss *= sd;
        todaysGauss += mean;

        p.fill(255,5);
        p.noStroke();
        p.rect(todaysGauss, p.height/2, 20,20);
    }
}
