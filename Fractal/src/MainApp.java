import processing.core.*;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends PApplet{

    private TreeManager _tree;

    private int _generations;
    private int _childCount;
    private float _spread;   // magnitude of heading change from parent to children
    private float _size;        // the length of the root branch
    private float _relativeChildSize;   // 1 = same as parent, 0,5 = half, 2 = double size

    private List<SpecialEffect> _effects;

    public static void main(String[] args)
    {
        PApplet.main("MainApp", args );
    }

    public void settings()
    {
        //fullScreen();
        size(1000, 820);

    }

    public void setup()
    {
        frameRate(30);
        _spread = 0;
        _size = 80;
        _generations = 4;
        _childCount = 4;
        _relativeChildSize = 1f;


        //VISUAL EFFECTS SETUP
        _effects = new ArrayList<>();

        SpecialEffect e01 = new SpecialEffect();
        e01.effectType = EffectType.TRAILS;
        e01.magnitude = 30;
        _effects.add(e01);

        _tree = new TreeManager(this);
    }

    public void draw(){

        _tree.populate(_generations,
                _childCount,
                _spread += 0.5,
                _size,
                _relativeChildSize);
        _tree.draw(_effects);
    }
}