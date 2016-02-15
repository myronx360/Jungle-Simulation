/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package junglesimulation;

import BookClasses.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Myron
 */
public class Shelter extends Turtle {
    

    private int size;
    private int resistanceToAttack;
    private boolean hidesScent;
    
    
   public Shelter(int x, int y, ModelDisplay modelDisplayer) {
        super(x, y, modelDisplayer);
        
    }
   
   /**
    * Method that handles how predators attack shelters
    */
   public void resistAttack(Predator predator){
       // when method is called in Jungle class 
       // it will be looping through the predatorsList checking the predator's
       // attackShelterStrength and compare it to this shelter's resistanceToAttack
   }
   
   /**
    * Method to handle how scent is hidden from predators
    */
   public void scent(){
       // get a random number from 0 to 1
       // if random number == 0 then set hidesScent = true
       // else set hidesScent = false
   }
     
  /**
   * Method to paint the shelter 
   * @param g the graphics context to paint on
   */
    @Override
  public synchronized void paintComponent(Graphics g)
  {
    // cast to 2d object (DON'T CHANGE THIS)
    Graphics2D g2 = (Graphics2D) g;
    
    // only bother with this if the shelter object is visible
    if (this.isVisible())
    {
      // save the current tranform (DON'T CHANGE THIS)
      AffineTransform oldTransform = g2.getTransform();
      
      // rotate the shelter and translate to xPos and yPos (DON'T CHANGE THIS)
      g2.rotate(Math.toRadians(this.getHeading()),this.getXPos(),this.getYPos());
      
      // create local variables to use in drawing (you can change/add/remove)
      int factor = this.getSize();
      int width = 10 * factor;
      int height = 20 * factor;
      int halfWidth = (int) (width/2); 
      int halfHeight = (int) (height/2); 
      
      // draw the inside of the shelter
      g2.setColor(Color.green);
      g2.fillRect(this.getXPos() - halfWidth,
                  this.getYPos() - halfHeight, 
                  width, height);
      
      // draw the outline of the shelter
      g2.setColor(Color.BLACK);
      g2.drawRect(this.getXPos() - halfWidth,
                  this.getYPos() - halfHeight, 
                  width, height);
      
      // add a mark near the top, to help visualize orientation
      g2.drawLine(this.getXPos() - halfWidth+2, this.getYPos() - 5, this.getXPos() + halfWidth-2, this.getYPos() - 5);
      
      // reset the tranformation matrix (DON'T CHANGE THIS)
      g2.setTransform(oldTransform);
    }
    
  }
    
    ///////////////////** Getters and Setters **///////////////////////////

    /**
     * Get the value of hidesScent
     *
     * @return the value of hidesScent
     */
    public boolean isHidesScent() {
        return hidesScent;
    }

    /**
     * Set the value of hidesScent
     *
     * @param hidesScent new value of hidesScent
     */
    public void setHidesScent(boolean hidesScent) {
        this.hidesScent = hidesScent;
    }

    

    /**
     * Get the value of resistanceToAttack
     *
     * @return the value of resistanceToAttack
     */
    public int getResistanceToAttack() {
        return resistanceToAttack;
    }

    /**
     * Set the value of resistanceToAttack
     *
     * @param resistanceToAttack new value of resistanceToAttack
     */
    public void setResistanceToAttack(int resistanceToAttack) {
        this.resistanceToAttack = resistanceToAttack;
    }


   


   
    
    
    
 
    
     /**
     * Get the value of size
     *
     * @return the value of size
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the value of size
     *
     * @param size new value of size
     */
    public void setSize(int size) {
        this.size = size;
    }
    
}
