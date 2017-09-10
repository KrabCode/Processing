//
//import com.hamoid.VideoExport;
//import processing.core.PApplet;
//import processing.core.PImage;
//
//import org.apache.commons.math.complex.Complex;
//import processing.core.PApplet;
//import processing.core.PConstants;
//import processing.core.PImage;
//
//import java.awt.*;
//
//
//public class MainApp extends PApplet{
//
//    public static void main(String[] args)
//    {
//        PApplet.main("MainApp", args);
//    }
//    public void settings()
//    {
//        //fullScreen();
//        size(400,400);
//    }
//
//    private Mandelbrot m;
//    private float xc;
//    private float yc;
//    private float size;
//    private float zoomModifier;
//    private float moveModifier;
//    private boolean manualRedraw;
//    private boolean autozoom;
//    private VideoExport videoExport;
//    private PImage lastResult;
//    //private int xOff ;
//    //private int yOff ;
//
//    public void setup()
//    {
//
//        drawBackground(false);
//        m = new Mandelbrot(this);
//        ellipseMode(CENTER);
//        zoomModifier = 0.995f;
//        moveModifier = 0.01f;
//        xc = -0.7499588f;
//        yc = 0.019979153f;
//
//        size = 3f;
//        manualRedraw = true;
//        autozoom = false;
//    }
//
//    public void draw() {
//
//        if (autozoom) {
//            size *= zoomModifier;
//            computeMandelbrotSet();
//        }
//        if (manualRedraw){
//            manualRedraw = !manualRedraw;
//            computeMandelbrotSet();
//        }
//
//        if(videoExport != null){
//            videoExport.saveFrame();
//        }
////draw the zoom target
//        stroke(255,0,0);
//        noFill();
//        ellipse(width/2, height/2, 5,5);
//
//    }
//
//    public void computeMandelbrotSet() {
//        print("...");
//        drawBackground(false);
//        lastResult = m.draw(xc,
//                yc,
//                size,
//                width,
//                width
//        );
//        drawOntoMainSurface();
//        print("! ");
//        //videoExport.saveFrame();
//    }
//
//    public void drawOntoMainSurface(){
//        image(lastResult,0,0);
//    }
//
//    private void drawBackground(boolean trailEffect)
//    {
//        if(trailEffect){
//            fill(0, 10);
//        }
//        else{
//            fill(0);
//        }
//        rect(-1,-1,width+1, height+1);
//    }
//
//    public void keyPressed() {
//        if(key=='v'){
//            autozoom = !autozoom;
//        }
//        if(key=='w'){
//            zoomModifier *= 2f;
//        }
//        if(key=='s'){
//            zoomModifier /= 2f;
//        }
//        if(key=='e'){
//            moveModifier *= 10f;
//        }
//        if(key=='d'){
//            moveModifier /= 10f;
//        }
//
//        if(key=='k'){
//            size *= zoomModifier;
//            manualRedraw = true;
//        }
//        if(key=='j'){
//            size /= zoomModifier;
//            manualRedraw = true;
//        }
//
//        if(keyCode==UP){
//            yc -= moveModifier;
//        }
//        if(keyCode==DOWN){
//            yc += moveModifier;
//        }
//        if(keyCode==LEFT){
//            xc -= moveModifier;
//        }
//        if(keyCode==RIGHT){
//            xc += moveModifier;
//        }
//        if(keyCode==UP ||
//                keyCode==DOWN||
//                keyCode==LEFT||
//                keyCode==RIGHT)
//        {
//            manualRedraw = true;
//        }
//
//
//        if(key=='r'){
//            size = 0.5f;
//            manualRedraw = true;
//        }
//        if(key=='o'){
//
//            if(videoExport == null){
//                videoExport = new VideoExport(this);
//                videoExport.startMovie();
//            }else{
//                videoExport.endMovie();
//                exit();
//            }
//        }
//
//        println("xc " + (xc) +
//                " | yc " + (yc) +
//                " | size " + size +
//                " | zoom mod " + zoomModifier +
//                " | move mod " + moveModifier +
//                " | auto " + autozoom
//        );
//    }
//}
//
//class Mandelbrot {
//    private PApplet p;
//    public Mandelbrot(PApplet p){
//        this.p = p;
//    }
//
//    public PImage draw(double xc , double yc , double size, int width, int height)
//    {
//        PImage result = p.createImage(width, height, 1);
//        int max = 255;   // maximum number of iterations
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                double x0 = xc - size/2 + size*i/width;
//                double y0 = yc - size/2 + size*j/height;
//                Complex z0 = new Complex(x0, y0);
//                int gray = max - mand(z0, max);
//                //if(gray*2 > 255) gray = 255/2;
//                Color c = new Color(255-gray,255-gray, 255-gray);
//                result.set(i, j, c.getRGB());
//            }
//        }
//        return result;
//    }
//
//    // return number of iterations to check if c = a + ib is in Mandelbrot set
//    private static int mand(Complex z0, int max) {
//        Complex z = z0;
//        for (int t = 0; t < max; t++) {
//            if (z.abs() > 2.0) return t;
//            z = z.multiply(z).add(z0);
//        }
//        return max;
//    }
//}