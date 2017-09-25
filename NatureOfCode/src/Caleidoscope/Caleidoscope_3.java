//import processing.core.PApplet;
//import processing.core.PImage;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.hamoid.VideoExport;
//
//public class MainApp extends PApplet{
//
////    concentric circles growing outward made out of various icons / gifs even? one icon type per circle
////    background generated from icon for good contrast?
//
//    int bandsShownMax;
//    int bandsShownSoFar;
//
//    int imgPerBand = 10;
//    int spawnrate = 50;
//    float longevityAccModifier = 1f;
//    float startAcc = 0;
//    float startSpd = 0.3f;
//    float startRacc = 0;
//    float startRspd = 0.008f;
//    float sizeModifier = 40;
//
//    int bgColor = 00;
//    int bgAlpha = 15;
//
//    String imgSourceDir = "C:\\Users\\Jakub\\Desktop\\174834-social-media-logos\\png";
//    List<PImage> imageStore;
//    List<Band> bandsOnScreen;
//
//
//
//    //////////////
//    // ON AIR   //
//    //////////////
//
//    boolean rec = true;
//    String ostSourcePath = "I:\\Roadtrip\\Folk Morricone\\08 Altai Kai - Warriors words.mp3";
//
//    boolean fadeout = false;
//    VideoExport vid;
//    float fadeMag = 0;
//    float fadeSpd = 3f;
//    float fadeMin = 0;
//
//    public static void main(String[] args)
//    {
//        PApplet.main("MainApp", args);
//    }
//
//    public void paintCrosshairs(){
//        stroke(200,0,0);
//        line(0, height/2, width, height/2);
//        line(width/2, 0, width / 2, height);
//    }
//
//    public void settings()
//    {
//
//        fullScreen();
//        // size(1200,800);
//    }
//
//    public void setup(){
//        bandsOnScreen = new ArrayList<Band>();
//        loadImagesFromDisk();
//
//        noStroke();
//        fill(bgColor);
//        rect(0,0,width,height);
//
//        if(rec){
//            vid = new VideoExport(this);
//            vid.setAudioFileName(ostSourcePath);
//            vid.startMovie();
//        }
//    }
//
//    boolean pause = false;
//    public void draw() {
//        if(!pause){
//            //background
//            noStroke();
//            fill(bgColor, bgAlpha);
//            rect(0, 0, width, height);
//            //spawn new bands
//            if (frameCount == 1 || (frameCount % spawnrate == 0)) {
//                bandsOnScreen.add(getNewBand());
//                bandsShownSoFar++;
//            }
//            //draw bands
//            for (Band b : bandsOnScreen) {
//                b.applyForce(sin(b.rot/10)/100, cos(b.loc/10)/100);
//                b.update();
//                b.draw();
//            }
//            removeAllOffscreenBands();
//            if (fadeout) {
//                if (fadeMag < 255 - fadeMin) {
//                    tint(255 - (fadeMag += fadeSpd));
//                } else {
//                    tint(fadeMin);
//                }
//            }
//            if (rec) {
//                vid.saveFrame();
//                println("elapsed: " + vid.getCurrentTime());
//            }
//        }
//
//        if(keyPressed){
//            if(key=='w' && rec) {
//                vid.endMovie();
//                rec = false;
//            }
//            if(key=='f'){
//                fadeout = true;
//            }
//            if(key=='+'){
//                startRacc += 0.01;
//            }
//            if(key=='-'){
//                startAcc += 0.01;
//            }
//        }
//        if(keyPressed && key=='p'){
//            pause = true;
//        }else{
//            pause = false;
//        }
//
//
//    }
//
//
//    void loadImagesFromDisk(){
//        imageStore = new ArrayList<PImage>();
//        List<String> imgFilenames = getFilenamesInDirectory(imgSourceDir);
//        for(String s : imgFilenames){
//            imageStore.add(loadImage(imgSourceDir + "\\" + s));
//        }
//        println("-----------------");
//        println("imgs loaded:" + imageStore.size());
//
//    }
//
//    void removeAllOffscreenBands(){
//        List<Band> toRemove = new ArrayList<Band>();
//        for(Band l : bandsOnScreen){
//            if(l.loc > width + height){
//                toRemove.add(l);
//            }
//        }
//        bandsOnScreen.removeAll(toRemove);
//    }
//
//    private Band getNewBand() {
//        Band b = new Band();
//        b.setup(getRandomUnusedImage(), imgPerBand, getProperSpin());
//        return b;
//    }
//
//    private SPIN getProperSpin() {
//        if (frameCount % 2 == 0){
//            return SPIN.right;
//        }
//        return SPIN.left;
//    }
//
//    ArrayList<Integer> usedFilenameIndexes = new ArrayList<Integer>();
//    private PImage getRandomUnusedImage(){
//        //reset if all have been used
//        if(usedFilenameIndexes.size() >= imageStore.size()){
//            usedFilenameIndexes.clear();
//        }
//        //randomly find an unused index
//        int randomIndex;
//        do{
//            randomIndex = round(random(0, imageStore.size()-1));
//        }while(usedFilenameIndexes.contains(randomIndex));
//        usedFilenameIndexes.add(randomIndex);
//        return imageStore.get(randomIndex);
//    }
//
//    ArrayList<String> getFilenamesInDirectory(String dir){
//        ArrayList<String> filenames = new ArrayList<String>();
//        File folder = new File(dir);
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles.length; i++) {
//            if (listOfFiles[i].isFile()) {
//                System.out.println("File " + listOfFiles[i].getName());
//                filenames.add(listOfFiles[i].getName());
//            } else if (listOfFiles[i].isDirectory()) {
//                System.out.println("Directory " + listOfFiles[i].getName());
//            }
//        }
//        return filenames;
//    }
//    public enum SPIN {right, left, up};
//
//    public class Band{
//        PImage img;
//        float loc, spd, acc;
//        float rot, rSpd, rAcc;
//        int imgCount;
//        SPIN mode;
//        public void setup(PImage img, int imgCount, SPIN mode){
//            this.img = img;
//            this.imgCount = imgCount;
//            this.spd = startSpd;
//            this.rot = bandsShownSoFar * 20;
//            this.rSpd = startRspd;
//            this.mode = mode;
//        }
//
//        public void applyForce(float acc, float rAcc){
//            this.acc += acc ;
//            this.rAcc += rAcc ;
//        }
//
//
//        public void update(){
//            switch (mode)
//            {
//                case left:{
//                    spd += acc;
//                    rSpd += rAcc;
//                    loc += spd;
//                    rot += rSpd;
//                    break;
//                }
//                case right:{
//                    spd -= acc;
//                    rSpd -= rAcc;
//                    loc -= spd;
//                    rot -= rSpd;
//                    break;
//                }
//            }
//            acc = 0;
//            rAcc = 0;
//        }
//
//        public void draw() {
//            float angleStep = 360 / imgCount;
//            for(int i = 0; i < imgCount; i++){
//                Point coords = getPointAtAngle(
//                        new Point(width/2, height/2),
//                        loc,
//                        rot + angleStep * i
//                );
//                float size = sizeModifier*spd*rSpd;
//                pushMatrix();
//                translate(coords.x,coords.y);
//                rotate(toRad(-90 + getAngle(new Point(coords.x, coords.y),new Point(width/2, height/2))));
//                image(img, -size/2,-size/2, size, size);
//                popMatrix();
//            }
//
//
//        }
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////
//    // LOW LEVEL MATH
//    // //////////////////////////////////////////////////////////////////////////////////////////
//    public float toDegrees(float rad){
//        return (float)Math.toDegrees(rad);
//    }
//
//    public float toRad(float degrees){
//        return (float)Math.toRadians(degrees);
//    }
//    /**
//     * Finds a point in a given angle and distance from a center point.
//     *
//     * @param center center point * @param radius given distance
//     * @param angle  given angle * @return
//     */
//    public Point getPointAtAngle(Point center, float radius, float angle) {
//        return new Point(center.x + radius * cos(angle * PI / 180), center.y + radius * sin(angle * PI / 180));
//    }
//
//    /**
//     * Returns angle of a line vs the horizont *
//     *
//     * @param origin start of the line
//     * @param end    end of the line 🚆
//     * @return horizontal line returns 0, vertical line returns -90 or 90
//     */
//    public float getAngle(Point origin, Point end) {
//        return degrees(atan2(end.y - origin.y, end.x - origin.x));
//    }
//
//    /**
//     * Pythagoras died for our sins
//     * @param a
//     * @param b
//     * @return
//     */
//    public float getDistance(Point a, Point b){
//        float A = b.x - a.x;
//        float B = b.y - a.y;
//        float C = sqrt(A*A + B*B);
//        return C;
//    }
//
//    public class Point {
//        public float x;
//        public float y;
//
//        public Point(float x, float y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
//
//}