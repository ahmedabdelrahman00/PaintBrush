/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaintPCK;

import java.awt.*;
import static java.awt.Color.*;
/**
 *
 * @author Ahmed Abdelrahman
 */
public class Line extends Shape {
    private int x1,y1,x2,y2;
//--------------------------------------------------------
    public Line(){
        
    }

    public Line(int x1, int y1, int x2, int y2,boolean dash,boolean fill , Color c,boolean erase) {
        super(x1, y1, x2, y2, dash, fill, c, erase);
    }
//--------------------------------------------------------
    @Override
    public void drawShape(Graphics2D g2d)
    {
        //Setting color of the line
         g2d.setColor(getCurrentColor());
        
        //Identify the line's coordenations
        x1=getX1();
        y1=getY1();
        x2=getX2();
        y2=getY2();
        //Identify the line's state whether it's solidLine or dashedLine
        if (getDash() == true) {
            Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
        } else {
            g2d.setStroke(new BasicStroke(2));
        }
        g2d.drawLine(x1, y1, x2, y2);
    }
    
}
