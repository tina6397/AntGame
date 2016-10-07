import com.model.*;
import com.model.exceptions.AntNotFoundException;
import com.view.GameGUI;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jacob on 26/03/2015.
 */
public class GameTest {

    /**
     * Test to check if the brain is checked for any syntax errors
     */
    @Test
    public void loadBrainTest() {
        File brainFile = new File("brains/brain1.txt");
        Colony c = new Colony(Colour.RED);
        Map m = new Map();
        File brainFile1 = new File("brains/brain1.txt");
        Game game = new Game(brainFile, "1", brainFile1, "2", null);
        game.getColony(Colour.RED);
        System.out.println(game.loadBrain(brainFile, Colour.RED));
        assertTrue(game.loadBrain(brainFile, Colour.RED));
    }

    /**
     * Tests to check if the map and game is setup correctly when starting up
     */
    @Test
    public void testSetup() {

        File brainFile = new File("brains/brain1.txt");

        File brainFile1 = new File("brains/brain1.txt");




        Game game = new Game(brainFile, "1", brainFile1, "2", null);
        List<Position> redHill = game.getMap().getAntHill(Colour.RED);
        List<Position> blackHill = game.getMap().getAntHill(Colour.BLACK);
        game.setup();


        for (Position p : redHill) {  //  checks ants are generated in to the map


            try {

                assertTrue(game.getMap().getAntAtCell(p).equals(game.getColony(Colour.RED).getAnt(p)));

            } catch (AntNotFoundException e) {
                e.printStackTrace();
            }


        }

        for (Position p1 : blackHill) {

            try {

                assertTrue(game.getMap().getAntAtCell(p1).equals(game.getColony(Colour.BLACK).getAnt(p1)));

            } catch (AntNotFoundException e) {
                e.printStackTrace();
            }

        }


    }
}