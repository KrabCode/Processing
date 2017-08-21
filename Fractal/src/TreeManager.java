import processing.core.PVector;
import processing.core.*;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.degrees;
import static processing.core.PConstants.PI;


public class TreeManager {

    private PApplet p;
    private ArrayList<Branch> _mainTree;

    TreeManager(PApplet parent)
    {
        p = parent;
    }

    public void populate(int generations, int childCount, float childSpread, float size, float relativeChildSize)
    {
        //place root at the center of the screen
        PVector rootOrigin = new PVector(p.width/2-size, p.height/2+size);
        PVector rootTarget = new PVector(p.width/2, p.height/2);
        Branch root = new Branch(rootOrigin, rootTarget);
        _mainTree = new ArrayList<>();
        _mainTree.add(root);

        //multiply it and each of its children until generations limit is reached
        for(int i = 0; i < generations; i++)
        {
            int startingBranchCount = _mainTree.size(); //remember the starting value: the number of children will change
            for(int j = 0; j < startingBranchCount; j++)
            {
                List<Branch> children = multiplyBranch(_mainTree.get(j),
                        childSpread, childCount, size*relativeChildSize);
                _mainTree.addAll(children);
            }
        }
    }

    private List<Branch> multiplyBranch(Branch branch, float spread, int childCount, float childSize)
    {
        List<Branch> resultingChildren = new ArrayList<>();
        if(spread>360*4)
        {
            spread = spread % 360*4; //limit it to the maximum angle
        }

        float spreadPerChild = spread*2 / (float)childCount;
        PVector childOrigin = new PVector(branch.target.x, branch.target.y);
        for(int i = 0; i < childCount+1; i++)
        {
            float parentAngle = degrees(PVector.angleBetween(branch.origin, branch.target));
            float firstChildAngle = parentAngle - spread;
            float angle =  firstChildAngle + spreadPerChild * i;
            PVector childTarget = findPointOnEdgeOfCircle(childOrigin, childSize, angle);
            Branch child = new Branch(childOrigin, childTarget);
            resultingChildren.add(child);
        }
        return resultingChildren;
    }

    private PVector findPointOnEdgeOfCircle(PVector center, float radius, float angle)
    {
        return new PVector(
                center.x + radius * p.cos(angle * PI / 180),
                center.y + radius * p.sin(angle * PI / 180)
        );
    }

    public void draw(List<SpecialEffect> effects)
    {
        //BACKGROUND
        SpecialEffect trailEffect = getEffectByType(EffectType.TRAILS, effects);
        if(trailEffect!=null)
        {
            p.fill(255, trailEffect.magnitude);

        }else{
            p.fill(255);
        }
        p.rect(0, 0, p.width, p.height);

        //DRAW TREE
        if(_mainTree!=null && _mainTree.size() > 0)
        {
            for(Branch b : _mainTree)
            {
                p.fill(0);
                p.line(b.origin.x, b.origin.y, b.target.x, b.target.y);
            }
        }
    }

    /**
     * Picks the first effect of a given type.
     * This means the second effect of the same type will be ignored.
     *
     * @param type
     * @param effects
     * @return
     */
    private SpecialEffect getEffectByType(EffectType type, List<SpecialEffect> effects)
    {
        for(int i = 0; i < effects.size(); i++)
        {
            if(effects.get(i).effectType == type)
            {
                return effects.get(i);
            }

        }
        return null;
    }
}