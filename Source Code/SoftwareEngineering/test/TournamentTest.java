import com.model.*;
import com.view.*;
import com.model.exceptions.CellAlreadyOccupiedException;
import com.model.exceptions.InvalidContentCharacterException;
import org.junit.Test;

import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;


public class TournamentTest {

    /**
     * Test that the fixtures are created properly with the correct players, playing each other with three players
     */
    @Test
    public void testCreateFixturesThereBrains(){

        HashMap<String,File> test = new HashMap<>();
        List<Game> testList;

        File file1 = new File("brains/file.txt");
        File file2 = new File("brains/file1.txt");
        File file3 = new File("brains/file2.txt");
        test.put("1",file1);
        test.put("2",file2);
        test.put("3",file3);




        Tournament testT = new Tournament();

        testList= testT.createFixtures(test);

        assertEquals("1", testList.get(0).getRedPlayerName());
        assertEquals("2",testList.get(0).getBlackPlayerName());

        assertEquals("2", testList.get(1).getRedPlayerName());
        assertEquals("1",testList.get(1).getBlackPlayerName());


        assertEquals("1", testList.get(2).getRedPlayerName());
        assertEquals("3",testList.get(2).getBlackPlayerName());

        assertEquals("3",testList.get(3).getRedPlayerName());
        assertEquals("1",testList.get(3).getBlackPlayerName());

        assertEquals("2", testList.get(4).getRedPlayerName());
        assertEquals("3",testList.get(4).getBlackPlayerName());

        assertEquals("3",testList.get(5).getRedPlayerName());
        assertEquals("2",testList.get(5).getBlackPlayerName());



    }

    /**
     * Test that the fixtures are created properly with the correct players, playing each other with four players
     */
    @Test

    public void testCreateFixturesFourBrains(){

        HashMap<String,File> test = new HashMap<>();
        List<Game> testList;

        File file1 = new File("brains/file.txt");
        File file2 = new File("brains/file1.txt");
        File file3 = new File("brains/file2.txt");
        File file4 = new File("brains/file3.txt");

        test.put("1",file1);
        test.put("2",file2);
        test.put("3",file3);
        test.put("4",file4);


        Tournament testT = new Tournament();

        testList= testT.createFixtures(test);

        assertEquals("1", testList.get(0).getRedPlayerName());
        assertEquals("2",testList.get(0).getBlackPlayerName());
        assertEquals("2", testList.get(1).getRedPlayerName());
        assertEquals("1",testList.get(1).getBlackPlayerName());


        assertEquals("1", testList.get(2).getRedPlayerName());
        assertEquals("3",testList.get(2).getBlackPlayerName());
        assertEquals("3",testList.get(3).getRedPlayerName());
        assertEquals("1",testList.get(3).getBlackPlayerName());

        assertEquals("1", testList.get(4).getRedPlayerName());
        assertEquals("4",testList.get(4).getBlackPlayerName());
        assertEquals("4",testList.get(5).getRedPlayerName());
        assertEquals("1",testList.get(5).getBlackPlayerName());


        assertEquals("2", testList.get(6).getRedPlayerName());
        assertEquals("3",testList.get(6).getBlackPlayerName());
        assertEquals("3",testList.get(7).getRedPlayerName());
        assertEquals("2",testList.get(7).getBlackPlayerName());

        assertEquals("2", testList.get(8).getRedPlayerName());
        assertEquals("4",testList.get(8).getBlackPlayerName());
        assertEquals("4",testList.get(9).getRedPlayerName());
        assertEquals("2",testList.get(9).getBlackPlayerName());

        assertEquals("3", testList.get(10).getRedPlayerName());
        assertEquals("4",testList.get(10).getBlackPlayerName());
        assertEquals("4",testList.get(11).getRedPlayerName());
        assertEquals("3",testList.get(11).getBlackPlayerName());



    }

    /**
     * Tests if the scores update correctly
     */
    @Test

    public void testUpdateScore(){

        File brainFile = new File("brains/brain1.txt");

        File brainFile1 = new File("brains/file.txt");

        File brainFile2 = new File("brains/file1.txt");

        HashMap<String,File> tester = new HashMap<>();

        tester.put("1",brainFile);
        tester.put("2",brainFile1);
        tester.put("3",brainFile2);


        Game game = new Game(brainFile, "1", brainFile1, "2", null);

        Tournament t = new Tournament();

        t.createFixtures(tester);
        t.updateScores(game);

        HashMap<String,Integer> testHashMap = t.getResults();


        assertEquals(1,(int)testHashMap.get("1"));
        assertEquals(1,(int) testHashMap.get("2"));
        assertEquals(0,(int) testHashMap.get("3"));

    }



}