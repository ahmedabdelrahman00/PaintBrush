/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaintPCK;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Ahmed Abdelrahman
 */
public abstract class Shape extends JPanel {
     private int x1,y1,x2,y2;

    private Color currentColor;
    private boolean fillState,lineStyle,eraseState;

  
    public Shape(){
        
    }
    
  
    public Shape(int x1,int y1, int x2, int y2,boolean dash,boolean fill, Color color, boolean erase){
    this.x1=x1;
    this.y1=y1;
    this.x2=x2;
    this.y2=y2;
    this.currentColor=color;
    this.lineStyle=dash;
    this.fillState=fill;
    this.eraseState=erase;
}
//--------------------------------------------------------
    public void setX1(int x1){
        this.x1=x1;
    }
    public void setY1(int y1){
        this.y1=y1;
    }
    public void setX2(int x2){
        this.x2=x2;
    }
    public void setY2(int y2){
        this.y2=y2;
    }
//--------------------------------------------------------
    public int getX1(){
        return x1;
    }
    public int getY1(){
        return y1;
    }
    public int getX2(){
        return x2;
    }
    public int getY2(){
        return y2;
    }
//--------------------------------------------------------
    public void setCurrentColor(Color color){
        this.currentColor=color;
    }
    
     public Color getCurrentColor(){
        return currentColor;
    }
//--------------------------------------------------
     public void setDash (boolean dashLine){
         this.lineStyle=dashLine;
     }
    
   public  boolean getDash(){
        return lineStyle ;
    }
//--------------------------------------------------
   public void setFill (boolean fillShape){
         this.fillState=fillShape;
     }
    
   public  boolean getFill(){
        return fillState ;
    }
   public void setErase (boolean eraseShape){
         this.eraseState=eraseShape;
     }
    
   public  boolean getErase(){
        return eraseState ;
    }
//--------------------------------------------------
    public abstract void drawShape(Graphics2D g2d);
    
}
