
import com.model.Ant;
import com.model.Colour;
import com.model.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by Tina on 3/15/2015.
 * This class is created to run Junit tests on the AntImpl Class.
*/
public class AntTest {

    Ant testAnt;

    /**
     * This before method initializes the Ant object for testing
     */
    @Before
    public void initialize(){
        testAnt = new Ant(1, Colour.RED,new Position(0,0));
    }


    /**
     * This test method tests the get and set method for the Direction instance in the AntImpl class
     */
    @Test
    public void test_GetDirection() {

        // --- for the range 0-5, it should be able to input into direction ---
            for (int i=0;i<6;i++){
                testAnt.setDirection(i);
                int result = testAnt.getDirection();
                assertEquals(i, result);
            }

        // --- for direction other than 0-5, input should not be set into direction ---
            testAnt.setDirection(6);
            int result = testAnt.getDirection();
            assertNotEquals(6,result);

    }


    /**
     * This test method tests the get and set method for the State instance in the AntImpl class
     */
    @Test
    public void test_GetState() {

        // --- test if state is in range 0-9999 ---
        testAnt.setState(1);
        int result = testAnt.getState();
        assertEquals(1, result);

        // --- test if state is out of range ---
        testAnt.setState(10000);
        int result2 = testAnt.getState();
        assertFalse(10000 == result2);

    }

    /**
     * This test method tests two methods related to the Resting instance in AntImpl Class,
     * which is isResting and incrementRest method.
     */
    @Test
    public void test_Resting() {

        // --- test for first round, ant should be not resting ---
        assertFalse(testAnt.isResting());


        // --- test for the next 14 round, ant should be resting ---
        for (int i=1;i<15;i++){
            testAnt.incrementRest();
            assertEquals(15 - i, testAnt.getRemainingRest());
            assertTrue(testAnt.isResting());
        }


        // --- test for the 15th round, ant should end resting ---
        testAnt.incrementRest();
        assertFalse(testAnt.isResting());


    }

    /**
     * This test class two method related to the hasFood instance,
     * which is hasFood and setHasFood.
     */
    @Test
    public void test_hasFood(){

        // --- ant shouldn't have food once created ---
        assertFalse(testAnt.hasFood);

        // --- ant should have food after being set ---
        testAnt.setHasFood(true);
        assertTrue(testAnt.hasFood);

    }

}
