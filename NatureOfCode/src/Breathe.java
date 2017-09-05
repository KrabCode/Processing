public class Breathe {


    /*
    * //FRACTAL CONTROLS



    private Mandelbrot m;
    private float xc;
    private float yc;
    private float size;
    private float zoomModifier;
    private float moveModifier;
    private boolean manualRedraw;
    private boolean autozoom;

    private PImage lastResult;
    //private int xOff ;
    //private int yOff ;

    public void setup()
    {
        drawBackground(false);
        m = new Mandelbrot(this);
        ellipseMode(CENTER);
        zoomModifier = 0.995f;
        moveModifier = 0.01f;
        xc = -0.7499588f;
        yc = 0.019979153f;

        size = 3f;
        manualRedraw = true;
        autozoom = false;
    }

    public void draw() {

        if (autozoom) {
            size *= zoomModifier;
            computeMandelbrotSet();
        }
        if (manualRedraw){
            manualRedraw = !manualRedraw;
            computeMandelbrotSet();
        }
        if(videoExport != null){
            videoExport.saveFrame();
        }
        //draw the zoom target
        stroke(255,0,0);
        noFill();
        ellipse(width/2, height/2, 5,5);

    }

    public void computeMandelbrotSet() {
        print("...");
        drawBackground(false);
        lastResult = m.draw(xc,
                yc,
                size,
                width,
                width
        );
        drawOntoMainSurface();
        print("! ");
        //videoExport.saveFrame();
    }

    public void drawOntoMainSurface(){
        image(lastResult,0,0);
    }

    private void drawBackground(boolean trailEffect)
    {
        if(trailEffect){
            fill(0, 10);
        }
        else{
            fill(0);
        }
        rect(-1,-1,width+1, height+1);
    }

    public void keyPressed() {
        if(key=='v'){
            autozoom = !autozoom;
        }
        if(key=='w'){
            zoomModifier *= 2f;
        }
        if(key=='s'){
            zoomModifier /= 2f;
        }
        if(key=='e'){
            moveModifier *= 10f;
        }
        if(key=='d'){
            moveModifier /= 10f;
        }

        if(key=='k'){
            size *= zoomModifier;
            manualRedraw = true;
        }
        if(key=='j'){
            size /= zoomModifier;
            manualRedraw = true;
        }

        if(keyCode==UP){
            yc -= moveModifier;
        }
        if(keyCode==DOWN){
            yc += moveModifier;
        }
        if(keyCode==LEFT){
            xc -= moveModifier;
        }
        if(keyCode==RIGHT){
            xc += moveModifier;
        }
        if(keyCode==UP ||
            keyCode==DOWN||
            keyCode==LEFT||
            keyCode==RIGHT)
        {
            manualRedraw = true;
        }


        if(key=='r'){
            size = 0.5f;
            manualRedraw = true;
        }
        if(key=='o'){
            if(videoExport == null){
                videoExport = new VideoExport(this);
                videoExport.startMovie();
            }else{
                videoExport.endMovie();
                exit();
            }
        }

        println("xc " + (xc) +
                " | yc " + (yc) +
                " | size " + size +
                " | zoom mod " + zoomModifier +
                " | move mod " + moveModifier +
                " | auto " + autozoom
        );
    }
    *
    * */
    private boolean breatheIn = true;

    private float minRadius = 10;
    private float maxRadiusEdgeMargin = 40;
    private float framesPerBreath = 30*8;

    private float radius;
    private float maxRadius;

    public void setup()
    {
        //fullScreen();
        radius = minRadius;
        if(width < height){
            maxRadius = width - maxRadiusEdgeMargin;
        }else{
            maxRadius = height - maxRadiusEdgeMargin;
        }
        background(0);
        noStroke();
        ellipseMode(CENTER);
    }


    public void draw()
    {
        if(breatheIn){
            radius += (maxRadius-minRadius) / framesPerBreath;
        }else{
            radius -= (maxRadius-minRadius) / framesPerBreath;
        }
        if(radius > maxRadius || radius < minRadius){
            breatheIn = !breatheIn;
        }

        fill(0,30);
        rect(0,0,width, height);
        fill(255, 255, 255, 30);
        ellipse(width/2, height/2, radius, radius);
        println(frameCount + ":" + breatheIn);
    }
}


