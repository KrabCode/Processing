package Fractals;

import org.apache.commons.math3.complex.Complex;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.*;

public class Mandelbrot {
    private PApplet p;
    Mandelbrot(PApplet p){
        this.p = p;
    }

    public PImage draw(double xc , double yc , double size, int width, int height)
    {
        PImage result = p.createImage(width, height, 1);
        int max = 255;   // maximum number of iterations
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double x0 = xc - size/2 + size*i/width;
                double y0 = yc - size/2 + size*j/height;
                Complex z0 = new Complex(x0, y0);
                int gray = max - mand(z0, max);
                //if(gray*2 > 255) gray = 255/2;
                Color c = new Color(255-gray,255-gray, 255-gray);
                result.set(i, j, c.getRGB());
            }
        }
        return result;
    }

    // return number of iterations to check if c = a + ib is in Mandelbrot set
    private static int mand(Complex z0, int max) {
        Complex z = z0;
        for (int t = 0; t < max; t++) {
            if (z.abs() > 2.0) return t;
            z = z.multiply(z).add(z0);
        }
        return max;
    }


}
