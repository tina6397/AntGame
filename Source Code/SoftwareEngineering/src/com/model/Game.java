
package com.model;
import com.model.exceptions.CellAlreadyOccupiedException;
import com.view.GameGUI;

import java.io.File;
import java.util.List;

public class Game {

    Colony red;
    Colony black;
    Map map = new Map();
    int redFood = 0;
    int blackFood = 0;
    int currentRound = 0;
    String redPlayerName;
    String blackPlayerName;
    GameGUI gui;

    /**
     *Create a new game object with the two brain files with the names of the players which the brain files belong to and the GUI object in which it will be played in
     * @param brain1
     * @param name1
     * @param brain2
     * @param name2
     * @param gui
     */
    public Game(File brain1, String name1, File brain2, String name2, GameGUI gui){

        red = new Colony(Colour.RED);
        black = new Colony(Colour.BLACK);
        loadBrain(brain1,Colour.RED);
        loadBrain(brain2, Colour.BLACK);
        redPlayerName = name1;
        blackPlayerName = name2;
        this.gui = gui;
        map.generateMap();
    }






    /**
     * Getting the GUI object
     * @return A GUI object
     */
    public GameGUI getGUI(){ return gui;}

    public void start() {

    }

    /**
     * Get the name of the black colony player
     * @return A string which represents the black colony player name
     */
    public String getBlackPlayerName() {
        return blackPlayerName;
    }

    /**
     * Get the name of the red colony player
     * @return A string which represents the red colony player name
     */
    public String getRedPlayerName() {
        return redPlayerName;
    }


    /**
     * Checks if the brain is syntactically correct and setting the brain to its correct colony
     * @param brain
     * @param colour
     * @return A boolean showing if the brain is syntactically correct
     */
    public boolean loadBrain(File brain, Colour colour) {



        Brain brainClass;
        boolean passed = true;

        if(colour == Colour.RED){
            brainClass = new Brain(map,red);
            passed = brainClass.loadBrain(brain);
            if(passed){
                red.setBrain(brainClass);
            }
        }
        else{
            brainClass = new Brain(map,black);
            passed = brainClass.loadBrain(brain);
            if(passed){
                black.setBrain(brainClass);
            }
        }

        return passed;
    }

    /**
     * Get a colony of a specific colour
     * @param colour
     * @return  A colony object of a specific object
     */
    public Colony getColony(Colour colour) {
        if(colour==Colour.RED){
            return red;
        }
        else{
            return black;
        }
    }


    /**
     * Get a brain from a specific colony
     * @param colour
     * @return A brain object from a specific colony
     */
    public File getBrain(Colour colour) {
        if(colour==Colour.RED){
            return red.getBrain().getLoadedFile();
        }
        else{
            return black.getBrain().getLoadedFile();
        }
    }

    /**
     * Get the current round the game is in
     * @return A integer representing the current round that the game is in
     */
    public int getCurrentRound(){
        return currentRound;
    }

    /**
     * Get the map the game is being played on
     * @return A map object which the game is being played on
     */
    public Map getMap() {
        return map;
    }

    private void generateAnts(){


        List<Position> redHill = map.getAntHill(Colour.RED);
        List<Position> blackHill = map.getAntHill(Colour.BLACK);



        int id = 0;


        for(Position p : redHill){


           red.addAnt(new Ant(id,Colour.RED,p));

            Ant ant = new Ant(id,Colour.RED,p);
            red.addAnt(ant);
            try {
                map.setAntAtCell(p,ant);
            } catch (CellAlreadyOccupiedException e) {
                e.printStackTrace();
            }

            id++;
        }

        for(Position p : blackHill){
            Ant ant = new Ant(id,Colour.BLACK,p);
            black.addAnt(ant);
            try {
                map.setAntAtCell(p,ant);
            } catch (CellAlreadyOccupiedException e) {
                e.printStackTrace();
            }
            id++;
        }
    }

    public void setup(){
        currentRound = 0;
       // map.clearMap();

        generateAnts();

    }



    /**
     * Running all the ants through the step method in the brain to execute the next instruction
     */
    public void nextRound() {
        for(int i=0;i<127;i++){
            red.getBrain().step(i);
        }
        for (int i=127;i<254;i++){
            black.getBrain().step(i);
        }
        currentRound++;
    }


    /**
     * R = Red win
     * B = Black win
     * D = Draw
     * @return R, B or D, depending on the outcome of the game.
     */
    public Colour getWinner() {
        if(red.getFoodInColony()>black.getFoodInColony()){
            return Colour.RED;
        }
        else if(black.getFoodInColony()>red.getFoodInColony()){
            return Colour.BLACK;
        }
        else
            return null;
    }

    public String getWinnerName(){
        if (getWinner()==Colour.BLACK){
            return  getBlackPlayerName();
        }else{
            return getRedPlayerName();
        }
    }


    /**
     * Checks if the custom map loaded in is syntactically correct or not
     * @param map
     * @return A boolean checking if a custom map is syntactically correct or not
     */
    public boolean loadMap(File map){
        System.out.println("1");
        return getMap().loadMap(map);

    }


}
