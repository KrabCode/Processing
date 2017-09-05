import Fractals.Mandelbrot;
import com.hamoid.VideoExport;
import processing.core.PApplet;
import processing.core.PImage;


public class MainApp extends PApplet{

    public static void main(String[] args)
    {
        PApplet.main("MainApp", args);
    }
    public void settings()
    {
        //fullScreen();
        size(400,400);
    }



    VideoExport videoExport;

    private boolean breatheIn = true;

    private float minRadius = 10;
    private float maxRadiusEdgeMargin = 40;
    private float framesPerBreath = 30*8;

    private float radius;
    private float maxRadius;

    public void setup()
    {
        //fullScreen();
        radius = minRadius;
        if(width < height){
            maxRadius = width - maxRadiusEdgeMargin;
        }else{
            maxRadius = height - maxRadiusEdgeMargin;
        }
        background(0);
        noStroke();
        ellipseMode(CENTER);
    }


    public void draw()
    {
        if(breatheIn){
            radius += (maxRadius-minRadius) / framesPerBreath;
        }else{
            radius -= (maxRadius-minRadius) / framesPerBreath;
        }
        if(radius > maxRadius || radius < minRadius){
            breatheIn = !breatheIn;
        }

        fill(0,30);
        rect(0,0,width, height);
        fill(255, 255, 255, 30);
        ellipse(width/2, height/2, radius, radius);
        println(frameCount + ":" + breatheIn);
    }






}