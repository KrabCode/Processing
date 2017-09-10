
import processing.core.PApplet;
import java.util.ArrayList;


public class MainApp extends PApplet {

    public static void main(String[] args) {
        PApplet.main("MainApp", args);
    }

    public void settings() {
        //fullScreen();
        size(1000, 1000);
    }
    //tree parameters
    private TreeManager tree;
    private int rootCount;
    private int generations;
    private int childCount;
    private float spread;
    private float spreadChangeSpeed;
    //magnitude of heading change from parent to children
    private float size;
    // the length of the root branch
    private float relativeChildSize;
    // 1 = same as parent, 0,5 = half, 2 = double size
    private float firstRootAngle;
    //draw effects
    private float trailEffect;
    private int backColour;
    private float opacity;

    private boolean mouseHeld;
    private int colourMode;

    public void setup() {
        frameRate(30);
        backColour = 0;
        tree = new TreeManager();
        // tree parameters

        rootCount = 2;
        generations = 3;
        childCount = 5;
        spread = 30;
        spreadChangeSpeed = 0.05f;
        size = 100;
        firstRootAngle = 90;
        relativeChildSize = 1f;
        opacity = 20;
        trailEffect = 3;

        // diminishing tree parameters
        /*
        rootCount = 2;
        generations = 7;
        childCount = 1;
        spread = 10;
        spreadChangeSpeed = 0.1f;
        size = 70;
        firstRootAngle = -90;
        relativeChildSize =0.8f;
        opacity = 100;
*/


        colorMode(RGB, 100, 100, 100, 100);

        // apply the background immediately to avoid having to fade into the final backColour from 0
        noStroke();
        fill(backColour);
        rect(0, 0, width, height);
    }

    public void draw() {
        //draw background without the background() method as it can't use alpha on the main drawing surface, this can
        noStroke();
        fill(backColour, trailEffect);
        rect(0,0,width, height);

        tree.populate(rootCount,
                generations,
                childCount,
                spread += spreadChangeSpeed,
                size,
                relativeChildSize,
                firstRootAngle
        );
        drawTree(tree.getMainTree());

        if(mousePressed && !mouseHeld){
            mouseHeld = true;
            colourMode++;
            if(colourMode > 3)
            {
                colourMode = 0;
            }
        }else if (!mousePressed){
            mouseHeld = false;
        }

        println("fps: " + (int) frameRate +
                " branchCount: " + tree.getBranchCount() +
                " colourMode: " + colourMode
        );
    }

    private void drawTree(ArrayList<ArrayList<Branch>> treeOfTrees) {
        //draw every branch
        if (treeOfTrees != null && treeOfTrees.size() > 0) {
            for (ArrayList<Branch> subTree : treeOfTrees) {
                for (Branch b : subTree) {
                    switch (colourMode){
                        case 0: strokePink(b);      break;
                        case 1: strokeBlue(b);      break;
                        case 2: strokeGreen(b);     break;
                        case 3: strokeRainbow(b);   break;
                    }
                    line(b.origin.x, b.origin.y, b.target.x, b.target.y);
                }
            }
        }


    }



    public void strokePink(Branch b){
        stroke(100,
                20 + cos(getAbsoluteAngle(b)) * 20 + sin(getAbsoluteAngle(b)) * 20,
                60 - sin(getAbsoluteAngle(b)) * 20 + cos(getAbsoluteAngle(b)) * 20,
                opacity);
    }

    public void strokeBlue(Branch b){
        stroke(
                20 + cos(getAbsoluteAngle(b)) * 20 + sin(getAbsoluteAngle(b)) * 20,
                60 - sin(getAbsoluteAngle(b)) * 20 + cos(getAbsoluteAngle(b)) * 20,
                100,
                opacity);
    }
    public void strokeGreen(Branch b){
        stroke(
                20 + cos(getAbsoluteAngle(b)) * 20 + sin(getAbsoluteAngle(b)) * 20,
                100,
                60 - sin(getAbsoluteAngle(b)) * 20 + cos(getAbsoluteAngle(b)) * 20,
                opacity);
    }

    public void strokeRainbow(Branch b){
        stroke(
                50 + sin(getAbsoluteAngle(b)) * 25 - cos(getAbsoluteAngle(b)) * 25,
                50 - cos(getAbsoluteAngle(b)) * 25 - sin(getAbsoluteAngle(b)) * 25,
                50 - sin(getAbsoluteAngle(b)) * 25 + cos(getAbsoluteAngle(b)) * 25,
                opacity);
    }

    /**
     * Gets the positive value of the angle from the input branch to an arbitrary baseline.
     * -1 becomes 1. 1 stays 1. Useful for working with colours I think. Don't wanna go below zero.
     * Ultimately unimportant.
     */
    private float getAbsoluteAngle(Branch b) {
        float result = tree.getAngle(b.origin, b.target);
        if (result < 0) {
            result *= -1;
        }
        return result;
    }

