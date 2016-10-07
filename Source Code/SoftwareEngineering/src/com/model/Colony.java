package com.model;

import com.model.exceptions.AntNotFoundException;

import java.util.HashMap;

/**
 * Created by Tina on 3/23/2015.
 */
public class Colony {

    Colour colour ;
    HashMap<Integer,Ant> antList ;
    Brain brain ;
    int food ;
    int maxAnts ;

    /**
     * The constructor of the ColonyImpl Class
     * which takes a Colour that represents the colour of the Colony
     *
     * @param colour
     */
    public Colony(Colour colour){

        this.colour = colour ;

        food = 0 ;
        maxAnts = 49 ;
        antList = new HashMap<Integer,Ant>();

    }

     /**
     * Get an Ant object from the list of Ants stored in the given colony.
      *
     * @param pos The cell position of the ant to be found
     * @return The Ant at indicated position
     */
    public Ant getAnt(Position pos) throws AntNotFoundException {

        Ant result = null ;

        //search for every ant in ant list
        for (Integer key : antList.keySet()){
            Ant ant = antList.get(key);
            if (ant.getPosition() == pos){
                result = ant ;
                break;
            }
        }

        //if not found, throw exception, otherwise return the ant found
            if (result == null){
                throw new AntNotFoundException("*** Ant not found ***");
            }else {
                return result;
            }
    }

    /**
     * Get an Ant object from the list of Ants stored in the given colony.
     *
     * @param id the id of the ant to be found
     * @return The Ant object with given id
     */
    public Ant getAnt(int id) throws AntNotFoundException {
        boolean found = antList.containsKey(id) ;

        // if id not found , throw exception, otherwise return ant
        if (!found){
            throw new AntNotFoundException("*** Ant not found ***");
        }else{
            return antList.get(id) ;
        }
    }

    /**
     * Get the number of ants currently alive belonging to the colony
     *
     * @return Number of ants still alive
     */
    public int getNumberOfAnts() {
        return antList.size();
    }

    /**
     * When given an ant id, will return true if specified ant is alive
     *
     * @param id ant id to check whether alive
     * @return true if ant alive, false otherwise
     */
    public boolean isAntAlive(int id) {
        return antList.containsKey(id) ;
    }

    /**
     * Get the total amount of food held by the colony. This does not include food held by individual ants
     * belonging to the colony.
     *
     * @return Amount of food in colony
     */
    public int getFoodInColony() {
        return food;
    }

    /**
     * Increments the total amount of food belonging to the colony by 1.
     * Invoked when an ant returns to the colony with food as it can only carry one particle at a time
     */
    public void incrementFood() {
        food ++ ;

    }

    /**
     * Load the colony with the given brain. This controls the behaviour of every ant in the colony.
     * The brain is fundamental to the operation of the colony and this method must be invoked before the game begins
     *
     * @param brain
     */
    public void setBrain(Brain brain) {
        this.brain = brain ;
    }


    /**
     * Returns the brain which has been loaded into the colony
     *
     * @return the colony brain
     */
    public Brain getBrain() {
        return brain ;

    }

    /**
     * Returns the colony colour, allowing differentiation between the two competing teams
     *
     * @return RED or BLACK
     */
    public Colour getColonyColour() {
        return colour ;
    }

    /**
     * This method set the colony colour
     *
     * @param colour
     */
    public void setColonyColour(Colour colour) {

    }

    /**
     * Removes ant belonging to this objects colony by removing it from the list of ants.
     * When an ant is killed it turns into 3 particles of food.
     * This method is invoked from the killEnemyAnt method in an Ant object
     *
     * @param id position of the ant to be killed
     */
    public void remove(int id) throws AntNotFoundException {

        antList.remove(id);

    }

    /**
     * This method add an Ant into the ant list
     *
     * @param ant, that will be added into the list
     */
    public void addAnt(Ant ant) {
        antList.put(ant.getID(), ant) ;
    }

    /**
     * This method resets all the states of an Ant
     */
    public void reset() {
        food = 0;
        antList.clear();
        brain = null ;
    }
}
