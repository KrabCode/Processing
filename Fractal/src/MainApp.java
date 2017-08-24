import com.hamoid.VideoExport;
import processing.core.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class that starts first.
 * Extending PApplet is just a way of running this from the IntelliJ IDEA ide.
 *
 * more info:
 * https://proto.ink/2016/06/03/processing-in-intellij-combining-proper-java-with-processing/
 */
public class MainApp extends PApplet{

    private TreeManager tree;
    private int generations;
    private int childCount;
    private int rootCount;
    private float spread;              // magnitude of heading change from parent to children
    private float size;                // the length of the root branch
    private float relativeChildSize;   // 1 = same as parent, 0,5 = half, 2 = double size
    private boolean captureVideo;

    private List<SpecialEffect> effects;

    private VideoExport videoExport;

    //delete main() and settings() in order to run this sketch in traditional processing
    public static void main(String[] args)
    {
        PApplet.main("MainApp", args );
    }

    public void settings()
    {
        //fullScreen();
        size(1200, 1080);
    }

    public void setup()
    {
        //SETTINGS SETUP
        frameRate(30);
        rootCount = 2;
        generations = 5;
        childCount = 3;
        spread = 0;
        size = 100;
        relativeChildSize = 0.8f;
        captureVideo = false;
        if (captureVideo)
        {
            videoExport = new VideoExport(this);
            videoExport.startMovie();
        }

        //VISUAL EFFECTS SETUP
        SpecialEffect trails = new SpecialEffect();
        trails.effectType = EffectType.TRAILS;
        trails.magnitude = 10;

        SpecialEffect foreColour = new SpecialEffect();
        foreColour.effectType = EffectType.FOREGROUND_BASE_COLOR;
        foreColour.magnitude = 50;

        SpecialEffect backColour = new SpecialEffect();
        backColour.effectType = EffectType.BACKGROUND_COLOR;
        backColour.magnitude = 255;

        effects = new ArrayList<>();
        effects.add(trails);
        effects.add(foreColour);
        effects.add(backColour);

        tree = new TreeManager(this);
    }

    public void draw(){
        tree.populate(rootCount,
                generations,
                childCount,
                spread += 0.1,
                size,
                relativeChildSize);
        tree.draw(effects);
        if (captureVideo)
        {
            videoExport.saveFrame();
            if(spread == 360)
            {
                videoExport.endMovie();
            }
        }
        println("fps: " + frameRate);
    }

    public void keyPressed() {
        if (captureVideo && key == 'q')
        {
            videoExport.endMovie();
        }
    }
}