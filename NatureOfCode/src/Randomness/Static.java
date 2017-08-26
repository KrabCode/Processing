package Randomness;

import processing.core.PApplet;

public class Static {

    PApplet p;

    public Static(PApplet p)
    {
        this.p = p;
    }

    public void drawRect(int x0, int y0, int x1, int y1)
    {
        for(int x = x0; x < x1; x++)
        {
            for(int y = y0; y < y1; y++)
            {
                int bright = (int) p.random(255);
                p.stroke(bright);
                p.point(x, y);
            }
        }
    }

    public void drawGrid(int rectPadding, int rectSize)
    {
        for(int x = 0; x < p.width/ rectPadding; x++){
            for(int y = 0; y < p.height/ rectPadding; y++)
            {
                drawRect(x*rectPadding,
                        y*rectPadding,
                        x*rectPadding + rectSize,
                        y*rectPadding + rectSize
                );
            }
        }
    }
}
