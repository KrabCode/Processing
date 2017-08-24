import processing.core.*;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.degrees;
import static processing.core.PConstants.PI;

/**
 * A class that instantiates and draws a tree of vector pairs (branches).
 */
public class TreeManager {

    private PApplet p;
    private List<List<Branch>> TREE_OF_TREES;

    TreeManager(PApplet parent)
    {
        p = parent;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //  BUSINESS LOGIC
    ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Instantiates n trees and populates them with branches according to the parameters.     *
     * @param rootCount how many roots and therefore trees to instantiate
     * @param generations how many levels should the tree have
     * @param childCount how many children on each level
     * @param childSpread angular distance between siblings
     * @param size absolute size of the tree
     * @param relativeChildSize relative size of every child to its parent branch
     */
    public void populate(int rootCount, int generations, int childCount,
                         float childSpread, float size, float relativeChildSize)
    {
        if(TREE_OF_TREES == null)
        {
            TREE_OF_TREES = new ArrayList<>();
        }else{
            TREE_OF_TREES.clear();
        }

        Point rootCenter = new Point(p.width/2, p.height/2);
        for(int rootIndex = 0; rootIndex < rootCount; rootIndex++)
        {
            //the roots will all grow from the middle in slices of (360 / root count)
            float rootAngle = rootIndex*(360/rootCount);

            //place root at the center of the screen
            Point rootTarget = findPointOnEdgeOfCircle(rootCenter, size, -90 + rootAngle);
            Branch root = new Branch(rootCenter, rootTarget);
            TREE_OF_TREES.add(new ArrayList<>());
            TREE_OF_TREES.get(rootIndex).add(root);

            //multiply it and each of its children until generations limit is reached
            for(int i = 0; i < generations; i++)
            {
                //remember the starting value: the number of children will change, you don't want to multiply freshly created children
                int startingBranchCount = TREE_OF_TREES.get(rootIndex).size();
                for(int branchIndex = 0; branchIndex < startingBranchCount; branchIndex++)
                {
                    List<Branch> children = multiplyBranch(TREE_OF_TREES.get(rootIndex).get(branchIndex),
                            childSpread, childCount, size*relativeChildSize);
                    TREE_OF_TREES.get(rootIndex).addAll(children);
                }
            }
        }
    }

    /**
     * Instantiates additional branches using input branch size and angle.
     * @param branch the parent branch
     * @param spread angular distance between siblings
     * @param childCount the number of children to instantiate
     * @param childSize absolute child size
     * @return
     */
    private List<Branch> multiplyBranch(Branch branch, float spread, int childCount, float childSize)
    {
        List<Branch> resultingChildren = new ArrayList<>();
        Point childOrigin = branch.target;
        float spreadPerChild = spread*2 / (float)childCount;
        float parentAngle = getAngle(branch.origin, branch.target);
        for(int childIndex = 0; childIndex <= childCount; childIndex++)
        {
            float firstChildAngle = parentAngle - spread;
            float angle =  firstChildAngle + spreadPerChild * childIndex;
            Point childTarget = findPointOnEdgeOfCircle(childOrigin, childSize, angle);
            Branch child = new Branch(childOrigin, childTarget);
            resultingChildren.add(child);
        }
        return resultingChildren;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //  DRAWING TO THE GUI
    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Draws the tree to the p canvas.
     * @param effects list of effects to display when drawing
     */
    public void draw(List<SpecialEffect> effects)
    {
        //paint the background
        SpecialEffect trailEffect = getEffectByType(EffectType.TRAILS, effects);
        SpecialEffect foreColour = getEffectByType(EffectType.FOREGROUND_BASE_COLOR, effects);
        SpecialEffect backColour = getEffectByType(EffectType.BACKGROUND_COLOR, effects);
        if(trailEffect!=null)
        {
            if(backColour != null)
            {
                //the magnitude is the alpha of the background: lower = more trails
                p.fill(backColour.magnitude, trailEffect.magnitude);
            }else{
                //the magnitude is the alpha of the background: lower = more trails
                p.fill(255, trailEffect.magnitude);
            }
        }else{
            p.fill(255);
        }
        p.rect(0, 0, p.width, p.height);

        //draw the tree
        if(TREE_OF_TREES !=null && TREE_OF_TREES.size() > 0)
        {
            if(foreColour != null)
            {
                //todo does not work.. why?
                p.fill(foreColour.magnitude);
            }
            else
            {
                p.fill(0);
            }
            for(List<Branch> subTree : TREE_OF_TREES)
            {
                for(Branch b : subTree)
                {
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

    ////////////////////////////////////////////////////////////////////////////////////////////
    //  MATH
    ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Finds a point in a given angle and distance from a center point.
     * @param center center point
     * @param radius given distance
     * @param angle given angle
     * @return
     */
    private Point findPointOnEdgeOfCircle(Point center, float radius, float angle)
    {
        Point result = new Point(
                center.x + radius * p.cos(angle * PI / 180),
                center.y + radius * p.sin(angle * PI / 180)
        );
        return result;
    }

    /**
     * Returns angle of a line vs the horizont
     *
     * @param origin start of the line
     * @param end end of the line 🚆
     * @return horizontal line returns 0, vertical line returns -90
     */
    private float getAngle(Point origin, Point end)
    {
        return degrees(p.atan2(end.y - origin.y, end.x - origin.x));
    }

}