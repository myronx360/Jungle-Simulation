/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package junglesimulation;

import BookClasses.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Myron
 */
public class Jungle implements Runnable{

    private int numShelters;
    private int numPredators;
    private int numPreys;
    public static int numDays;
    public static int threadSpeed;
    private World jungle;
    public static ArrayList<Shelter> sheltersList = new ArrayList();
    public static ArrayList<Prey> preyList = new ArrayList();
    public static ArrayList<Predator> predatorsList = new ArrayList();
    public static ArrayList<ArrayList> animalsList = new ArrayList();
    
    //new Jungle(days, threadSpeed, numPrey, numPredator, numShelters);
    public Jungle(int numDays, int threadSpeed, int numPreys, int numPredators, int numShelters) {
        this.numDays = numDays;
        this.threadSpeed = threadSpeed;
        this.numPredators = numPredators;
        this.numPreys = numPreys;
        this.numShelters = numShelters;
        
       
        jungle = new World(800, 600);
        
    }
    @Override
    public void run() {
         initializeJungle();
    }
    

    
    /**
     * Method to add shelters, predators, and preys to the jungle
     * and to let the animals evaluate there situations
     */
    private void initializeJungle(){
        createShelters();
        createPreys();
        createPredators();
        evaluateSituations();
    }
    
    

    
    /**
     * Method to create shelters
     */
    private void createShelters() {
       for (int i = 0; i < numShelters; i++) { 
            // positioning Shelters randomly around the world, 
            // calculate random x and y coords for each new Shelter
            int yPos = (int) (Math.random() * (jungle.getHeight()-20));
            int xPos = (int) (Math.random() * (jungle.getWidth()-20));
            int randomSize = (int) (Math.random() * 3)+1;

            // make a shelter and add to arrayList and 
            // set the size to a random number from 1-3
            sheltersList.add(new Shelter(xPos, yPos, jungle));
            sheltersList.get(i).setSize(randomSize);
        }
    }
    
    /**
     * Method to create preys
     */
    private void createPreys() {
        for (int i = 0; i < numPreys; i++) {
            // positioning prey animals randomly around the world, 
            // calculate random x and y coords for each new prey animal
            int yPos = (int) (Math.random() * (jungle.getHeight()-20));
            int xPos = (int) (Math.random() * (jungle.getWidth()-20));
            int randomPreyAnimal = (int) (Math.random() * 4);
            
            switch(randomPreyAnimal){
                case 0:
                    // make a prey animal and add to arrayList
                    preyList.add(new Prey(xPos, yPos, jungle,Prey.PreyMember.DEER));
                    break;
                case 1:
                    // make a prey animal and add to arrayList
                    preyList.add(new Prey(xPos, yPos, jungle,Prey.PreyMember.MOOSE));
                    break;
                case 2:
                    // make a prey animal and add to arrayList
                    preyList.add(new Prey(xPos, yPos, jungle,Prey.PreyMember.RABBIT));
                    break;
                case 3:
                    // make a prey animal and add to arrayList
                    preyList.add(new Prey(xPos, yPos, jungle,Prey.PreyMember.SHEEP));
                    break;
            }
            
        }
    }

    /**
     * Method to create predators
     */
    private void createPredators() {
        for (int i = 0; i < numPredators; i++) {
            // positioning Predator animals randomly around the world, 
            // calculate random x and y coords for each new Predator animal
            int yPos = (int) (Math.random() * (jungle.getHeight()-20));
            int xPos = (int) (Math.random() * (jungle.getWidth()-20));
            int randomPredatorAnimal = (int) (Math.random() * 3);
            
            switch(randomPredatorAnimal){
                case 0:
                    // make a Predator animal and add to arrayList
                    predatorsList.add(new Predator(xPos, yPos, jungle,Predator.PredatorMember.BEAR));
                    break;
                case 1:
                    // make a Predator animal and add to arrayList
                    predatorsList.add(new Predator(xPos, yPos, jungle,Predator.PredatorMember.LION));
                    break;
                case 2:
                    // make a Predator animal and add to arrayList
                    predatorsList.add(new Predator(xPos, yPos, jungle,Predator.PredatorMember.TIGER));
                 break;
            }
        }
    }
    
