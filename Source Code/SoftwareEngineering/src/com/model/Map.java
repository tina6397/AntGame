package com.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import com.model.exceptions.*;

public class Map {

    private Cell[][] map = new Cell[150][150];
    public Cell[][] spairMap = new Cell[150][150];
    private ArrayList<Position> RED = new ArrayList<>();
    private ArrayList<Position> BLACK = new ArrayList<>();
    private ArrayList<Cell> REDHILL = new ArrayList<>();
    private ArrayList<Cell> BLACKHILL = new ArrayList<>();
    private ArrayList<Cell> Foodblob = new ArrayList<>();
    private ArrayList<Cell> Rocky = new ArrayList<>();
    private String lexedMap[][] = new String[150][150];
    private Cell[][] customMap = new Cell[150][150];

    public Map(){
        emptyMap();

    }
    
    private Position createPosition(int x, int y) throws PositionOutOfBoundsException {
        if((x < 0 || x > 149) || (y < 0 || y > 149)){
            throw new PositionOutOfBoundsException("The position is out of bound");
        }
        Position result = new Position(x,y);
        return result;
        
    }

    public void emptyMap(){
        for(int y = 0; y < 150; y++) {
            for (int x = 0; x < 150; x++) {
                map[x][y] = new Cell();
                customMap[x][y] = new Cell();

            }
        }
    }

    /**
     * Generates a pseudo-random map which is 150x150 in size
     * With terrain and food
     */
    public void generateMap() {
        for(int y = 0; y < 150; y++){
            for(int x = 0; x < 150; x++){
                if( x == 0 || x == 149 || y == 0 || y == 149 ){
                    map[x][y] = new Cell(Content.ROCKY);                      // The edge of com.model.Map
                    Rocky.add(map[x][y]);
                    spairMap[x][y] = new Cell(Content.ROCKY);
                }else{
                    map[x][y] = new Cell();
                    spairMap[x][y] = new Cell();
                }
            }
        }   // Setting a map.

        Random ran = new Random();

        //div1
        int x = (ran.nextInt(66)+5)*2 - 1;
        int div1 = (ran.nextInt(30)+5)*2 - 1;

        //div2
        int x1 = (ran.nextInt(66)+5)*2 - 1;
        int div2 = (ran.nextInt(30)+42)*2 - 1;

        int color = ran.nextInt(2);
        if(color == 0){
            generateAntHill(x,div1, Colour.BLACK);
            generateAntHill(x1,div2, Colour.RED);
        }
        if(color == 1) {
            generateAntHill(x, div1, Colour.RED);
            generateAntHill(x1, div2, Colour.BLACK);
        } // setting anthill;

        //
        int foodblob = 0;
        while(foodblob != 11){
            int foodX = 0, foodY = 0;
            int food = ran.nextInt(2);
            if(food == 0){  // x , y  are even
                foodX = (ran.nextInt(71) + 1) * 2;
                foodY = (ran.nextInt(71) + 1) * 2;
            }
            else if(food == 1){  // x , y  are odd
                foodX = (ran.nextInt(71) + 2) * 2 + 1;
                foodY = (ran.nextInt(71) + 1) * 2 + 1;
            }

            if(checkCell(foodX,foodY,food)){
                generateFoodBlob(foodX,foodY,food);
                foodblob++;
            }
        }

        int rock = 0;
        while(rock != 14){
            int X = (ran.nextInt(149) + 1);
            int Y = (ran.nextInt(149) + 1);
            if(map[X][Y].getContents() == Content.EMPTY){
                map[X][Y].setContents(Content.ROCKY);
                spairMap[X][Y].setContents(Content.ROCKY);
                Rocky.add(map[X][Y]);
                rock++;
            }
        }


    }

