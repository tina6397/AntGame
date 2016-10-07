package com.model;

/**
 * Created by Tina on 3/23/2015.
 */
public class Ant {

    public int ID ;
    public Colour colour ;
    public  int state ;
    public int resting ;
    public int direction;
    public boolean hasFood;
    public Position position;


    /**
     * This is the constructor of the AntImpl Class
     * @param ID
     * @param colour
     * @param position
     */
    public Ant(int ID, Colour colour, Position position){
        this.ID = ID ;
        this. colour = colour ;
        this.position = position ;

        direction = 0 ;
        resting =0;
        state = 0;
        hasFood = false ;
    }

    /**
     * This method returns the position of an Ant
     * @return position
     */
    public Position getPosition(){
        return position;
    }

    /**
     * This method set the position of an Ant
     * @param position
     */
    public void setPosition(Position position){
        this.position = position ;
    }

    /**
     * this method returns the ID of the ant
     * @return int, the ID of the ant
     */
    public int getID() {
        return ID;
    }

    /**
     * this method set the ID of the ant
     * @param id int
     */
    public void setID(int id) {
        ID = id ;
    }

    /**
     * This method gets the current direction that the ant is facing
     * @return int representing current direction between 0 and 5 (inclusive)
     */
    public int getDirection() {
        return direction;
    }

    /**
     * This method sets the direction that the ant is facing
     * @param direction The direction to set the ant as facing towards, between 0 and 5 (inclusive)
     */
    public void setDirection(int direction) {
        if (direction>=0 && direction<6){
            this.direction = direction ;
        }
    }

    /**
     * This method gets the current brain state that the ant is in, used by the Brain to compute the ant's next move
     * @return int representing the current brain state
     */
    public int getState() {
        return state ;
    }

    /**
     * This method set the current brain state of the ant, invoked by the brain in order to update the ant's
     * behaviour for the next turn
     * @param state an int representing the current state of the brain
     */
    public void setState(int state) {
        if (state <= 9999 && state >=0){
            this.state = state ;
        }

    }

    /**
     * This method sets the colour of an Ant
     * @param colour
     */
    public void setColour(Colour colour) {
        this.colour = colour ;
    }


    /**
     * This method returns the Colour of an Ant
     * @return colour
     */
    public Colour getColour()
    {

        return colour ;
    }



    /**
     * This method returns whether the ant is resting. After an ant performs an action, it rests for 14 turns.
     * @return true if ant is resting, false otherwise
     */
    public boolean isResting() {

        boolean rest = false;
        if(resting>0&& resting<15){
            return rest =true;
        }

        return rest;

    }

    /**
     * This method sets rest to 1. Which begins the rest period which lasts 14 turns.
     */
    public void startResting() {
        resting = 1 ;
    }

    /**
     * This method returns the amount of rest that the ant still requires before its next move. An ant rests for 14 turns
     * @return int representing number of turns that the ant still must rest for before its next movement
     */
    public int getRemainingRest() {
        return (15-resting) ;
    }

    /**
     * This method increments the ant's rest counter by 1. The counter ranges from 0-14, looping back after it has reached it's max
     * range. An ant can only perform a movement on turn 0.
     */
    public void incrementRest() {
        if (resting < 15){
            resting ++;
        }
        if (resting == 15){
            resting = 0;
        }

    }



    /**
     * This method returns whether the ant is currently carrying food
     * @return Boolean hasFood, true if carrying food, false otherwise.
     */
    public boolean hasFood() {
        return hasFood;
    }

    /**
     * This method sets if an Ant have food
     * @param Boolean food
     */
    public void setHasFood(Boolean food) {
        hasFood = food ;
    }


    /**
     * This method compares if the Ant input and the current Ant object has the same state
     * @param o, the object to compare with
     * @return true if all the states are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ant ant = (Ant) o;

        if (ID != ant.ID) return false;
        if (direction != ant.direction) return false;
        if (hasFood != ant.hasFood) return false;
        if (resting != ant.resting) return false;
        if (state != ant.state) return false;
        if (colour != ant.colour) return false;
        if (position != null ? !position.equals(ant.position) : ant.position != null) return false;

        return true;
    }

    /**
     * This method returns the summary of the Ant states
     * @return
     */
    @Override
    public int hashCode() {
        int result = ID;
        result = 31 * result + (colour != null ? colour.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + resting;
        result = 31 * result + direction;
        result = 31 * result + (hasFood ? 1 : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}






