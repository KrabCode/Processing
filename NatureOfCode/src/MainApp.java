import Ecosystem.Fly;
import Ecosystem.Shark;
import Ecosystem.SimpleBall;
import Vectors.BallManager;
import com.hamoid.VideoExport;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;


public class MainApp extends PApplet{

    public static void main(String[] args)
    {
        PApplet.main("MainApp", args);
    }
    public void settings()
    {
        fullScreen();
    }
    BallManager b;
    VideoExport videoExport;

    public void setup(){

        b = new BallManager(this);
        b.setup();
        drawBackground(false);
        //videoExport = new VideoExport(this);
        //videoExport.startMovie();
    }

    public void draw()
    {
        noStroke();
        fill(255,60);
        b.draw();
        drawBackground(true);
        //videoExport.saveFrame();
    }

    public void drawBackground(boolean trailEffect)
    {
        if(trailEffect)    fill(0, 10);
        else                fill(0);
        rect(-5,-5,width+5, height+5);
    }

    public void keyPressed() {
        if(key == 'q'){
            //videoExport.endMovie();
            exit();
        }
    }
}