    private void generateAntHill(int x,int y, Colour colour){
        Content team;
        Cell cell, cell1, spcell, spcell1;
        if(colour == Colour.BLACK){
            team = Content.BLACKHILL;
        }else{
            team = Content.REDHILL;
        }
        for(int a = 0; a < 7; a++){
            for(int i = 0; i < 7 + a; i++){
                if(a != 6){
                    cell = map[x+i-(a/2)][y-6+a];
                    cell.setContents(team);
                    cell1 = map[x+i-(a/2)][y+6-a];
                    cell1.setContents(team);

                    spcell = spairMap[x+i-(a/2)][y-6+a];
                    spcell.setContents(team);
                    spcell1 = spairMap[x+i-(a/2)][y+6-a];
                    spcell1.setContents(team);
                    if(team == Content.BLACKHILL){
                        try {
                            BLACK.add(createPosition(x+i-(a/2),y-6+a));
                            BLACK.add(createPosition(x+i-(a/2),y+6-a));
                            BLACKHILL.add(cell);
                            BLACKHILL.add(cell1);
                            cell.setColonyCell(Content.BLACKHILL);
                            cell1.setColonyCell(Content.BLACKHILL);
                            spcell.setColonyCell(Content.BLACKHILL);
                            spcell1.setColonyCell(Content.BLACKHILL);
                        } catch (PositionOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }else if(team == Content.REDHILL){
                        try {
                            RED.add(createPosition(x+i-(a/2),y-6+a));
                            RED.add(createPosition(x+i-(a/2),y+6-a));
                            REDHILL.add(cell);
                            REDHILL.add(cell1);
                            spcell.setColonyCell(Content.REDHILL);
                            spcell.setColonyCell(Content.REDHILL);
                            spcell1.setColonyCell(Content.REDHILL);
                            spcell1.setColonyCell(Content.REDHILL);
                        } catch (PositionOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                }else{  // a == 6
                    cell = map[x+i-(a/2)][y-6+a];
                    cell.setContents(team);
                    spcell = spairMap[x+i-(a/2)][y-6+a];
                    spcell.setContents(team);
                    if(team == Content.BLACKHILL){
                        try {
                            BLACK.add(createPosition(x+i-(a/2),y-6+a));
                            BLACKHILL.add(cell);
                            cell.setColonyCell(Content.BLACKHILL);
                            spcell.setColonyCell(Content.BLACKHILL);
                        } catch (PositionOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }else if(team == Content.REDHILL){
                        try {
                            RED.add(createPosition(x+i-(a/2),y-6+a));
                            REDHILL.add(cell);
                            cell.setColonyCell(Content.REDHILL);
                            spcell.setColonyCell(Content.REDHILL);
                        } catch (PositionOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void generateFoodBlob(int x, int y, int Case){
        if(Case == 1){
            for(int a = 0; a < 5 ; a++){
                for(int b = 0; b < 5; b++){
                    map[x+b-(a/2)][y+a].setContents(Content.FIVE);
                    spairMap[x+b-(a/2)][y+a].setContents(Content.FIVE);
                    Foodblob.add(map[x+b-(a/2)][y+a]);
                }
            }
        }

        if(Case == 0){
            for(int a = 0; a < 5 ; a++){
                for(int b = 0; b < 5; b++){
                    map[x+b+(a/2)][y+a].setContents(Content.FIVE);
                    spairMap[x+b+(a/2)][y+a].setContents(Content.FIVE);
                    Foodblob.add(map[x+b+(a/2)][y+a]);
                }
            }
        }
    }

    private boolean checkCell(int x, int y, int Case){
        boolean result = true;

        if(Case == 1){  // left side
            for(int a = 0; a < 5; a ++) {
                for (int b = 0; b < 5; b++) {
                    if(map[x+b-(a/2)][y+a].getContents() != Content.EMPTY || Foodblob.contains(map[x+b-(a/2)][y+a]) || REDHILL.contains(map[x+b-(a/2)][y+a]) || BLACKHILL.contains(map[x+b-(a/2)][y+a])){
                        result = false;
                        break;
                    }
                }
            }
        }

        else if(Case == 0){
            for(int a = 0; a < 5; a ++) {
                for (int b = 0; b < 5; b++) {
                    if(map[x+b+(a/2)][y].getContents() != Content.EMPTY || Foodblob.contains(map[x+b+(a/2)][y+a])|| REDHILL.contains(map[x+b+(a/2)][y+a]) || BLACKHILL.contains(map[x+b+(a/2)][y+a]) ){
                        result = false;
                        break;
                    }
                }
            }            // right side
        }

        return result;
    }

    /**
     * reset map state to when it was last generated
     */
    public void clearMap() {
        map = spairMap;
    }

    /**
     * Returns whether the cell at a given position is Rocky.
     * Ants cannot traverse rocky cells
     *
     * @param pos the position of the cell
     * @return true if the cell is rocky, false otherwise.
     */
    public boolean getCellIsRocky(Position pos) {
        return map[pos.getX()][pos.getY()].isRocky();
    }

    /**
     * Gets the contents of a cell at a given position.
     *
     * @param pos the position of the cell
     * @return char representing the contents of the cell
     */
    public Content getCellContents(Position pos) {
        return map[pos.getX()][pos.getY()].getContents();
    }

    /**
     * Gets the scent marker of a cell at a given position.
     * If the scent marker is
     *
     * @param colourOfQueryingAnt the colour of the colony the querying ant belongs to
     * @param pos                 the position of the cell
     * @return The scent marker at the given cell. (1-7) are black colony scents
     * (7-13) are red. 0 if no scent marker has been placed.
     * <p/>
     * IF THE COLOUR OF THE QUERYING ANT IS ENEMY TO THE SCENT MARKER STORED IN THE CELL,
     * THEN THIS METHOD WILL RETURN -1
     */
    public int getCellScentMarker(Colour colourOfQueryingAnt, Position pos) { // swapped it around
        int value = 0;

        if(colourOfQueryingAnt == Colour.RED && map[pos.getX()][pos.getY()].getScentMark() < 7 ){
            value = map[pos.getX()][pos.getY()].getScentMark();
        }

         if(colourOfQueryingAnt == Colour.RED && map[pos.getX()][pos.getY()].getScentMark() > 6 ){

            value = -1;
        }

         if(colourOfQueryingAnt == Colour.BLACK && map[pos.getX()][pos.getY()].getScentMark() < 7 ){

                 value = -1;

        }

         if(colourOfQueryingAnt == Colour.BLACK && map[pos.getX()][pos.getY()].getScentMark() > 6 ){
            value = map[pos.getX()][pos.getY()].getScentMark();
        }
        if(map[pos.getX()][pos.getY()].getScentMark() == 0){
            value =0;
        }

        return value;
    }

    /**
     * Gets the ant at the cell with given position. Returns 0 if cell contains no ant.
     *
     * @param pos the position of the cell
     * @return id of ant at given position
     */
    public Ant getAntAtCell(Position pos) {
        return map[pos.getX()][pos.getY()].getAnt();
    }

    /**
     * #
     *
     * @param pos    the position of the ant
     * @param colour the colour of the target at at position pos, which is being checked for adjacent ants.
     * @return number representing adjacent enemy ants
     */
    public int getAdjacentEnemyAnts(Position pos, Colour colour) {
        int number = 0;
        if(pos.getY() % 2 == 1){
            if(map[pos.getX()][pos.getY()-1].getAnt() == null){

            }
            else if(map[pos.getX()][pos.getY()-1].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()+1][pos.getY()-1].getAnt() == null){
            }
            else if(map[pos.getX()+1][pos.getY()-1].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()- 1][pos.getY()].getAnt() == null) {
            }
            else if(map[pos.getX()-1][pos.getY()].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()+1][pos.getY()].getAnt() == null){
            }
            else if(map[pos.getX()+1][pos.getY()].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()][pos.getY()+1].getAnt() == null ){
            }
            else if(map[pos.getX()][pos.getY()+1].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()+1][pos.getY()+1].getAnt() == null){
            }
            else if(map[pos.getX()+1][pos.getY()+1].getAnt().getColour() == colour){
                number++;
            }

        }

        if(pos.getY() % 2 == 0){
            if(map[pos.getX()-1][pos.getY()-1].getAnt() == null){
            }
            else if(map[pos.getX()-1][pos.getY()-1].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()][pos.getY()-1].getAnt() == null){
            }
            else if(map[pos.getX()][pos.getY()-1].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()-1][pos.getY()].getAnt() == null){
            }
            else if(map[pos.getX()-1][pos.getY()].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()+1][pos.getY()].getAnt() == null){
            }
            else if(map[pos.getX()+1][pos.getY()].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()-1][pos.getY()+1].getAnt() == null){
            }
            else if(map[pos.getX()-1][pos.getY()+1].getAnt().getColour() == colour){
                number++;
            }
            if(map[pos.getX()][pos.getY()+1].getAnt() == null){
            }
            else if(map[pos.getX()][pos.getY()+1].getAnt().getColour() == colour){
                number++;
            }
        }


        return number;
    }

    /**
     * This method return the list of position which is the Anthill.
     * @param colour
     * @return
     */
    public List<Position> getAntHill(Colour colour) {
        if(colour == Colour.BLACK){
            return BLACK;
        }
        return RED;
    }

    /**
     * Sets the given cell to contain an ant specified with an ant id.
     *
     * @param pos   the position of the cell
     * @param ant the object of the ant
     * @throws CellAlreadyOccupiedException if the cell already contains an ant.
     */
    public void setAntAtCell(Position pos, Ant ant) throws CellAlreadyOccupiedException {

        if(map[pos.getX()][pos.getY()].getAnt() != null){
            throw new CellAlreadyOccupiedException("There is com.model.Ant already.");
        }else{
            map[pos.getX()][pos.getY()].setAnt(ant);
            ant.setPosition(pos);
        }

    }

    /**
     * Sets the contents of a cell, requires a single character which specifies the contents
     *
     * @param pos      the position of the cell
     * @param contents the character representing contents of the cell
     */
    public void setCellContents(Position pos, Content contents) throws InvalidContentCharacterException {
        if(contents == Content.EMPTY ||
                contents == Content.ROCKY ||
                contents == Content.REDHILL ||
                contents == Content.BLACKHILL ||
                contents == Content.ONE ||
                contents == Content.TWO ||
                contents == Content.THREE ||
                contents == Content.FOUR ||
                contents == Content.FIVE ||
                contents == Content.SIX ||
                contents == Content.SEVEN ||
                contents == Content.EIGHT ||
                contents == Content.NINE){
            map[pos.getX()][pos.getY()].setContents(contents);
        }else{
            throw new InvalidContentCharacterException(contents + " is not available contents");
        }
    }

    /**
     * Sets the scent marker of a cell
     *
     * @param pos    the position of the cell
     * @param marker the value representing the scent mark. int between 1 (inclusive) and 13 (exclusive). Use 0 to remove the scent
     */
    public void setCellScentMarker(Position pos, int marker) {

        map[pos.getX()][pos.getY()].setScentMark(marker);
    }

    /**
     * return current map as a 2D Cell array
     */
    public Cell[][] getMap() {
        return map;
    }

    /**
     *  This is when first game is finished, refresh to initial map.
     */
    public void getClearedMap(){
        REDHILL.clear();

        for(int y = 0; y < 150; y++){
            for(int x = 0; x < 150; x++){
                map[x][y].setContents(spairMap[x][y].getContents());
                map[x][y].setAnt(null);
            }
        }
    }

    /**
     *  clear Ant in the map.
     *  This is for when game is over, make clear map for next game.
     *  @param pos the position of cell where want to be cleaned.
     */
    public void clearAnt(Position pos){

        map[pos.getX()][pos.getY()].setAnt(null);

    }

    /**
     * This map is for when user want to play on loaded map.
     * This method setting the map.
     * @param world
     */
    private void  generateCustomMap(String world[][]){
        RED.clear();
        BLACK.clear();
        BLACKHILL.clear();
        REDHILL.clear();Foodblob.clear();Rocky.clear();
        for(int y = 0; y < 150 ; y++){
            for(int x =0; x < 150;x++){
                if(world[y][x].equals("#")){
                    Content c = Content.ROCKY;
                    map[149-x][149-y].setContents(c);
                    Cell m = customMap[149-x][149-y];
                    Rocky.add(m);
                }else if (world[y][x].equals("5")){
                    Content c = Content.FIVE;
                    map[149-x][149-y].setContents(c);
                    Cell m = customMap[149-x][149-y];
                    Foodblob.add(m);
                }else if (world[y][x].equals("+")){
                    Content c = Content.REDHILL;
                    RED.add(new Position(149-x,149-y));
                    map[149-x][149 -y].setContents(c);
                    Cell m = customMap[149-x][149-y];
                    REDHILL.add(m);
                }else if (world[y][x].equals("-")){
                    Content c = Content.BLACKHILL;
                    BLACK.add(new Position(149-x,149-y));
                    map[149-x][149-y].setContents(c);
                    Cell m = customMap[149-x][149-y];
                    BLACKHILL.add(m);
                }else{
                    Content c = Content.EMPTY;
                    map[149-x][149-y].setContents(c);
                }

            }
        }
    }

    /**
     *  If it is true, the map is loaded successfully
     *
     *  @param world the map file.
     */
    public boolean loadMap(File world){
        System.out.println(2);
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(world.getPath()));
            String worldString = new String(encoded, "UTF-8");
            String lexString = new String(encoded, "UTF-8");
            if(lexMap(lexString)){

                generateCustomMap(lexedMap);
                return true;
            } else {
                Arrays.fill(lexedMap,"");
                return false;
            }
        }
        catch(IOException e){
            return false;
        } catch (IncorrectMapSyntaxException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return false;
    }

