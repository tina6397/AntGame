
import com.model.*;
import com.model.exceptions.CellAlreadyOccupiedException;
import com.model.exceptions.InvalidContentCharacterException;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class MapTest {

    /**
     * This test is to create map properly.
     * such as number of two anthill, rock and food.
     */
    @Test // should also test the rocky borders
    public void testGenerateMapTest(){
        Map m = new Map();
        m.generateMap();

        assertEquals(127,m.getArray("red"));
        assertEquals(127,m.getArray("black"));
        assertEquals(275,m.getArray("food"));
        assertEquals(610,m.getArray("rocky"));
    }

    /**
     * When user cleared the map
     * this test will give it refresh properly
     */
    @Test // this test is wrong
    public void testClearMap(){
        Map m = new Map();
        m.generateMap();
        Cell[][] m1 =  m.getMap();

        Position p = new Position(1,1);
        Position p1 = new Position(1,2);
        Position p2= new Position(1,3);
        Position p3 = new Position(1,4);
        Ant a = new Ant(1,Colour.RED,p);
        Ant a1 = new Ant(2,Colour.RED,p1);
        Ant a2= new Ant(3,Colour.RED,p2);
        Ant a3 = new Ant(4,Colour.RED,p3);
        try {
            m.setAntAtCell(p,a);
            m.setAntAtCell(p1,a1);
            m.setAntAtCell(p2,a2);
            m.setAntAtCell(p3,a3);
        } catch (CellAlreadyOccupiedException e) {
            e.printStackTrace();
        }


        assertEquals(a,m.getAntAtCell(p));
        assertEquals(a1,m.getAntAtCell(p1));
        assertEquals(a2,m.getAntAtCell(p2));
        assertEquals(a3,m.getAntAtCell(p3));

        m.clearMap();

        assertEquals(null,m.getAntAtCell(p));
        assertEquals(null,m.getAntAtCell(p1));
        assertEquals(null,m.getAntAtCell(p2));
        assertEquals(null,m.getAntAtCell(p3));

        Cell[][] m2 = m.getMap();

        for(int x = 0; x < 150; x++){
            for(int b = 0; b < 150; b++){
                assertEquals(m1[x][b].getContents(),m2[x][b].getContents());
            }
        }

    }

    /**
     *  This test is to check isRocky method is working properly by using setContent method
     */
    @Test // should also check if a empty cell is rocky and should return false
    public void testCellIsRocky(){
        Map m = new Map();
        m.generateMap();
        Cell[][] map = m.getMap();

        map[5][5].setContents(Content.EMPTY);
        assertFalse(map[5][5].isRocky());
        map[5][5].setContents(Content.ROCKY);
        assertTrue(map[5][5].isRocky());
    }

    /**
     * This test is to check set Cell content method is working properly.
     * @throws InvalidContentCharacterException
     */
    @Test
    public void testCellContent() throws InvalidContentCharacterException {
        Map m = new Map();
        m.generateMap();
        Cell[][] map = m.getMap();

        Position p = new Position(5,5);
        m.setCellContents(p,Content.EMPTY);
        assertEquals(m.getCellContents(p), Content.EMPTY);
        m.setCellContents(p,Content.REDHILL);
        assertEquals( m.getCellContents(p),Content.REDHILL);
        m.setCellContents(p,Content.BLACKHILL);
        assertEquals( m.getCellContents(p),Content.BLACKHILL);
        m.setCellContents(p,Content.ONE);
        assertEquals( m.getCellContents(p),Content.ONE);
        m.setCellContents(p,Content.TWO);
        assertEquals( m.getCellContents(p),Content.TWO);
        m.setCellContents(p,Content.THREE);
        assertEquals( m.getCellContents(p),Content.THREE);
        m.setCellContents(p,Content.FOUR);
        assertEquals( m.getCellContents(p),Content.FOUR);
        m.setCellContents(p,Content.FIVE);
        assertEquals( m.getCellContents(p),Content.FIVE);
        m.setCellContents(p,Content.SIX);
        assertEquals( m.getCellContents(p),Content.SIX);
        m.setCellContents(p,Content.SEVEN);
        assertEquals( m.getCellContents(p),Content.SEVEN);

    }

    /**
     * This test is to check Cell ScenMarker return properly.
     * if the team is different it should return -1
     * else it will return their number.
     */
    @Test
    public void testCellScentMarker(){
        Map m = new Map();
        m.generateMap();

        Position p = new Position(5,5);
        m.setCellScentMarker(p,3);
        assertEquals(-1, m.getCellScentMarker(Colour.BLACK, p));
        assertEquals(3,m.getCellScentMarker(Colour.RED,p));

        m.setCellScentMarker(p,8);
        assertEquals(8,m.getCellScentMarker(Colour.BLACK,p));
        assertEquals(-1,m.getCellScentMarker(Colour.RED,p));


    }

    /**
     * This test is to test When ant in a cell, it return the ant properly(Checking setAnt and getAnt method )
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void testAntAtCell() throws CellAlreadyOccupiedException {
        Map m = new Map();
        m.generateMap();

        assertNull(m.getAntAtCell(new Position(5,5)));

        Ant ant = new Ant(1, Colour.RED,new Position(5,5));
        m.setAntAtCell(new Position(5,5),ant);
        assertEquals(m.getAntAtCell(new Position(5,5)),ant);

    }


    /**
     * This test is to test getAdjacentEnemyAnts method work properly.
     * @throws CellAlreadyOccupiedException
     */
    @Test
    public void getAdjacentEnemyAnts() throws CellAlreadyOccupiedException {
        Map m = new Map();
        m.generateMap();

        Ant ant1 = new Ant(1, Colour.BLACK,new Position(5,5));
        Ant ant2 = new Ant(1, Colour.RED,new Position(4,5));
        Ant ant3 = new Ant(1, Colour.RED,new Position(6,5));
        Ant ant4 = new Ant(1, Colour.RED,new Position(5,4));
        Ant ant5 = new Ant(1, Colour.RED,new Position(6,4));
        Ant ant6 = new Ant(1, Colour.RED,new Position(5,6));
        Ant ant7 = new Ant(1, Colour.RED,new Position(5,6));

        m.setAntAtCell(new Position(5, 5), ant1);
        assertEquals(0, m.getAdjacentEnemyAnts(new Position(5, 5), Colour.RED));

        m.setAntAtCell(new Position(4, 5), ant2);
        assertEquals(1, m.getAdjacentEnemyAnts(new Position(5, 5), Colour.RED));

        m.setAntAtCell(new Position(6,5),ant3);
        assertEquals(2,m.getAdjacentEnemyAnts(new Position(5,5),Colour.RED));

        m.setAntAtCell(new Position(5,4),ant4);
        assertEquals(3,m.getAdjacentEnemyAnts(new Position(5,5),Colour.RED));

        m.setAntAtCell(new Position(6,4),ant5);
        assertEquals(4,m.getAdjacentEnemyAnts(new Position(5,5),Colour.RED));

        m.setAntAtCell(new Position(5,6),ant6);
        assertEquals(5,m.getAdjacentEnemyAnts(new Position(5,5),Colour.RED));

        m.setAntAtCell(new Position(6,6),ant7);
        assertEquals(6,m.getAdjacentEnemyAnts(new Position(5,5),Colour.RED));


    }

    /**
     * This test is to check the map has syntactically error or not.
     */
    @Test
    public void testLoadMap(){
        

        Map m = new Map();
        boolean testChecker;
        testChecker = m.loadMap(new File("brains/brokenWorld.world"));
        assertFalse(testChecker);


        boolean testChecker1;
        testChecker1 = m.loadMap(new File("brains/workingWorld.world"));
        assertTrue(testChecker1);

    }







}
