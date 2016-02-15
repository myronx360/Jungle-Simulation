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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Myron
 */
public class Prey extends Turtle{
    private PreyMember prey;
    private boolean isAlive = true;
    private boolean safeInsideShelter = false;
    private int threadSpeed = Jungle.threadSpeed;
    
    public Prey(int x, int y, ModelDisplay modelDisplayer, PreyMember prey) {
        super(x, y, modelDisplayer);
        this.prey = prey;
        this.setBodyColor(prey.getColor());
    }

  
    
    
     public enum PreyMember{
        RABBIT("Rabbit", 1, 1, 10, 2, 50, 10,new Color(195, 187, 180)),//grey
        SHEEP("Sheep", 2, 2, 20, 2, 150, 20,Color.DARK_GRAY),
        DEER("Deer", 3, 3, 40, 2, 200, 40,new Color(102,51,0)),//Brown
        MOOSE("Moose", 4, 4, 50, 2, 250, 40,Color.BLACK);
        
        private final String name;
        private final int sizeAndAmountOfFoodNeeded;
        private final int amountOfFoodForPredators;
        private final int chanceOfSurvivingAttack; 
        private final int maxDaysSurvive;
        private final int maxMoveDistance;
        private final int scentSightSmellDistance;
        private final Color color;
        
        
        PreyMember(String name, int sizeAndAmountOfFoodNeeded,int amountOfFoodForPredators,
                   int chanceOfSurvivingAttack, int maxDaysSurvive, 
                   int maxMoveDistance, int scentSightSmellDistance, Color color){
            
            this.name = name;
            this.sizeAndAmountOfFoodNeeded = sizeAndAmountOfFoodNeeded;
            this.amountOfFoodForPredators = amountOfFoodForPredators;
            this.chanceOfSurvivingAttack = chanceOfSurvivingAttack;
            this.maxDaysSurvive = maxDaysSurvive;
            this.maxMoveDistance = maxMoveDistance;
            this.scentSightSmellDistance = scentSightSmellDistance;
            this.color = color;
        }

        public String getName() {
            return name;
        }
        public int getSizeAndAmountOfFoodNeeded() {
            return sizeAndAmountOfFoodNeeded;
        }

        public int getAmountOfFoodForPredators() {
            return amountOfFoodForPredators;
        }

        public int getChanceOfSurvivingAttack() {
            return chanceOfSurvivingAttack;
        }

        public int getScentSightSmellDistance() {
            return scentSightSmellDistance;
        }


        public int getMaxMoveDistance() {
            return maxMoveDistance;
        }

        public int getMaxDaysSurvive() {
            return maxDaysSurvive;
        }   

        public Color getColor() {
            return color;
        }

}// End of enum

