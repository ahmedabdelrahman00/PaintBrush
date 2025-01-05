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
public class Oval extends Shape {
    private int x,y,height,width;
//-----------------------------------------------------------
    public Oval(){
        
    }

    public Oval(int x1, int y1, int x2, int y2,boolean dash,boolean fill , Color c,boolean erase)
    {
        super(x1, y1, x2, y2, dash, fill,c,erase);
    }
//-----------------------------------------------------------
    @Override
    public void drawShape(Graphics2D g2d) {
       
        // Specify the color of the shape
        g2d.setColor(getCurrentColor());
        
       //modify the coordinates of the shape
        x = Math.min(getX1(), getX2());
        y = Math.min(getY1(), getY2());
        width = Math.abs(getX1() - getX2());
        height = Math.abs(getY1() - getY2());
        
        //Specify whether the shape is filled or not and if not specify whether the shape is dashed or not
        if (getFill() == true) {
            g2d.fillOval(x, y, width, height);
        } else {
            if (getDash() == true) {
                Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                g2d.setStroke(dashed);
            } else {
                g2d.setStroke(new BasicStroke(2));
            }
            g2d.drawOval(x, y, width, height);
        }
    }   
    
    
    
}
