//import processing.core.PApplet;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class MainApp extends PApplet{
//
//    int lineCount, spawnrate;
//    float  minSpd, maxSpd, minStagnant, maxStagnant;
//    List<Line> lines;
//
//    public static void main(String[] args)
//    {
//        PApplet.main("MainApp", args);
//    }
//
//    public void settings()
//    {
//        //fullScreen();
//        size(800,600);
//    }
//
//    public void setup(){
//        lineCount = 40;
//        spawnrate = 4;
//        minSpd = -1.0f;
//        maxSpd =  1.0f;
//        minStagnant = -0.15f;
//        maxStagnant =  0.15f;
//        lines = new ArrayList<Line>();
//
//        for(int i = 0; i < lineCount; i++){
//            lines.add(generateRandomLine());
//        }
//    }
//
//
//    public void draw(){
//        noStroke();
//        fill(0, 10);
//        rect(0,0,width,height);
//
//        if(frameCount % spawnrate == 0 && lines.size() < lineCount){
//            lines.add(generateRandomLine());
//        }
//
//        stroke(0,250,0, 30);
//        for(Line l : lines){
//            l.draw();
//        }
//
//        removeAllOffscreenLines();
//    }
//
//    Line generateRandomLine(){
//        Line l = new Line();
//        if(random(1) > 0.5){
//            l.span = Span.hor;
//            l.loc = random(0, height);
//        }else {
//            l.span = Span.ver;
//            l.loc = random(0, width);
//        }
//
//        //get a speed between min and max that is also not too close to 0
//        do {
//            l.spd = random(minSpd, maxSpd);
//        }
//        while(l.spd > minStagnant && l.spd < maxStagnant);
//        return l;
//    }
//
//    void removeAllOffscreenLines(){
//        List<Line> toRemove = new ArrayList<Line>();
//        for(Line l : lines){
//            if(l.span == Span.hor){
//                if(l.loc < 0 || l.loc > height){
//                    toRemove.add(l);
//                }
//            }else if(l.span == Span.ver){
//                if(l.loc < 0 || l.loc > width){
//                    toRemove.add(l);
//                }
//            }
//        }
//        lines.removeAll(toRemove);
//    }
//
//    enum Span {hor, ver}
//    class Line{
//        float loc;
//        float spd;
//        Span span;
//
//        void draw(){
//            if(span == Span.hor){
//                line(0, loc, width, loc);
//            }
//            else if (span == Span.ver){
//                line(loc, 0, loc, height);
//            }
//            loc += spd;
//        }
//
////  Vertical span example
////        -----loc--------------
////        |     |              |
////        |     |              |
////        |     |              |
////        |     |              |
////        -----loc--------------
////
//// Horizontal span example
////        ----------------------
////        |                    |
////       loc------------------loc
////        |                    |
////        |                    |
////        ----------------------
//    }
//}