package Vectors;

import processing.core.PApplet;
import processing.core.PVector;

public class Ball {
    PApplet p;

    private float mass;
    private float radius;
    private PVector location;
    private PVector velocity = new PVector();
    private PVector acceleration = new PVector();
    private PVector wind;
    private PVector gravity;

    /**
     * Just a reference to the main surface needed because of the IDEA ide.
     * @param p
     */
    public Ball(PApplet p)
    {
        this.p = p;
    }

    public void setup(PVector location, float mass)
    {
        this.mass = mass;
        this.radius = mass*8;
        this.location = new PVector(location.x + radius, location.y);
        wind = new PVector(+0.01f, 0);
        gravity = new PVector(0,0.05f);
    }

    public void update()
    {
        checkBoundary();

        addForce(PVector.mult(gravity, mass));
        addForce(wind);

        velocity.add(acceleration);

        location.add(velocity);

        acceleration.mult(0);
    }

    private void checkBoundary() {
        //if out of right wall
        if(location.x + radius > p.width){
            velocity.x *=  -0.9f;
            location.x = p.width - radius;
        }
        //if out of left wall
        if(location.x - radius < 0){
            velocity.x *=  -0.9f;
            location.x = 0 + radius;
        }
        //if out of bottom wall
        if(location.y + radius > p.height){
            velocity.y *=  -0.9f;
            location.y = p.height - radius;
        }
    }

    public void draw()
    {
        p.ellipse(location.x, location.y, radius*2, radius*2);
    }

    public void addForce(PVector force)
    {
        acceleration.add(PVector.div(force, mass));
    }
}
