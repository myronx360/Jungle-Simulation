/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package junglesimulation;

import BookClasses.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Myron
 */
public class Predator extends Turtle {
    private PredatorMember predator;
    private boolean isAlive = true;
    private int amountOfFoodEaten;
    private int threadSpeed = Jungle.threadSpeed;
    
     public Predator(int x, int y, ModelDisplay modelDisplayer, PredatorMember predator) {
        super(x, y, modelDisplayer);
        this.predator = predator;
        this.setBodyColor(predator.getColor());
    }
    
     public enum PredatorMember{
        LION("Lion", 2, 1, 3, 200, 100,new Color(252, 221, 21)),//gold
        TIGER("Tiger", 3, 2, 4, 300, 150, Color.ORANGE),
        BEAR("Bear", 4, 3, 5, 400, 200, Color.BLACK);
        
        
        private final String name;
        private final int amountOfFoodNeeded;
        private final int attackShelterStrength;
        private final int maxDaysSurvive;
        private final int maxMoveDistance;
        private final int scentSightSmellDistance;
        private final Color color;
        
        PredatorMember(String name, int amountOfFoodNeeded,int attackShelterStrength, 
                       int maxDaysSurvive,int maxMoveDistance, int scentSightSmellDistance,
                       Color color){
            
            this.name = name;
            this.amountOfFoodNeeded =  amountOfFoodNeeded;
            this.attackShelterStrength = attackShelterStrength;
            this.maxDaysSurvive = maxDaysSurvive;
            this.maxMoveDistance = maxMoveDistance;
            this.scentSightSmellDistance = scentSightSmellDistance;  
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public int getAmountOfFoodNeeded() {
            return amountOfFoodNeeded;
        }

        public int getAttackShelterStrength() {
            return attackShelterStrength;
        }

        public int getMaxDaysSurvive() {
            return maxDaysSurvive;
        }

        public int getMaxMoveDistance() {
            return maxMoveDistance;
        }

        public int getScentSightSmellDistance() {
            return scentSightSmellDistance;
        }  

        public Color getColor() {
            return color;
        }
    }

