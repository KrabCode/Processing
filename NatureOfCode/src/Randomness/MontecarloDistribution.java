package Randomness;

import processing.core.PApplet;

public class MontecarloDistribution {

    PApplet p;


    public MontecarloDistribution(PApplet p)
    {
        this.p = p;
    }

    public void draw()
    {
        p.noStroke();
        p.fill(255, 5);
        p.rect(montecarlo()*p.width, p.height/2, 20, 20);
    }

    //0.83 has 83% chance, 0.1 has 10% chance
    float montecarlo() {
        while (true) {
            float r1 = p.random(1); //Pick a random value.
            float probability = r1; //Assign a probability.
            float r2 = p.random(1); //Pick a second random value.
            if (r2 < probability) { //Does it qualify? If so, we’re done!
                return r1;
            }
        }
    }

}