    public void eat() {
      // compares sizeAndAmountOfFoodNeeded to amount of food found
      // if the prey animal ate enoungh it lives
      //  else
      //    isAlive = false;
    }

    
    public boolean findCloseShelter(Shelter shelter) {
         // checks to see if this predator is close to a prey animal within its
        // sight and smell 
        boolean closeTo = this.isTurtleClose(shelter, prey.getMaxMoveDistance());
        boolean canStillMove = true;
        int maxMove = this.prey.getMaxMoveDistance();
        int currentAmountOfMove = 0;
        int moveDist = 10;
        while(canStillMove){
            if (closeTo){
                System.out.println("The " + this.getPrey().getName() + " is close to a shelter.");
                while(closeTo){
                    if(currentAmountOfMove <= maxMove){
                        // turn torward a shelter and move toward it
                        this.turnToFace(shelter);
                        this.forward(moveDist);
                        try {
                            Thread.sleep(threadSpeed);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Prey.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        currentAmountOfMove+=moveDist;
                        canStillMove = true;
                        //checks the distance between the prey animal and shelter
                        if ((int)(this.getDistance(shelter.getXPos(), shelter.getYPos()))<= moveDist){
                            closeTo = false;
                            canStillMove = false;
                            System.out.println("The " + this.getPrey().getName() + " arrived at a shelter.");
                                return true;
                        }
                    }else{// reached maximum move distance
                        // exit outer while loop
                        canStillMove = false;
                        closeTo = false;
                        System.out.println("The " + this.getPrey().getName() + " can no longer move.");
                        return false;
                    }
                    
                }// end of closeTO inner loop
            }else{//Prey isn't close this shelter
                closeTo = false;
              //  System.out.println("The " + this.getPrey().getName() +" isn't close to this shelter");
                return false;
            }
        }// end of canStillMove outer loop
       // canStillMove = false;
        closeTo = false;
        return false;
    }
 
    /**
     * This method is called when a predator animal isn't close to any prey animals
     * @throws InterruptedException 
     */    
    public void roam() throws InterruptedException {
        System.out.println(this.getPrey().getName() + " is roaming");   
        int maxMove = this.getPrey().getMaxMoveDistance();
        int moveDist = 30;
        int currentAmountOfMove = 0;       
        int boundary = 20;
        boolean foundShelter = false;
        
        // loop while the animal has enough movement distance
        while(currentAmountOfMove <= maxMove){
            
         // if the predator hasn't found the prey while roaming roam some more
        if(!foundShelter){
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
           // loop through shelters to check if the prey gets close
           // to a shelter while roaming
            for (int i = 0; i < Jungle.sheltersList.size()-1; i++) {
              int thresold = this.getPrey().getScentSightSmellDistance();
              Shelter shelter = Jungle.sheltersList.get(i);
              int dist = maxMove - currentAmountOfMove;
              if (this.isTurtleClose(shelter, thresold)){
                 this.turnToFace(shelter);
                 this.forward(dist);
                 currentAmountOfMove+=dist;
                 foundShelter = true;
              }  
            }
        }
    }
    
    /**
     * Method that returns the type of prey animal
     * @return Prey.PreyMember
     */
    public Prey.PreyMember reproduce() {
        return this.getPrey();
    }
    
    /**
     * This method is called in the Jungle class while looping through the
     * predator animals and checking the distance(scentSightSmellDistance) 
     * between this prey animal and a predator animal.
     * @param predator
     */
    public void seeOrSmellPredator(Predator predator){
        //if the prey animal is close to a preadtor while roaming
        // it move away from the predator while i still has move distance left
    }
    
    /**
     * Method called when a prey animal is being attacked
     * @return isKilled (boolean)
     */
    public boolean beingAttacked(){
        boolean isKilled;
        
        int survivingChance = (int)(Math.random()*(this.getPrey().getChanceOfSurvivingAttack()));
       
        // if the prey animal is a moose then it has a chance to fight back
        if (this.getPrey()== this.getPrey().MOOSE){
            
        }
        // prey didn't survive attack
        if (survivingChance%2 == 0){
            isKilled = true;
            this.setIsAlive(false);
            this.setVisible(false);
            System.out.println("The " + this.getPrey().getName() + " didn't survive attack");
           
        }else{// prey did survive attack
            isKilled = false;
            System.out.println("The " + this.getPrey().getName() + " survived attack");
        }
        return isKilled;
    }
    /**
     * Method is called once a prey animal successfully locates a shelter
     * and determines if the prey animal fits inside the shelter
     * @param shelter
     * @param sizeOfPrey
     * @return whether or not the prey animal fits inside the shelter
     */
    public boolean foundShelter(Shelter shelter, int sizeOfPrey){
        int shelterSize = shelter.getSize();
        if (sizeOfPrey <= shelterSize) {
            System.out.println("The " + this.getPrey().getName() + " fits inside shelter");
            this.setSafeInsideShelter(true); 
            this.setVisible(false);
            return true;
        }else{
            System.out.println("The " + this.getPrey().getName() + " doesn't fit inside shelter");
            this.setSafeInsideShelter(false);
            this.setVisible(true);
            return false;
        }
    }
    

    
  /**
   * Method to paint the prey Animals 
   * @param g the graphics context to paint on
   */
    @Override
  public synchronized void paintComponent(Graphics g)
  {
    // cast to 2d object (DON'T CHANGE THIS)
    Graphics2D g2 = (Graphics2D) g;
    
    // only bother with this if the VehicleTurtle object is visible
    if (this.isVisible())
    {
      // save the current tranform (DON'T CHANGE THIS)
      AffineTransform oldTransform = g2.getTransform();
      
      // rotate the turtle and translate to xPos and yPos (DON'T CHANGE THIS)
      g2.rotate(Math.toRadians(this.getHeading()),this.getXPos(),this.getYPos());
      
      // create local variables to use in drawing (you can change/add/remove)
      int factor = this.getPrey().getSizeAndAmountOfFoodNeeded();
      int width = 15 * factor;
      int height = 20 * factor;
      int halfWidth = (int) (width/2); 
      int halfHeight = (int) (height/2); 
      
      // draw the body
      g2.setColor(this.getBodyColor());
      g2.fillOval(this.getXPos() - halfWidth,
                  this.getYPos() - halfHeight, width/2, height/2);
      
    

      
     
      // reset the tranformation matrix (DON'T CHANGE THIS)
      g2.setTransform(oldTransform);
    }
  }
  
   ///////////////////** Getters and Setters **///////////////////////////
    public PreyMember getPrey() {
        return prey;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isSafeInsideShelter() {
        return safeInsideShelter;
    }

    public void setSafeInsideShelter(boolean safeInsideShelter) {
        this.safeInsideShelter = safeInsideShelter;
    }
    
}