    public void eat(Prey prey) {
       int foodAmount = prey.getPrey().getAmountOfFoodForPredators();
       this.setAmountOfFoodEaten(this.getAmountOfFoodEaten()+ foodAmount);
       
       // compare the amountOfFoodEaten to setAmountOfFoodNeeded and
       // decide wheather or not to remove this predator from the jungle

    }


    
    public boolean findPrey(Prey prey){
        // checks to see if this predator is close to a prey animal within its
        // sight and smell distance
        boolean closeTo = this.isTurtleClose(prey, this.getPredator().getScentSightSmellDistance());
        boolean canStillMove = true;
        int maxMove = this.getPredator().getMaxMoveDistance();
        int currentAmountOfMove = 0;
        int moveDist = 30;
        int distleft = maxMove - currentAmountOfMove;
        
        while(canStillMove){
            if (closeTo && prey.isIsAlive() && !prey.isSafeInsideShelter()){
                System.out.println("The " + this.getPredator().getName() + " is close to a "+prey.getPrey().getName() +".");
                while(closeTo){
                    if(currentAmountOfMove <= maxMove){
                        // turn torward a prey animal and move toward it
                        this.turnToFace(prey);
                        this.forward(moveDist);
                        try {
                            Thread.sleep(threadSpeed);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Predator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        currentAmountOfMove+=moveDist;
                        canStillMove = true;
                        //checks the distance between the prey animal and predator animal
                        if ((int)(this.getDistance(prey.getXPos(), prey.getYPos()))<= distleft){
                            closeTo = false;
                            canStillMove = false;
                            System.out.println("The " + this.getPredator().getName() + " found a "+prey.getPrey().getName() +".");
                                return true;
                        }
                    }else{// reached maximum move distance
                        // exit outer while loop
                        canStillMove = false;
                        closeTo = false;
                        System.out.println("The " + this.getPredator().getName() + " can no longer move.");
                        return false;
                    }
                    
                }// end of closeTO inner loop
            }else{//Prey isn't close this prey animal or the prey isn't alive
                closeTo = false;
              //  System.out.println("The " + this.getPrey().getName() +" isn't close to this prey");
                return false;
            }
        }// end of canStillMove outer loop
        closeTo = false;
        return false;
    }

    /**
     * This method is called when a predator animal isn't close to any prey animals
     * @throws InterruptedException 
     */
    public void roam() throws InterruptedException {
        System.out.println(this.getPredator().getName() + " is roaming");   
        int maxMove = this.getPredator().getMaxMoveDistance();
        int moveDist = 30;
        int currentAmountOfMove = 0;
        int distleft = maxMove - currentAmountOfMove;
        int boundary = 30;
        boolean foundPrey = false;
        boolean attackAttempted = false;
        // loop while the animal has enough movement distance
        while(currentAmountOfMove <= maxMove){
            
         // if the predator hasn't found the prey while roaming roam some more
        if(!foundPrey){
            // while the predator animal is close to the edge of the world turn around and move background
            while((this.getYPos() >= (this.getModelDisplay().getHeight()) - boundary)||
                  (this.getXPos() >= (this.getModelDisplay().getWidth()) - boundary) ||
                  (this.getYPos() <= boundary)||
                  (this.getXPos() <= boundary)){
                
                  Thread.sleep(threadSpeed);
                  
                  this.turn(100);
                  this.forward((moveDist+boundary));
                  currentAmountOfMove+=boundary;
                  
                  // if the predator runs out of move and is still near 
                  // the edge of the world then break out of while loop
                  if (currentAmountOfMove>=maxMove) {
                    break;
                }
              }

                  // move around ramdomly to get closer to a prey animal
                  this.setHeading(Math.random()*45);
                  Thread.sleep(threadSpeed);
                  this.forward(moveDist);
                  currentAmountOfMove+=moveDist;
            }
        
           // loop through prey aniamls to check if the predator gets close
           // to a prey animal while roaming
            for (int i = 0; i < ((Jungle.preyList.size()-1)); i++) {
              int thresold = this.getPredator().getScentSightSmellDistance();
              Prey prey = Jungle.preyList.get(i);
              
              // if the predator is close to the prey animal...
              if (this.isTurtleClose(prey, thresold) && prey.isIsAlive() && !prey.isSafeInsideShelter()){
                  System.out.println("The "+this.getPredator().getName()+ " close to a " + prey.getPrey().getName());
                    // if predator gets close enough(movement left) to attack then attack
                    if (((int)(this.getDistance(prey.getXPos(), prey.getYPos()))<= distleft)
                       && !attackAttempted && prey.isIsAlive() && !prey.isSafeInsideShelter()){
                        System.out.println("The "+this.getPredator().getName()+ " is attacking a " + prey.getPrey().getName());
                        prey.beingAttacked();
                        foundPrey = true;
                        attackAttempted = true;
                        
                    }
                  // ...move closer while there are moves left
                  while(currentAmountOfMove <= maxMove){
                    Thread.sleep(threadSpeed);
                    this.turnToFace(prey);
                    this.forward(moveDist);
                    currentAmountOfMove+=moveDist;
                    
                    // if predator gets close enough to attack then attack
                    if ((int)(this.getDistance(prey.getXPos(), prey.getYPos()))<= distleft 
                       && !attackAttempted && prey.isIsAlive() && !prey.isSafeInsideShelter()){
                        this.moveTo(prey.getXPos(), prey.getYPos());
                        prey.beingAttacked();
                        foundPrey = true;
                        attackAttempted = true;                        
                    }
                  }
                 foundPrey = true;
              }  
            }
        }
    }
    
    /**
     * Method that returns the type of predator animal
     * @return Predator.PredatorMember
     */
    public Predator.PredatorMember reproduce() { 
       return this.getPredator();
    }

     
     
  /**
   * Method to paint the predator animal 
   * @param g the graphics context to paint on
   */
    @Override
  public synchronized void paintComponent(Graphics g)
  {
    // cast to 2d object (DON'T CHANGE THIS)
    Graphics2D g2 = (Graphics2D) g;
    
    // only bother with this if the predator animal  object is visible
    if (this.isVisible())
    {
      // save the current tranform (DON'T CHANGE THIS)
      AffineTransform oldTransform = g2.getTransform();
      
      // rotate the predator animal  and translate to xPos and yPos (DON'T CHANGE THIS)
      g2.rotate(Math.toRadians(this.getHeading()),this.getXPos(),this.getYPos());
      
      
      // create local variables to use in drawing (you can change/add/remove)
      int factor = this.getPredator().getAttackShelterStrength();
      int width = 25*factor;
      int height = 30*factor;
      int halfWidth = (int) (width/2); 
      int halfHeight = (int) (height/2); 
      int[] triX = {this.getXPos(),this.getXPos()+halfWidth,this.getXPos(),this.getXPos()};
      int[] triY = {this.getYPos(), this.getYPos(), this.getYPos() - halfHeight,this.getYPos()};
     
      
      // draw the ouside of the predator animal 
      g2.setColor(Color.BLACK);
      g2.drawPolygon(triX,triY ,4 );
      
      // draw the inside of the predator animal 
      g2.setColor(this.getBodyColor());
      g2.fillPolygon(triX,triY ,4 );
      
                       

      
      // reset the tranformation matrix (DON'T CHANGE THIS)
      g2.setTransform(oldTransform);
    }
    
  }
     ///////////////////** Getters and Setters **///////////////////////////
    public PredatorMember getPredator() {
        return predator;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getAmountOfFoodEaten() {
        return amountOfFoodEaten;
    }

    public void setAmountOfFoodEaten(int amountOfFoodEaten) {
        this.amountOfFoodEaten = amountOfFoodEaten;
    }
  
}
