/**
 * Created by hao-linliang on 17/03/15.
 */

import com.model.*;
import com.model.exceptions.AntNotFoundException;
import com.model.exceptions.CellAlreadyOccupiedException;
import com.model.exceptions.InvalidContentCharacterException;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class BrainTest {


    /**
     * Testing that the instruction tokens  are seen as correct tokens
     *
     */
    @Test
    public void testInstructionToken(){
        Colony c = new Colony(null);
        Map m = new Map();
        Brain b = new Brain(m,c);

        assertTrue(b.loadBrain(new File("brains/file.txt")));
    }

    /**
     * Testing that all direction tokens are  seen as correct tokens
     *
     */
    @Test
    public void testDirectionToken(){

        Colony c = new Colony(null);
        Map m = new Map();

        Brain b2 = new Brain(m,c);

        assertTrue(b2.loadBrain(new File("brains/file1.txt")));

    }


    /**
     *
     * Testing that all condition tokens are seen as correct tokens
     *
     */

    @Test
    public void testConditionsToken(){

        Colony c = new Colony(null);
        Map m = new Map();

        Brain b3 = new Brain(m,c);

        assertTrue(b3.loadBrain(new File("brains/file2.txt")));

    }

    /**
     * Testing if all argument tokens are seen as correct tokens
     */
    @Test
    public void testArgumentTokens(){

        Colony c = new Colony(null);
        Map m = new Map();

        Brain b4 = new Brain(m,c);

        assertTrue(b4.loadBrain(new File("brains/file3.txt")));
    }

    /**
     * Test a mix of all correct tokens
      */

    @Test
    public void testMixOfTokens(){

        Colony c = new Colony(null);
        Map m = new Map();

        Brain b5 = new Brain(m,c);

        assertTrue(b5.loadBrain(new File("brains/file4.txt")));


    }

    /**
     * Testing a mix of incorrect tokens
     *
     */
    @Test
    public void testFailTokens(){

        Colony c = new Colony(null);
        Map m = new Map();

        Brain b6 = new Brain(m,c);
        Brain b7 = new Brain(m,c);
        Brain b8 = new Brain(m,c);
        Brain b9 = new Brain(m,c);
        Brain b10 = new Brain(m,c);
        Brain b11= new Brain(m,c);
        Brain b12 = new Brain(m,c);
        Brain b13 = new Brain(m,c);

        assertFalse(b6.loadBrain(new File("brains/file5.txt")));

        assertFalse(b7.loadBrain(new File("brains/file6.txt")));

        assertFalse(b8.loadBrain(new File("brains/file7.txt")));

        assertFalse(b9.loadBrain(new File("brains/file8.txt")));

        assertFalse(b10.loadBrain(new File("brains/file10.txt")));

        assertFalse(b11.loadBrain(new File("brains/file11.txt")));

        assertFalse(b12.loadBrain(new File("brains/file12.txt")));

        assertFalse(b13.loadBrain(new File("brains/file9.txt"))); // need to check why this fails
    }

    /**
     * Testing a mix of correct and incorrect tokens together
     */
    @Test
    public void testCorrectWrongTokens(){

        Colony c = new Colony(null);
        Map m = new Map();

        Brain b7 = new Brain(m,c);

        assertFalse(b7.loadBrain(new File("brains/file13.txt")));

        assertFalse(b7.loadBrain(new File("brains/file14.txt")));

        assertFalse(b7.loadBrain(new File("brains/file15.txt")));
    }

    /**
     *
     * Testing if an ant rests for the correct amount of time
     */
    @Test
    public void testStepResting(){


        Colony c = new Colony(Colour.BLACK);
        Map m = new Map();
        Brain b8 = new Brain(m,c);
        Position p = new Position(1,1);
        Ant a = new Ant(1,Colour.BLACK,p);

        m.generateMap();

        c.addAnt(a);

        a.startResting();

        assertTrue(a.isResting());

        assertEquals(14,a.getRemainingRest());

        b8.step(a.getID()); // should ant store its ID too

        assertEquals(13, a.getRemainingRest());

        b8.step(a.getID());

        assertEquals(12, a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(11,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(10,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(9,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(8,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(7,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(6,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(5,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(4,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(3,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(2,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(1,a.getRemainingRest());

        b8.step(a.getID());
        assertEquals(15,a.getRemainingRest());

        assertFalse(a.isResting());


    }


    /**
     * Tests if the sensing of an ant works
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testSenseTrue() throws CellAlreadyOccupiedException { /// breaks

        Map map = new Map();
        Colony c = new Colony(Colour.RED);
        Brain b9 = new Brain(map, c);

        map.generateMap();
        Position pos = new Position(1,1);
        Position pos1  = new Position(2,1);


        Ant a1 = new Ant(1,Colour.RED,pos);
        Ant a2 = new Ant(2,Colour.RED,pos1);

        c.addAnt(a1);
        c.addAnt(a2);


        a1.setPosition(pos);

        map.setAntAtCell(pos , a1);
        map.setAntAtCell(pos1, a2);

        b9.loadBrain(new File("brains/file16.txt"));

        int state = 1;

        b9.step(a1.getID());

        // checks if its taken the correct state
        assertEquals(state, a1.getState());

        b9.step(a1.getID());

        assertEquals(0,a1.getState());



    }

    /**
     *
     * Testing if an ant can mark a cell
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testMark() throws CellAlreadyOccupiedException {

        Map m = new Map();

        Colony c= new Colony(Colour.RED);

        Brain b10 = new Brain(m,c);

        m.generateMap();
        Position p = new Position(1,1);

        Ant a3 = new Ant(2,Colour.RED,p);

        c.addAnt(a3);


        m.setAntAtCell(p ,a3);
        // set state of ant before hand?
        b10.loadBrain(new File("brains/file17.txt"));



        // Cell c = new Cell();



        // m.setCellScentMarker(p,1);
        b10.step(a3.getID());
        System.out.println(m.getCellScentMarker((Colour.RED) , p));
        assertEquals(1, m.getCellScentMarker(Colour.RED, p));



    }

    /**
     * Testing if an ant can unmark a cell of same colour and number
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testUnMark() throws CellAlreadyOccupiedException {

        Map m1 = new Map();
        Colony c = new Colony(Colour.BLACK);
        Brain b11 = new Brain(m1,c);
        Position p1 = new Position(1,1);
        Ant a4 = new Ant(2,Colour.BLACK,p1);




        m1.generateMap();

        c.addAnt(a4);


        m1.setAntAtCell(p1, a4);


        b11.loadBrain(new File("brains/file18.txt"));

        m1.setCellScentMarker(p1,7);


        b11.step(a4.getID());

        assertEquals(1, a4.getState());


        assertEquals(0, m1.getCellScentMarker(Colour.BLACK, p1));




    }

    /**
     * Testing if an ant can unmark a scent of a different colour ant
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testCantUnMarkOtherColour() throws CellAlreadyOccupiedException {


        Map m2 = new Map();
        Colony c = new Colony(Colour.BLACK);

        Brain b12 = new Brain(m2,c);
        m2.generateMap();
        Position p2 = new Position(1,1);


        Ant a6 = new Ant(3,Colour.BLACK,p2);



        a6.setColour(Colour.BLACK);

        a6.setID(3);

        a6.setPosition(p2);


        c.addAnt(a6);


        m2.setAntAtCell(p2, a6);

        b12.loadBrain(new File("brains/file19.txt"));

        m2.setCellScentMarker(p2,1);

        b12.step(a6.getID());

        assertEquals(-1,m2.getCellScentMarker(Colour.RED,p2)); // returns -1 as it shows that it cannot unmark it
        assertEquals(1,a6.getState());



    }

    /**
     * Tests if an ant can pickup food
     * @throws InvalidContentCharacterException
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testPickUpFood() throws InvalidContentCharacterException, CellAlreadyOccupiedException {


        Map m3 = new Map();
        Colony c = new Colony(Colour.RED);

        Brain b13 = new Brain(m3,c);
        Position p3 = new Position(1,1);
        m3.generateMap();


        Ant a7 = new Ant(1,Colour.RED,p3);

        a7.setHasFood(false);
        m3.setAntAtCell(p3,a7);

        c.addAnt(a7);

        Content testEnum = Content.NINE;

        m3.setCellContents(p3, testEnum);

        b13.loadBrain(new File("brains/file20.txt"));




        b13.step(1); // should set state to 1 if there is food at cell
        assertEquals(1, a7.getState());
        Content con = m3.getCellContents(p3);
        assertEquals(Content.EIGHT, con ); // testing if it picks up the food changing it from nine to eight


        // another test checking if it picks anything up if a cell is empty
        Colony col = new Colony(Colour.RED);
        Map m4 = new Map();
        Brain b14 = new Brain(m4,col);
        Position p4 = new Position(1,1);
        Ant a8 = new Ant(4,Colour.RED,p4);

        m4.generateMap();

        col.addAnt(a8);

        m4.setCellContents(p4, Content.EMPTY);

        m4.setAntAtCell(p4, a8);

        b14.loadBrain(new File("brains/file21.txt"));


        b14.step(a8.getID());


        assertEquals(2, a8.getState());
        assertEquals(Content.EMPTY, m4.getCellContents(p4));
        // checks that it has gone to state 2 as there is no food


        // checks if it picks up food if it has food as it shouldn't
        Map m5 = new Map();
        Colony col1 = new Colony(Colour.RED);
        Brain b15 = new Brain(m5,col1);
        Position p5 = new Position(1,1);
        Ant a9 = new Ant(2,Colour.RED,p5);

        m5.generateMap();
        col1.addAnt(a9);
        b15.loadBrain(new File("brains/file22.txt"));


        m5.setCellContents(p4, Content.EMPTY);
        m5.setAntAtCell(p5, a9);
        a9.setHasFood(true);

        b15.step(2);

        assertEquals((Content.EMPTY), m5.getCellContents(p4));
        assertEquals(2, a9.getState()); // checks that it has gone to state 2 as the ant is carrying food

    }

    /**
     *
     * Testing if an ant will drop food
     * @throws CellAlreadyOccupiedException
     * @throws InvalidContentCharacterException
     */
    @Test
    public void testDrop() throws CellAlreadyOccupiedException, InvalidContentCharacterException {

        //testing an ant dropping food into an empty cell
        Map m6 = new Map();
        Colony c = new Colony(Colour.RED);
        Brain b16 = new Brain(m6,c);
        Position p6 = new Position(1,1);
        Ant a10 = new Ant(1,Colour.RED,p6);
        m6.generateMap();

        c.addAnt(a10);

        m6.setAntAtCell(p6, a10);

        m6.setCellContents(p6, Content.EMPTY);

        b16.loadBrain(new File("brains/file23.txt"));

        a10.setHasFood(true);


        b16.step(1);

        assertEquals(Content.ONE, m6.getCellContents(p6));
        assertEquals(1, a10.getState());


        // testing an ant dropping food into a cell with food already in it
        Map m7 = new Map();
        Colony c2 = new Colony(Colour.RED);
        Brain b17 = new Brain(m7,c2);
        Position p7 = new Position(1,1);
        Ant a11 = new Ant(3,Colour.RED,p7);

        m7.generateMap();
        c2.addAnt(a11);

        b17.loadBrain(new File("brains/file23.txt"));

        a11.setHasFood(true);

        m7.setCellContents(p7, Content.ONE);
        m7.setAntAtCell(p7, a10);

        b17.step(a11.getID());

        assertEquals(Content.TWO, m7.getCellContents(p7));
        assertEquals(1, a11.getState());


        // testing if the ant will drop any food if it is not holding any food in the first place
        Map m8 = new Map();
        Colony c3 = new Colony(Colour.RED);
        Brain b18 = new Brain(m8,c3);
        Position p8 = new Position(1,1);
        Ant a12 = new Ant(5,Colour.RED,p8);

        c3.addAnt(a12);
        m8.generateMap();
        b18.loadBrain(new File("brains/file24.txt"));

        a12.setHasFood(false);

        m8.setCellContents(p8, Content.EMPTY);
        m8.setAntAtCell(p8, a12);

        b18.step(5);

        assertEquals(Content.EMPTY,m8.getCellContents(p8));
        assertEquals(2, a12.getState());

    }

    /**
     * Testing if the ant turns left successfully
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testTurnLeft() throws CellAlreadyOccupiedException {

        Map m9 = new Map();
        Colony c = new Colony(Colour.RED);
        Brain b19 = new Brain(m9,c);
        Position p9 = new Position(1,1);
        Ant a17 = new Ant(1,Colour.RED,p9);
        m9.generateMap();
        c.addAnt(a17);

        b19.loadBrain(new File("brains/file25.txt"));

        m9.setAntAtCell(p9,a17);

        a17.setDirection(0);

        b19.step(1);

        assertEquals(5,a17.getDirection()); // checks if its in the correct direction
        assertEquals(1, a17.getState()); //  checks if its in the correct state


        b19.step(1);

        assertEquals(4,a17.getDirection()); // checks if its in the correct direction
        assertEquals(1, a17.getState()); //  checks if its in the correct state

        b19.step(1);

        assertEquals(3,a17.getDirection()); //checks if its in the correct direction
        assertEquals(1, a17.getState()); //  checks if its in the correct state

        b19.step(1);

        assertEquals(2,a17.getDirection());//checks if its in the correct direction
        assertEquals(1, a17.getState()); // checks if its in the correct state

        b19.step(1);

        assertEquals(1,a17.getDirection()); // checks if its in the correct direction
        assertEquals(1, a17.getState()); // checks if its in the correct state


        b19.step(1);

        assertEquals(0,a17.getDirection()); // checks if its in the correct direction
        assertEquals(1, a17.getState()); // checks if its in the correct state


    }

    /**
     *  Testing if the ant turns right correctly
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testTurnRight() throws CellAlreadyOccupiedException {


        Map m10 = new Map();
        Colony c = new Colony(Colour.RED);
        Brain b20 = new Brain(m10,c);
        Position p10 = new Position(1,1);
        Ant a18 = new Ant(1,Colour.RED,p10);

        m10.generateMap();
        c.addAnt(a18);

        b20.loadBrain(new File("brains/file26.txt"));

        m10.setAntAtCell(p10, a18);

        a18.setDirection(0);

        b20.step(1);

        assertEquals(1,a18.getDirection()); // checks if its in the correct direction
        assertEquals(1, a18.getState()); // checks if its in the correct state


        b20.step(1);

        assertEquals(2,a18.getDirection()); // checks if its in the correct direction
        assertEquals(1, a18.getState()); // checks if its in the correct state

        b20.step(1);

        assertEquals(3,a18.getDirection()); // checks if its in the correct direction
        assertEquals(1, a18.getState()); // checks if its in the correct state

        b20.step(1);

        assertEquals(4,a18.getDirection()); // checks if its in the correct direction
        assertEquals(1, a18.getState()); // checks if its in the correct state

        b20.step(1);

        assertEquals(5,a18.getDirection()); // checks if its in the correct direction
        assertEquals(1, a18.getState()); // checks if its in the correct state


        b20.step(1);

        assertEquals(0,a18.getDirection()); // checks if its in the correct direction
        assertEquals(1, a18.getState()); // checks if its in the correct state

    }

    /**
     * Tests if it moves into the correct cell
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testMove() throws CellAlreadyOccupiedException {

        Colony c = new Colony(Colour.RED);
        Map m11 = new Map();
        Brain b21 = new Brain(m11,c);

        Position p11 = new Position(3,3); // original
        Position  p12 = new Position(4,3);// goes right
        Position p13 = new Position(2,3); // left
        Position p14 = new Position(4,2); // top right
        Position p15 = new Position(3,2); // top left
        Position p16 = new Position(4,4); // bottom right
        Position p17 = new Position(3,4); // bottom left
        Position p18 = new Position(5,3);

        m11.generateMap();


        try {
            m11.setCellContents(p12,Content.EMPTY);
            m11.setCellContents(p13,Content.EMPTY);
            m11.setCellContents(p14,Content.EMPTY);
            m11.setCellContents(p15,Content.EMPTY);
            m11.setCellContents(p16,Content.EMPTY);
            m11.setCellContents(p17,Content.EMPTY);
            m11.setCellContents(p18,Content.EMPTY);
        } catch (InvalidContentCharacterException e) {
            e.printStackTrace();
        }
        b21.loadBrain(new File("brains/file27.txt"));

        Ant a19 = new Ant(1,Colour.RED,p11);


        c.addAnt(a19);

        //setting up the ant in the right cell and direction
        m11.setAntAtCell(p11,a19);
        a19.setDirection(0);


        b21.step(1);

        //checks if the cell that the ant was previously in is set to null
        assertEquals(null, m11.getAntAtCell(p11));
        assertEquals(a19,m11.getAntAtCell(p12));
        assertEquals(0,a19.getState());

        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);
        b21.step(1);

        //making the ant move again
        assertEquals(a19,m11.getAntAtCell(p18));
        assertEquals(null, m11.getAntAtCell(p12));

        assertEquals(0,a19.getState());


        //removing the ant that has moved to set up again
        try {
            c.remove(a19.getID());
        } catch (AntNotFoundException e) {
            e.printStackTrace();
        }

        //checks if the cell that the ant was previously in is set to null
        m11.clearAnt(a19.getPosition());
        assertEquals(null,m11.getAntAtCell(p12));


        // create a new ant to move
        Ant a = new Ant(1,Colour.RED, p11);
        c.addAnt(a);
        m11.setAntAtCell(p11,a19);
        a.setDirection(3);


        b21.step(1);

        // check cell the ant previously was in is null and if the ant is in the right state
        assertEquals(0,a.getState());
        assertEquals(a,m11.getAntAtCell(p13));
        assertEquals(null, m11.getAntAtCell(p11));


        m11.clearAnt(a.getPosition());
        try {
            c.remove(a.getID());
        } catch (AntNotFoundException e) {
            e.printStackTrace();
        }


        assertEquals(null,m11.getAntAtCell(p13));


        Ant a1 = new Ant(1, Colour.RED, p11);
        c.addAnt(a1);

        m11.setAntAtCell(p11 , a1);
        a1.setDirection(5);

        b21.step(1);

        assertEquals(0,a1.getState());
        assertEquals(null, m11.getAntAtCell(p11));
        assertEquals(a1,m11.getAntAtCell(p14));

        m11.clearAnt(a1.getPosition());
        try {
            c.remove(a1.getID());
        } catch (AntNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(null,m11.getAntAtCell(p14));


        Ant a2 = new Ant(1,Colour.RED,p11);

        c.addAnt(a2);

        m11.setAntAtCell(p11 , a2);
        a2.setDirection(4);

        b21.step(1);

        assertEquals(null, m11.getAntAtCell(p11));
        assertEquals(a2,m11.getAntAtCell(p15));
        assertEquals(0,a2.getState());

        m11.clearAnt(a2.getPosition());
        try {
            c.remove(a2.getID());
        } catch (AntNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(null,m11.getAntAtCell(p15));



        Ant a3 = new Ant(1,Colour.RED, p11);
        c.addAnt(a3);

        m11.setAntAtCell(p11 , a3);
        a3.setDirection(1);

        b21.step(1);

        assertEquals(null, m11.getAntAtCell(p11));
        assertEquals(a3,m11.getAntAtCell(p16));
        assertEquals(0,a3.getState());

        m11.clearAnt(a3.getPosition());

        try {
            c.remove(a3.getID());
        } catch (AntNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(null,m11.getAntAtCell(p16));


        Ant a4 = new Ant(1,Colour.RED,p11);

        c.addAnt(a4);
        m11.setAntAtCell(p11 , a4);
        a4.setDirection(2);

        b21.step(1);

        assertEquals(null, m11.getAntAtCell(p11));
        assertEquals(a4,m11.getAntAtCell(p17));
        assertEquals(0,a4.getState());

        m11.clearAnt(p17);
        try {
            c.remove(a4.getID());
        } catch (AntNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(null,m11.getAntAtCell(p17));

//        //////
//        //////
//
//        /// checking if rocky or occupied if it is do nothing change state

        Ant a5 = new Ant(1,Colour.RED,p11);
        c.addAnt(a5);
        m11.setAntAtCell(p11 , a5);
        a5.setDirection(0);

        try {
            m11.setCellContents(p12, Content.ROCKY);
        } catch (InvalidContentCharacterException e) {
            e.printStackTrace();
        }
        b21.step(1);

        assertEquals(a5, m11.getAntAtCell(p11));
        assertEquals(null,m11.getAntAtCell(p12));
        assertEquals(1, a5.getState());

        m11.clearAnt(a5.getPosition());
        try {
            c.remove(a5.getID());
        } catch (AntNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(null, m11.getAntAtCell(p11));


        // checks if the ant will move into an occupied cell

        Ant a6 = new Ant(1,Colour.RED,p11);
        Ant a7 = new Ant(2,Colour.RED,p12);

        c.addAnt(a6);
        c.addAnt(a7);
        m11.setAntAtCell(p11 , a6);
        m11.setAntAtCell(p12, a7);
        a6.setDirection(0);

        b21.step(1);

        assertEquals(a7,m11.getAntAtCell(p12));
        assertEquals(a6, m11.getAntAtCell(p11));
        assertEquals(1, a6.getState());


    }

    /**
     * Testing if when a surrounded ant will be killed
     */
    @Test
    public void testKillAntMove(){

        Colony red = new Colony(Colour.RED);
        Colony black = new Colony(Colour.BLACK);

        Map m = new Map();
        Brain b = new Brain(m,red);
        Brain b1 = new Brain(m,black);

        m.generateMap();


        Position p11 = new Position(3,3); // original ant position
        Position p12 = new Position(4,3);//  top right of the original pos
        Position p13 = new Position(2,3); // left of the original pos
        Position p14 = new Position(4,2); // top right of the original pos
        Position p15 = new Position(3,2); // top left of the original pos
        Position p16 = new Position(4,4); // bottom right of the original pos
        Position p17 = new Position(3,4); // bottom left of the original pos

        //creating and setting up so that once an ant moves it gets surrounded
        Ant a = new Ant(1,Colour.RED,p17);
        Ant a1 = new Ant(2,Colour.BLACK,p12);
        Ant a2 = new Ant(3,Colour.BLACK,p13);
        Ant a3 = new Ant(4,Colour.BLACK,p14);
        Ant a4 = new Ant(5,Colour.BLACK,p15);
        Ant a5 = new Ant(6,Colour.BLACK,p16);

        red.addAnt(a);
        black.addAnt(a1);
        black.addAnt(a2);
        black.addAnt(a3);
        black.addAnt(a4);
        black.addAnt(a5);

        a.setDirection(5);


        // puts some food into the cell the ant will die in to check if the when the ant dies it will add food to the cell it dies in
        try {
            m.setCellContents(p11,Content.THREE);
        } catch (InvalidContentCharacterException e) {
            e.printStackTrace();
        }

        b.loadBrain(new File("brains/file28.txt"));

        try {
            m.setAntAtCell(p17,a);
            m.setAntAtCell(p12,a1);
            m.setAntAtCell(p13,a2);
            m.setAntAtCell(p14,a3);
            m.setAntAtCell(p15,a4);
            m.setAntAtCell(p16,a5);

        } catch (CellAlreadyOccupiedException e) {
            e.printStackTrace();
        }

        assertEquals(Content.THREE,m.getCellContents(p11));

        b.step(a.getID());

        // checks if the ant is died after moving
        assertEquals(false,red.isAntAlive(1));

        //checks if the content of the cell is  6 after the ant as died
        assertEquals(Content.SIX, m.getCellContents(p11));

    }

    /**
     *
     * Checks if a resting ant dies if it gets surrounded
     */
    @Test
    public void testKillRestingAnt(){

        Colony red = new Colony(Colour.RED);
        Colony black = new Colony(Colour.BLACK);

        Map m = new Map();
        Brain b = new Brain(m,red);
        Brain b1 = new Brain(m,black);

        m.generateMap();

        Position p11 = new Position(3,3); // original
        Position p12 = new Position(4,3);// goes right
        Position p13 = new Position(2,3); // left
        Position p14 = new Position(4,2); // top right
        Position p15 = new Position(3,2); // top left
        Position p16 = new Position(4,4); // bottom right
        Position p17 = new Position(3,4); // bottom left

        Ant a = new Ant(1,Colour.RED,p11);
        Ant a1 = new Ant(2,Colour.BLACK,p12);
        Ant a2 = new Ant(3,Colour.BLACK,p13);
        Ant a3 = new Ant(4,Colour.BLACK,p14);
        Ant a4 = new Ant(5,Colour.BLACK,p15);
        Ant a5 = new Ant(6,Colour.BLACK,p16);

        red.addAnt(a);
        black.addAnt(a1);
        black.addAnt(a2);
        black.addAnt(a3);
        black.addAnt(a4);
        black.addAnt(a5);

        // a.setHasFood(true);

        a.setDirection(0);
        a.startResting();

        b.loadBrain(new File("brains/file29.txt"));

        try {
            m.setAntAtCell(p11,a);
            m.setAntAtCell(p12,a1);
            m.setAntAtCell(p13,a2);
            m.setAntAtCell(p14,a3);
            m.setAntAtCell(p15,a4);
            m.setAntAtCell(p16,a5);
        } catch (CellAlreadyOccupiedException e) {
            e.printStackTrace();
        }

        b.step(a.getID());

        assertEquals(false, red.isAntAlive(1));

        assertEquals(Content.THREE, m.getCellContents(p11));


    }


    /**
     * checks if the ant dies if it is idle or executing an instruction which isn't move and is surrounded
     */
    @Test
    public void testKillingAnt(){


        Colony red = new Colony(Colour.RED);
        Colony black = new Colony(Colour.BLACK);

        Map m = new Map();
        Brain b = new Brain(m,red);
        Brain b1 = new Brain(m,black);

        m.generateMap();

        Position p11 = new Position(3,3); // original
        Position p12 = new Position(4,3);// goes right
        Position p13 = new Position(2,3); // left
        Position p14 = new Position(4,2); // top right
        Position p15 = new Position(3,2); // top left
        Position p16 = new Position(4,4); // bottom right
        Position p17 = new Position(3,4); // bottom left

        Ant a = new Ant(1,Colour.RED,p11);
        Ant a1 = new Ant(2,Colour.BLACK,p12);
        Ant a2 = new Ant(3,Colour.BLACK,p13);
        Ant a3 = new Ant(4,Colour.BLACK,p14);
        Ant a4 = new Ant(5,Colour.BLACK,p15);
        Ant a5 = new Ant(6,Colour.BLACK,p16);

        red.addAnt(a);
        black.addAnt(a1);
        black.addAnt(a2);
        black.addAnt(a3);
        black.addAnt(a4);
        black.addAnt(a5);


        a.setDirection(0);


        b.loadBrain(new File("brains/file29.txt"));

        try {
            m.setAntAtCell(p11,a);
            m.setAntAtCell(p12,a1);
            m.setAntAtCell(p13,a2);
            m.setAntAtCell(p14,a3);
            m.setAntAtCell(p15,a4);
            m.setAntAtCell(p16,a5);
        } catch (CellAlreadyOccupiedException e) {
            e.printStackTrace();
        }

        b.step(a.getID());

        assertEquals(false,red.isAntAlive(1));

        assertEquals(Content.THREE, m.getCellContents(p11));



    }


}
