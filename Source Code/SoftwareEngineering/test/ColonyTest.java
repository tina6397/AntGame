
import com.model.*;
import com.model.exceptions.AntNotFoundException;
import org.junit.*;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;


/**
 * Created by Tina on 3/15/2015.
 * This class perform tests on the method in ColonyImpl
*/
public class ColonyTest {

    Colour color ;
    HashMap<Integer,Ant> antList ;
    Ant ant1 ;
    Ant ant2 ;
    Ant ant3 ;
    Colony colony ;

    /**
     * The is a before method for initializing the Colony object for testing
     * which contains three Ant object and is in the color RED
     */
    @Before
    public void initialize() {

        color = Colour.RED;

        colony = new Colony(color);

        ant1 = new Ant(1,color,new Position(0,0));
        ant2 = new Ant(2,color,new Position(0,1));
        ant3 = new Ant(3,color,new Position(0,2));

        colony.addAnt(ant1);
        colony.addAnt(ant2);
        colony.addAnt(ant3);
    }

    /**
     * This test method tests the getAnt method that takes an ID of an Ant object
     * @throws AntNotFoundException
     */
    @Test
    public void test_GetAnt_with_ID() throws AntNotFoundException {

        // --- test if the output of getting ant with ID=1 is the same object with the original ANT ---
        Ant output1 = colony.getAnt(1);
        assertEquals(ant1, output1);


        Ant output2 = colony.getAnt(2);
        assertEquals(ant2, output2);

        Ant output3 = colony.getAnt(3);
        assertEquals(ant3, output3);
    }

    /**
     * This test method tests the getAnt method that takes the position of an Ant object
     * @throws AntNotFoundException
     */
    @Test
    public void test_GetAnt_with_position() throws AntNotFoundException{

        assertEquals(ant1.getPosition(), colony.getAnt(1).getPosition());

    }

    /**
     * This test method tests the isAntAlive method by checking an existing ant and an non-existing ant
     */
    @Test
    public void test_is_ant_alive(){

        // --- for ants that exist, is should return true ---
        for (int i=1;i<4;i++){
            assertTrue(colony.isAntAlive(i));
        }

        // --- for ant that doesn't exist, should return false ---
        assertFalse(colony.isAntAlive(4));
    }

    /**
     * This test method tests the getNumberOfAnts method
     */
    @Test
    public void test_getNumberOfAnts(){
        assertEquals(3, colony.getNumberOfAnts());
    }

    /**
     * This test method tests the getFoodInColony method and the incrementFood method
     */
    @Test
    public void test_food(){

        // --- initially colony should have no food ---
        assertEquals( 0, colony.getFoodInColony());

        // --- after incrementing, colony should have 1 food ---
        colony.incrementFood();
        assertEquals( 1, colony.getFoodInColony());


    }

    /**
     * This test method tests the setBrain and getBrain metod
     */
    @Test
    public void test_brain(){

        Map testMap = new Map();
        Colony testColony = new Colony(Colour.RED);
        Brain testBrain = new Brain(testMap,testColony);

        testColony.setBrain(testBrain);
        assertEquals(testBrain, testColony.getBrain());

    }

    /**
     * This test method tests the getColonyColor method
     */
    @Test
    public void test_getColonyColour(){
        assertEquals(color , colony.getColonyColour());
    }

    /**
     * This test method tests the remove method which removes an ant from the colony
     * @throws AntNotFoundException
     */
    @Test
    public void test_remove() throws AntNotFoundException {

        // --- remove ant at 0,0 which is ant1, it should be deleted from the list ---
        colony.remove(1);
        assertFalse(colony.isAntAlive(1));

    }

}
