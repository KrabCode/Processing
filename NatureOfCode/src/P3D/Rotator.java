//package P3D;
//
//import processing.core.PApplet;
//
//import com.hamoid.VideoExport;
//import processing.core.PApplet;
//import processing.core.PImage;
//
//import processing.core.PApplet;
//import processing.core.PConstants;
//import processing.core.PImage;
//
//import java.awt.*;
//
//
//
//    public class MainApp extends PApplet {
//
//        public static void main(String[] args)
//        {
//            PApplet.main("MainApp", args);
//        }
//        public void settings()
//        {
//            //fullScreen();
//            size(400,400, P3D);
//        }
//
//        float angle;
//
//        public void setup() {
//            noStroke();
//        }
//
//        public void draw() {
//            background(0);
//            camera(width/2, height/2, 300, width/2, height/2, 0, 0, 1, 0);
//
//            pointLight(200, 200, 200, width/2, height/2, -200);
//
//            translate(width/2, height/2);
//            rotateY(angle);
//
//            beginShape(QUADS);
//            normal(0, 0, 1);
//            fill(50, 50, 200);
//            vertex(-100, +100);
//            vertex(+100, +100);
//            fill(200, 50, 50);
//            vertex(+100, -100);
//            vertex(-100, -100);
//            endShape();
//
//            angle += 0.01;
//        }
//    }
//
