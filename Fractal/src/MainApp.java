import com.hamoid.VideoExport;
import processing.core.*;
import java.util.List;

/**
 * The main class that starts first.
 * Extending PApplet is just a way of running this from the IntelliJ IDEA ide.
 *
 * more info:
 * https://proto.ink/2016/06/03/processing-in-intellij-combining-proper-java-with-processing/
 */
public class MainApp extends PApplet{

    //tree parameters
    private TreeManager tree;
    private int rootCount;
    private int generations;
    private int childCount;
    private float spread;              // magnitude of heading change from parent to children
    private float size;                // the length of the root branch
    private float relativeChildSize;   // 1 = same as parent, 0,5 = half, 2 = double size
    private float firstRootAngle;

    private boolean captureVideoFlag;
    private VideoExport videoExport;

    //draw effects
    float trailEffect;
    int backColour;
    float transparency;

    //if you want to run this in regular processing or APDE on android
    //just delete everything that throws an error and you're set m8
    //keep in mind that the IDEA environment is superior to any alternative
    public static void main(String[] args)
    {
        PApplet.main("MainApp", args );
    }

    public void settings()
    {
        fullScreen();
        //size(1200, 880);
    }

    public void setup()
    {
        //tree parameters
        frameRate(30);
        rootCount = 4;
        generations = 3;
        childCount = 3;
        spread = 0;
        size = 140;
        firstRootAngle = 0;
        relativeChildSize = 0.8f;

        captureVideoFlag = true;
        colorMode(RGB , 100, 100, 100, 100);

        //draw effects
        backColour = 0;
        trailEffect = 5;
        transparency = 20;

        //apply the background immediately to avoid having to fade into the final backColour from 0 or something
        fill(backColour);
        rect(0,0,width, height);

        //check this class out too
        tree = new TreeManager(this);

        //video capture setup
        if (captureVideoFlag)
        {
            videoExport = new VideoExport(this);
            videoExport.startMovie();
        }
    }

    public void draw()
    {
        tree.populate(
                rootCount,
                generations,
                childCount,
                spread += 0.05,
                size,
                relativeChildSize,
                firstRootAngle
        );

        drawTree(
                tree.getMainTree(),
                backColour,
                trailEffect,
                transparency
        );

        if (captureVideoFlag)
        {
            videoExport.saveFrame();
        }
        //println("fps: " + frameRate);
    }

    private void drawTree(List<List<Branch>> treeOfTrees,  int backColour, float trailEffect, float transparency )
    {
        fill(backColour, trailEffect);
        rect(-1, -1, width+1, height+1);

        //draw the tree
        if(treeOfTrees !=null && treeOfTrees.size() > 0)
        {
            for(List<Branch> subTree : treeOfTrees)
            {
                for(Branch b : subTree)
                {
                    stroke(
                            50+cos(getAbsoluteAngle(b))*50 + sin(getAbsoluteAngle(b))*50,
                            0,
                            50-cos(getAbsoluteAngle(b))*50 + sin(getAbsoluteAngle(b))*50,
                            transparency);

                    line(b.origin.x, b.origin.y, b.target.x, b.target.y);
                }
            }
        }
    }

    /**
     * Gets the positive value of the angle from the input branch to an arbitrary baseline.
     * -1 becomes 1. 1 stays 1. Useful for working with colours I think. Don't wanna go below zero.
     *
     * Ultimately unimportant.
     * @param b
     * @return
     */
    private float getAbsoluteAngle(Branch b)
    {
        float result = tree.getAngle(b.origin,b.target);
        if(sin(result)< 0){
            result *= -1;
        }
        return result;
    }


    public void keyPressed() {
        if (key == 'q')
        {
            if(captureVideoFlag)
            {
                videoExport.endMovie();
            }
            exit();
        }

    }
}