    public class TreeManager {
        private ArrayList<ArrayList<Branch>> TREE_OF_TREES;

        public ArrayList<ArrayList<Branch>> getMainTree() {
            return TREE_OF_TREES;
        }

        //////////////////////////////////////////////////////////////////////////////////////////
        // HIGH LEVEL TREE LOGIC
        //////////////////////////////////////////////////////////////////////////////////////////

        /**
         * Instantiates n trees and populates them with branches according to the parameters
         */
        public void populate(int rootCount, int generations, int childCount, float childSpread, float size, float relativeChildSize, float firstRootAngle) {
            if (TREE_OF_TREES == null) {
                TREE_OF_TREES = new ArrayList<ArrayList<Branch>>();
            } else {
                TREE_OF_TREES.clear();
            }
            Point rootCenter = new Point(width / 2, height / 2);
            for (int rootIndex = 0; rootIndex < rootCount; rootIndex++) {
                //the roots will all grow from the middle in slices of (360 / root count)
                float rootAngle = rootIndex * (360 / rootCount);
                //place root at the center of the screen
                Point rootTarget = findPointOnEdgeOfCircle(rootCenter, size, firstRootAngle + rootAngle);
                Branch root = new Branch(rootCenter, rootTarget);
                TREE_OF_TREES.add(new ArrayList<Branch>());
                TREE_OF_TREES.get(rootIndex).add(root);
                //multiply it and each of its children until generations limit is reached
                for (int i = 0; i < generations; i++) {
                    //remember the starting value: the number of children will change, you don't want to multiply freshly created children
                    int startingBranchCount = TREE_OF_TREES.get(rootIndex).size();
                    for (int branchIndex = 0; branchIndex < startingBranchCount; branchIndex++) {
                        ArrayList<Branch> children = multiplyBranch(TREE_OF_TREES.get(rootIndex).get(branchIndex), childSpread, childCount, relativeChildSize);
                        TREE_OF_TREES.get(rootIndex).addAll(children);
                    }
                }
            }
        }

        /**
         * Instantiates additional branches using input branch size and angle.
         * @param branch the parent branch *
         * @param spread angular distance between siblings
         * @param childCount the number of children to instantiate
         * @param relativeChildSize relative child size
         *
         */
        private ArrayList<Branch> multiplyBranch(Branch branch, float spread, int childCount, float relativeChildSize) {
            ArrayList<Branch> resultingChildren = new ArrayList<Branch>();
            Point childOrigin = branch.target;
            float spreadPerChild =  (spread * 2 / (float) childCount);
            float parentAngle =  getAngle(branch.origin, branch.target);
            float parentSize = size;
            //we can skip a lot of the cpu expensive sqrt() if the relativeChildSize is 1
            if(relativeChildSize != 1){
                parentSize = getDistance(branch.origin, branch.target);
            }
            float childSize = parentSize * relativeChildSize;
            for (int childIndex = 0; childIndex < childCount + 1; childIndex++) {
                float firstChildAngle = parentAngle - spread;
                float angle = firstChildAngle + spreadPerChild * childIndex;
                Point childTarget = findPointOnEdgeOfCircle(childOrigin, childSize, angle);
                Branch child = new Branch(childOrigin, childTarget);
                resultingChildren.add(child);
            }
            return resultingChildren;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        // LOW LEVEL TREE MATH
        // //////////////////////////////////////////////////////////////////////////////////////////

        /**
         * Finds a point in a given angle and distance from a center point.
         *
         * @param center center point * @param radius given distance
         * @param angle  given angle * @return
         */
        public Point findPointOnEdgeOfCircle(Point center, float radius, float angle) {
            return new Point(center.x + radius * cos(angle * PI / 180), center.y + radius * sin(angle * PI / 180));
        }

        /**
         * Returns angle of a line vs the horizont *
         *
         * @param origin start of the line
         * @param end    end of the line 🚆
         * @return horizontal line returns 0, vertical line returns -90 or 90
         */
        public float getAngle(Point origin, Point end) {
            return degrees(atan2(end.y - origin.y, end.x - origin.x));
        }

        /**
         * Pythagoras died for our sins
         * @param origin
         * @param end
         * @return
         */
        public float getDistance(Point origin, Point end){
            float TriangleSideX = end.x - origin.x;
            float TriangleSideY = end.y - origin.y;
            float hypotenuse = sqrt(TriangleSideX*TriangleSideX + TriangleSideY * TriangleSideY);
            return hypotenuse;
        }

        /**
         *
         * @return
         */
        public int getBranchCount() {
            int total = 0;
            for (int i = 0; i < TREE_OF_TREES.size(); i++) {
                total += TREE_OF_TREES.get(i).size();
            }
            return total;
        }
    }

    public class Branch {
        public Point origin;
        public Point target;

        /**
         * Constructor
         * @param origin point A
         * @param target point B
         */
        public Branch(Point origin, Point target) {
            this.origin = origin;
            this.target = target;
        }
    }

    public class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}