    /** 
     * Method that creates a list of animals
     */
    private void createAnimalList(){
        // add the lists of predators and preys to animalsList
    }

    
    /**
     * Method to handle days (rounds)
     */
    private void newDay(){
       // remove dead amimals from arrayList's
        
       // reproduce surving animals by creating new objects
            // loop through the predator and prey arraylists
            // call the reproduce method

       // add them to the world and arrayLists
        
    }
    
    /**
     * Method to start prey and predator actions
     */
    private void evaluateSituations(){
        preyAction();
        predatorAction();
    }
    
    /**
     * Method to start moving prey animals 
     */
    private void preyAction(){
       // loop through the list of shelters and prey animals 
       // to check if it is close to a shelter
        
        for (Prey prey : preyList) {    
           System.out.println();
            for (Shelter shelter : sheltersList) {
                // if true prey animal found a near by shelter
                if(prey.findCloseShelter(shelter)){
                    // checks to see if the pre can fit inside of the shelter
                   prey.foundShelter(shelter, prey.getPrey().getSizeAndAmountOfFoodNeeded());
                   break;
                }
                // checks if the prey animal isn't close to the last shelter
                if (shelter == sheltersList.get(sheltersList.size()-1)){
                    System.out.println("The " + prey.getPrey().getName() 
                                       + " isn't close enough to any shelters");
                    // since the prey isn't near a shelter it moves around ramdomly
                    // to try to get closer to a shelter
                    try {
                        prey.roam();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Jungle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } 
        }
        System.out.println("All prey animals have finished");
    }
    /**
     * Method to start moving predator animals 
     */
    private void predatorAction(){
    // loop through the list of predator animals to see if any are close to a prey
        for (Predator predator : predatorsList) {    
            System.out.println();
            for (Prey prey : preyList) {
                if (predator.findPrey(prey)){
                    prey.beingAttacked();
                    break;
                }
                // checks if the predator animal isn't close to the last prey animal
                if (prey == preyList.get(preyList.size()-1)){
                    System.out.println("The " + predator.getPredator().getName() 
                                       + " isn't close enough to any prey animal");
                    try {
                        // the predator animal roams around to attempt to get closer to prey animals
                        predator.roam();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Jungle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }
        System.out.println("All predators animals have finished");
    }
   
    
    ///////////////////** Getters and Setters **///////////////////////////
    public void incrementNumDay(){
        numDays++;
    }
        
    public void decrementNumDay(){
        numDays--;
    }
    
    /**
     * Get the value of numDays
     *
     * @return the value of numDays
     */
    public int getNumDays() {
        return numDays;
    }

    /**
     * Set the value of numDays
     *
     * @param numDays new value of numDays
     */
    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }


    /**
     * Get the value of numPreys
     *
     * @return the value of numPreys
     */
    public int getNumPreys() {
        return numPreys;
    }

    /**
     * Set the value of numPreys
     *
     * @param numPreys new value of numPreys
     */
    public void setNumPreys(int numPreys) {
        this.numPreys = numPreys;
    }

    

    /**
     * Get the value of numPredators
     *
     * @return the value of numPredators
     */
    public int getNumPredators() {
        return numPredators;
    }

    /**
     * Set the value of numPredators
     *
     * @param numPredators new value of numPredators
     */
    public void setNumPredators(int numPredators) {
        this.numPredators = numPredators;
    }


    /**
     * Get the value of numShelters
     *
     * @return the value of numShelters
     */
    public int getNumShelters() {
        return numShelters;
    }

    /**
     * Set the value of numShelters
     *
     * @param numShelters new value of numShelters
     */
    public void setNumShelters(int numShelters) {
        this.numShelters = numShelters;
    }

    @Override
    public String toString() {
        return "Jungle{" + "numShelters=" + numShelters + ", numPredators=" + numPredators + ", numPreys=" + numPreys + ", jungle=" + jungle + 
                "thred " + threadSpeed+"days "+numDays+'}';
    }



}