    private int lexFood(int y, int x, int offset, String[][] mapToLex) throws IncorrectMapSyntaxException{
        if(mapToLex[y][x-1+offset].equals("5")){
            for(int i=0;i<5;i++){
                if(mapToLex[y][x-1+i+offset].equals("5")){
                    mapToLex[y][x-1+i+offset] = "*";
                }
                else{
                    throw new IncorrectMapSyntaxException("Incorrect Map Syntax");
                }
            }
            return -1;
        }
        else if(mapToLex[y][x+offset].equals("5")){
            for(int i=0;i<5;i++){
                if(mapToLex[y][x+i+offset].equals("5")){
                    mapToLex[y][x+i+offset] = "*";
                }
                else{
                    throw new IncorrectMapSyntaxException("Incorrect Map Syntax");
                }
            }
            return 0;
        }
        else if(mapToLex[y][x+1+offset].equals("5")){
            for(int i=0;i<5;i++){
                if(mapToLex[y][x+1+i+offset].equals("5")){
                    mapToLex[y][x+1+i+offset] = "*";
                }
                else{
                    throw new IncorrectMapSyntaxException("Incorrect Map Syntax");
                }
            }
            return 1;
        }
        else{
            throw new IncorrectMapSyntaxException("Incorrect Map Syntax");
        }
    }

