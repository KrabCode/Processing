import Randomness.GaussianDistribution;
import Randomness.MontecarloDistribution;
import Randomness.Perlin;
import Randomness.Static;
import Vectors.BounceBallGrid;
import com.hamoid.VideoExport;
import processing.core.PApplet;
import processing.core.PVector;


public class MainApp extends PApplet{

    public static void main(String[] args)
    {
        PApplet.main("MainApp", args);
    }
    public void settings()
    {
        fullScreen();
    }


    private GaussianDistribution gauss;
    private MontecarloDistribution monte;
    private Perlin perlin;
    private Static stat;
    private BounceBallGrid bounceBallGrid_ver;

    private BounceBallGrid bounceBallGrid_hor;

    VideoExport videoExport;

    public void setup(){

        bounceBallGrid_hor = new BounceBallGrid(this,
                30,
                20,
                1f,
                new PVector(0.5f,0f),
                1,
                0.0000001f,
                1,
                0
        );

        bounceBallGrid_ver = new BounceBallGrid(this,
                30,
                20,
                1f,
                new PVector(0,0.5f),
                1,
                0.000001f,
                0,
                1
        );

        drawBackground(false);

        videoExport = new VideoExport(this);
        videoExport.startMovie();
    }

    public void draw()
    {
        bounceBallGrid_ver.draw();
        bounceBallGrid_hor.draw();

        drawBackground(true);
        videoExport.saveFrame();
    }

    public void drawBackground(boolean trailEffect)
    {
        if(trailEffect)    fill(0, 10);
        else                fill(0);
        rect(-5,-5,width+5, height+5);
    }

    public void keyPressed() {
        if(key == 'q'){
            videoExport.endMovie();
            exit();
        }
    }
}
