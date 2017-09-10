//
//import processing.core.PApplet;
////import processing.sound.*;
//import java.util.ArrayList;
//
////import com.hamoid.VideoExport;
//
//public class MainApp extends PApplet{
//
//    public static void main(String[] args)
//    {
//        PApplet.main("MainApp", args);
//    }
//
//    public void settings()
//    {
//        fullScreen();
//    }
//    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    //   SOUND
//    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    /**
//     * Processing Sound Library, Example 6
//     *
//     * This sketch shows how to use the Amplitude class to analyze a
//     * stream of sound. In this case a sample is analyzed. The smoothFactor
//     * variable determines how much the signal will be smoothed on a scale
//     * from 0 - 1.
//     */
//
//
////
////    // Declare the processing sound variables
////    SoundFile sample;
////    Amplitude rms;
////
////    // Declare a scaling factor
////    float scale = 5.0;
////
////    // Declare a smooth factor
////    float smoothFactor = 0.25;
////
////    // Used for smoothing
////    float sum;
////
////    void setup() {
////        size(640, 360);
////
////        //Load and play a soundfile and loop it
////        sample = new SoundFile(this, "beat.aiff");
////        sample.loop();
////
////        // Create and patch the rms tracker
////        rms = new Amplitude(this);
////        rms.input(sample);
////    }
////
////    void draw() {
////        // Set background color, noStroke and fill color
////        background(0, 0, 255);
////        noStroke();
////        fill(255, 0, 150);
////
////        // Smooth the rms data by smoothing factor
////        sum += (rms.analyze() - sum) * smoothFactor;
////
////        // rms.analyze() return a value between 0 and 1. It's
////        // scaled to height/2 and then multiplied by a scale factor
////        float rmsScaled = sum * (height/2) * scale;
////
////        // Draw an ellipse at a size based on the audio analysis
////        ellipse(width/2, height/2, rmsScaled, rmsScaled);
////    }
//
//
//    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    //   VISUALS
//    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//    //center rectangle
//    Point cA, cB, cC, cD;
//    float cSize;
//    //lines from the rectangle to the corners of the screen
//    Line tl, tr, br, bl;
//
//    ArrayList<Distance> N;
//
//    float speed;
//    float freq;
//    float acceleration;
//
//    public void setup(){
//        frameRate(60);
//        background(0);
//        speed = 0;
//        freq = 30;
//        acceleration = 0.01f;
//
//        //setup center rectangle
//        cSize = 20;
//
//        cA =new Point(width/2 - cSize, height/2 - cSize);
//        cB =new Point(width/2 + cSize, height/2 - cSize);
//        cC =new Point(width/2 + cSize, height/2 + cSize);
//        cD =new Point(width/2 - cSize, height/2 + cSize);
//
//        //top left, right, bottom right, left
//        tl = new Line(cA.x, cA.y, 0, 0);
//        tr = new Line(cB.x, cB.y, width, 0);
//        br = new Line(cC.x, cC.y, width, height);
//        bl = new Line(cD.x, cD.y, 0, height);
//
//        N = new ArrayList<Distance>(){};
//        N.add(new Distance(0f, speed, acceleration));
//    }
//
//
//    public void draw(){
////        draw background + trail effect
//        noStroke();
//        fill(0, 20);
//        rect(0,0,width, height);
//
//
////      draw center rectangle
//        fill(0);
//        rect(cA.x, cA.y, cC.x- cA.x ,  cC.y - cA.y);
//
////      draw lines connecting corners
////
////        tl.draw();
////        tr.draw();
////        br.draw();
////        bl.draw();
//
//        strokeWeight(4);
//        //draw lines connecting lines connecting corners
//        for(int i = 0; i < N.size(); i++){
//
//            Line left = new Line(
//                    bl.getPointOnLineAtDistanceFromA(N.get(i).dist),
//                    tl.getPointOnLineAtDistanceFromA(N.get(i).dist)
//            );
//            Line right = new Line(
//                    tr.getPointOnLineAtDistanceFromA(N.get(i).dist),
//                    br.getPointOnLineAtDistanceFromA(N.get(i).dist)
//            );
//
//
//            Line bottom = new Line(
//                    br.getPointOnLineAtDistanceFromA(N.get(i).dist, 60),
//                    bl.getPointOnLineAtDistanceFromA(N.get(i).dist, -60)
//            );
//
//
//// blue neon in a midnight country road
//// stroke(70, 173, 212);
//
//            stroke(255-N.get(i).dist/3, 20, 20);
//            left.draw();
//            right.draw();
//
//            stroke(255);
//            bottom.draw();
//            Distance n = N.get(i); //n[i]+=speed;
//            n.vel += n.acc;
//            n.dist+= n.vel;
//        }
//        if(frameCount%freq==0){
//            N.add(new Distance(0, speed, acceleration));
//
//            cleanupOutboundLines();
//        }
////        ex.saveFrame();
//        println(N.size() + " : " + frameRate);
//    }
//
//    void cleanupOutboundLines(){
//        ArrayList<Distance> toRemove = new ArrayList<>();
//        for(Distance n : N)
//        {
//            if(n.dist > width && n.dist > height){
//                toRemove.add(n);
//            }
//        }
//        N.removeAll(toRemove);
//    }
//
//    class Point{
//        float x, y;
//
//        public Point(float x, float y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//    class Distance{
//        float dist, vel, acc;
//
//        public Distance(float dist, float vel, float acc) {
//            this.dist = dist;
//            this.vel = vel;
//            this.acc = acc;
//        }
//    }
//
//    class Line{
//        Point A, B;
//        public Line(float Ax, float Ay, float Bx, float By){
//            A = new Point(Ax, Ay);
//            B = new Point(Bx, By);
//        }
//
//        public Line(Point A, Point B){
//            this.A = A;
//            this.B = B;
//        }
//
//        public Point getPointOnLineAtDistanceFromA(float dist){
//            float angle = getAngle(A, B);
//            return findPointOnEdgeOfCircle(A, dist, angle);
//        }
//
//        public Point getPointOnLineAtDistanceFromA(float dist, float angleCoeff){
//            float angle = getAngle(A, B) + angleCoeff;
//            return findPointOnEdgeOfCircle(A, dist, angle);
//        }
//
//        public void draw(){
//            line(A.x, A.y, B.x, B.y);
//        }
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////
//    //  LOW LEVEL MATH
//    ////////////////////////////////////////////////////////////////////////////////////////////
//    /**
//     * Finds a point in a given angle and distance from a center point.
//     * @param center center point
//     * @param radius given distance
//     * @param angle given angle
//     * @return
//     */
//    public Point findPointOnEdgeOfCircle(Point center, float radius, float angle)
//    {
//        return new Point(
//                center.x + radius * cos(angle * PI / 180),
//                center.y + radius * sin(angle * PI / 180)
//        );
//    }
//
//    /**
//     * Returns angle of a line vs the horizont
//     *
//     * @param origin start of the line
//     * @param end end of the line 🚆
//     * @return horizontal line returns 0, vertical line returns -90
//     */
//    public float getAngle(Point origin, Point end)
//    {
//        return degrees(atan2(end.y - origin.y, end.x - origin.x));
//    }
//}