    private void lexColony(int y, int x, int length, String[][] mapToLex, String hill) throws IncorrectMapSyntaxException{
        for(int i=0;i<length;i++){
            if(mapToLex[y][x+i].contains(hill))
                mapToLex[y][x+i] = "*";
            else
                throw new IncorrectMapSyntaxException("Incorrect Map Syntax");
        }
    }

    private boolean lexMap(String worldWithSpace) throws IncorrectMapSyntaxException{
        String world = worldWithSpace.replaceAll("\\s", "").substring(6);
        final int requiredFoodBlobs = 11;
        int worldSize = 150 * 150;
        int foodQty = 0;
        int rockQty = 0;
        boolean redCol = false;
        boolean blackCol = false;
        String[][] mapWorld = new String[150][150];
        for(int i=0;i<150;i++){	// fill 2d array with chars
            for(int j=0;j<150;j++){
                mapWorld[i][j]= world.substring(((i*150)+j),((i*150)+j)+1);
                lexedMap[i][j]= world.substring(((i*150)+j),((i*150)+j)+1);
            }
        }
        for(int i=0;i<150;i++){
            for(int j=0;j<150;j++){
                if(i==0||i==149){
                    if(mapWorld[i][j].equals("#")){
                        mapWorld[i][j] = "*";
                        rockQty++;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    switch(mapWorld[i][j]){
                        case "#":
                            mapWorld[i][j] = "*";
                            rockQty++;
                            break;
                        case ".":
                            mapWorld[i][j] = "*";
                            break;
                        case "5":
                            int offset = 0;
                            for(int k=0;k<5;k++){	// for each line
                                offset = offset + lexFood(i+k,j,offset,mapWorld);
                            }
                            foodQty++;
                            break;
                        case "+":
                            int redY = i;
                            int redX = j;
                            int redLength = 7;
                            for(int k=0;k<13;k++){
                                if(k<7){
                                    if((redY)%2==0){
                                        lexColony(redY,redX,redLength,mapWorld,"+");
                                        redX--;
                                    }
                                    else {
                                        lexColony(redY,redX,redLength,mapWorld,"+");
                                    }
                                    redLength++;
                                }
                                else{
                                    if(k==7){
                                        redX++;
                                        redLength = redLength -2;
                                    }
                                    if((redY)%2==0){
                                        lexColony(redY,redX,redLength,mapWorld,"+");
                                    }
                                    else{
                                        lexColony(redY,redX,redLength,mapWorld,"+");
                                        redX++;
                                    }
                                    redLength--;
                                }
                                redY++;
                            }
                            redCol = true;
                            break;
                        case "-":
                            int blackY = i;
                            int blackX = j;
                            int blackLength = 7;
                            for(int k=0;k<13;k++){
                                if(k<7){
                                    if((blackY)%2==0){
                                        lexColony(blackY,blackX,blackLength,mapWorld,"-");
                                        blackX--;
                                    }
                                    else {
                                        lexColony(blackY,blackX,blackLength,mapWorld,"-");
                                    }
                                    blackLength++;
                                }
                                else{
                                    if(k==7){
                                        blackX++;
                                        blackLength = blackLength -2;
                                    }
                                    if((blackY)%2==0){
                                        lexColony(blackY,blackX,blackLength,mapWorld,"-");
                                    }
                                    else{
                                        lexColony(blackY,blackX,blackLength,mapWorld,"-");
                                        blackX++;
                                    }
                                    blackLength--;
                                }
                                blackY++;
                            }
                            blackCol = true;
                            break;
                        case "*":
                            break;
                        default:
                            return false;
                    }
                }
            }
        }

        if(foodQty == 11 && redCol && blackCol && rockQty == 610){
            return true;
        }
        else
            return false;
    }
    /**
     * clean the ant of all map.
     */
    public void cleanAnt(){
        for(int y = 0; y < 150; y++ ){
            for(int x = 0; x < 150; x++){
                this.getMap()[x][y].setAnt(null);
            }
        }
    }

    /**
     * This method is for JUnit test
     * @param name
     * @return
     */
    public int getArray(String name){
        if(name == "food"){
            return Foodblob.size();
        }else if(name == "red"){
            return REDHILL.size();
        }else if(name == "rocky"){
            return Rocky.size();
        }
        return BLACKHILL.size();
    }

}
