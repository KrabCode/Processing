import processing.core.PVector;
import processing.core.*;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.atan2;
import static processing.core.PApplet.degrees;
import static processing.core.PConstants.PI;


public class TreeManager {

    private PApplet p;
    private List<List<Branch>> _mainTree;

    TreeManager(PApplet parent)
    {
        p = parent;
    }

    /**
     * Instantiates the tree and populates it with branches.
     *
     * @param generations how many levels should the tree have
     * @param childCount how many children on each level
     * @param childSpread angular distance between siblings
     * @param size absolute size of the tree
     * @param relativeChildSize relative size of the child
     */
    public void populate(int rootCount, int generations, int childCount, float childSpread, float size, float relativeChildSize)
    {
        _mainTree = new ArrayList<>();
        for(int rootIndex = 0; rootIndex < rootCount; rootIndex++)
        {
            float rootAngle = rootIndex*(360/rootCount);

            //place root at the center of the screen
            PVector rootCenter = new PVector(p.width/2, p.height/2);
            PVector rootTarget = findPointOnEdgeOfCircle(rootCenter, size, -90 + rootAngle);
            Branch root = new Branch(rootCenter, rootTarget);
            _mainTree.add(new ArrayList<>());
            _mainTree.get(rootIndex).add(root);

            //multiply it and each of its children until generations limit is reached
            for(int i = 0; i < generations; i++)
            {
                int startingBranchCount = _mainTree.get(rootIndex).size(); //remember the starting value: the number of children will change
                for(int j = 0; j < startingBranchCount; j++)
                {
                    List<Branch> children = multiplyBranch(_mainTree.get(rootIndex).get(j),
                            childSpread, childCount, size*relativeChildSize);
                    _mainTree.get(rootIndex).addAll(children);
                }
            }
        }
    }

    /**
     * Instantiates additional branches from an input branch's size and angle.
     *
     * @param branch the parent branch
     * @param spread angular distance between siblings
     * @param childCount the number of children to instantiate
     * @param childSize absolute child size
     * @return
     */
    private List<Branch> multiplyBranch(Branch branch, float spread, int childCount, float childSize)
    {
        List<Branch> resultingChildren = new ArrayList<>();
        if(spread>360*2)
        {
            spread = spread % 360*2; //limit the maximum spread
        }

        float spreadPerChild = spread*2 / (float)childCount;
        PVector childOrigin = branch.target;
        float parentAngle = getAngleBetweenVectors(branch.origin, branch.target);
        for(int i = 0; i < childCount+1; i++)
        {
            float firstChildAngle = parentAngle - spread;
            float angle =  firstChildAngle + spreadPerChild * i;
            PVector childTarget = findPointOnEdgeOfCircle(childOrigin, childSize, angle);
            Branch child = new Branch(childOrigin, childTarget);
            resultingChildren.add(child);
        }
        return resultingChildren;
    }

    /**
     * Finds a point in a given angle and distance from a center point.
     * @param center center point
     * @param radius given distance
     * @param angle given angle
     * @return
     */
    private PVector findPointOnEdgeOfCircle(PVector center, float radius, float angle)
    {
        return new PVector(
                center.x + radius * p.cos(angle * PI / 180),
                center.y + radius * p.sin(angle * PI / 180)
        );
    }

    private float getAngleBetweenVectors(PVector origin, PVector end)
    {
        return degrees(p.atan2(end.y - origin.y, end.x - origin.x));
    }


    /**
     * Draws the tree to the p canvas.
     * @param effects list of effects to display when drawing
     */
    public void draw(List<SpecialEffect> effects)
    {
        //paint the background
        SpecialEffect trailEffect = getEffectByType(EffectType.TRAILS, effects);
        if(trailEffect!=null)
        {
            //the magnitude is the alpha of the background: lower = more trails
            p.fill(255, trailEffect.magnitude);

        }else{
            p.fill(255);
        }
        p.rect(0, 0, p.width, p.height);

        //draw the tree
        if(_mainTree!=null && _mainTree.size() > 0)
        {
            for(List<Branch> subTree : _mainTree)
            {
                for(Branch b : subTree)
                {
                    p.fill(0);
                    p.line(b.origin.x, b.origin.y, b.target.x, b.target.y);
                }
            }

        }
    }

    /**
     * Picks the first effect of a given type.
     * This means the second effect of the same type will be ignored.
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