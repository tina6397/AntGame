package com.model;

import com.model.exceptions.*;
import com.model.tokens.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Brain {

    private List<Token> brain[];
    public List<Token> lexedList = new ArrayList<>();

    public List<Token> instructionGetter = new ArrayList<>();
    private File loadedFile;
    private int flipCounter =0;

    public ArrayList<ArrayList<Token>> state = new ArrayList<>();




    private Map map;
    private Colony colony;



    public Brain(Map map, Colony colony){
        this.map = map;
        this.colony = colony;

    }

    /**
     * gets the file that was loaded into the brain
     *
     * @return the txt file loaded into the com.model.Brain object containing the brain instructions
     */
    public File getLoadedFile() {
        return loadedFile;
    }

    private boolean lexBrain(String brain) {

        lexedList.clear();  // preps the lexedList for new brain lexing

        if(brain.length()==0)
            return false;

        String nextToken = "";



        String lines[] = brain.split("[\\r?\\n]+");


        for(int i=0;i<lines.length;i++) {
            String currentLine = lines[i];
            boolean end = false;
            int j = 0;
            while (!end && j < lines[i].length()) {
                String nextChar = currentLine.substring(j, j + 1);
                if (nextChar.equals(" ") && !nextToken.isEmpty()) {   // if next char is space AND nextToken is not empty
                    try {
                        lexedList.add(chooseToken(nextToken));

                        instructionGetter.add(chooseToken(nextToken));

                        nextToken = "";
                    } catch (BrainSyntaxIncorrectException e) {
                        System.out.println(e);
                        lexedList.clear();
                        return false;
                    }
                } else if (nextChar.equals(";")) {
                    if (!nextToken.isEmpty()) {
                        try {
                            lexedList.add(chooseToken(nextToken));

                            instructionGetter.add(chooseToken(nextToken));

                            nextToken = "";
                        } catch (BrainSyntaxIncorrectException e) {
                            System.out.println(e);
                            lexedList.clear();
                            return false;
                        }
                    }
                    end = true;
                } else {
                    nextToken = nextToken + nextChar;
                    if(currentLine.length()==(j+1)){
                        try {
                            lexedList.add(chooseToken(nextToken));

                            instructionGetter.add(chooseToken(nextToken));

                            nextToken = "";
                        } catch (BrainSyntaxIncorrectException e) {
                            System.out.println(e);
                            lexedList.clear();
                            return false;
                        }
                    }
                }
                j++;
            }
        }



        return true;
    }

    private Token chooseToken(String tokenAsString) throws BrainSyntaxIncorrectException {
        Token token = null;
        try{
            token = new Int(Integer.parseInt(tokenAsString));
        }
        catch (NumberFormatException e) {
            switch (tokenAsString) {
                case "Ahead":
                    token = new Ahead();
                    break;
                case "Drop":
                    token = new Drop();
                    break;
                case "Flip":
                    token = new Flip();
                    break;
                case "Foe":
                    token = new Foe();
                    break;
                case "FoeHome":
                    token = new FoeHome();
                    break;
                case "FoeWithFood":
                    token = new FoeWithFood();
                    break;
                case "FoeMarker":
                    token = new FoeMarker();
                    break;
                case "Food":
                    token = new Food();
                    break;
                case "Friend":
                    token = new Friend();
                    break;
                case "FriendWithFood":
                    token = new FriendWithFood();
                    break;
                case "Here":
                    token = new Here();
                    break;
                case "Home":
                    token = new Home();
                    break;
                case "Left":
                    token = new Left();
                    break;
                case "LeftAhead":
                    token = new LeftAhead();
                    break;
                case "Mark":
                    token = new Mark();
                    break;
                case "Marker":
                    token = new Marker();
                    break;
                case "Move":
                    token = new Move();
                    break;
                case "Right":
                    token = new Right();
                    break;
                case "RightAhead":
                    token = new RightAhead();
                    break;
                case "Rock":
                    token = new Rock();
                    break;
                case "Sense":
                    token = new Sense();
                    break;
                case "Turn":
                    token = new Turn();
                    break;
                case "Unmark":
                    token = new Unmark();
                    break;
                case "PickUp":
                    token = new PickUp();
            }
        }
        if(token!=null) {
            return token;
        }
        else
            throw new BrainSyntaxIncorrectException("Invalid Token!");
    }



    private boolean parseBrain() {

        try {
            parseInstruction(lexedList);
            if(lexedList.isEmpty())
                return true;

        } catch (BrainSyntaxIncorrectException e) {
            System.out.println(e);
            return false;

        }
        return false;

    }

    private List<Token> parseInstruction(List<Token> tokens) throws BrainSyntaxIncorrectException {

        if (tokens.isEmpty()) {

        }
        else if (tokens.get(0) instanceof Sense){
            tokens.remove(0);
            tokens = parseSenseDir(tokens);
            tokens = parseSt(tokens);
            tokens = parseSt(tokens);
            tokens = parseCond(tokens);
            tokens = parseInstruction(tokens);
        }
        else if(tokens.get(0) instanceof Mark){
            tokens.remove(0);
            tokens = parseI(tokens);
            tokens = parseSt(tokens);
            tokens = parseInstruction(tokens);
        }
        else if(tokens.get(0) instanceof Unmark){
            tokens.remove(0);
            tokens = parseI(tokens);
            tokens = parseSt(tokens);
            tokens = parseInstruction(tokens);
        }
        else if(tokens.get(0) instanceof PickUp){
            tokens.remove(0);
            tokens = parseSt(tokens);
            tokens = parseSt(tokens);
            tokens = parseInstruction(tokens);
        }
        else if(tokens.get(0) instanceof Drop){
            tokens.remove(0);
            tokens = parseSt(tokens);
            tokens = parseInstruction(tokens);
        }
        else if(tokens.get(0) instanceof Turn){
            tokens.remove(0);
            tokens = parseLr(tokens);
            tokens = parseSt(tokens);
            tokens = parseInstruction(tokens);
        }
        else if(tokens.get(0) instanceof Move){
            tokens.remove(0);
            tokens = parseSt(tokens);
            tokens = parseSt(tokens);
            tokens = parseInstruction(tokens);
        }
        else if(tokens.get(0) instanceof Flip) {
            tokens.remove(0);
            tokens = parseP(tokens);
            tokens = parseSt(tokens);
            tokens = parseSt(tokens);
            tokens = parseInstruction(tokens);
        }
        else
            throw new BrainSyntaxIncorrectException("Ill-formed Instruction");

        return tokens;

    }

    private List<Token> parseSenseDir(List<Token> tokens) throws BrainSyntaxIncorrectException {
        if (tokens.get(0) instanceof Here){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof Ahead){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof LeftAhead){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof RightAhead){
            tokens.remove(0);
        }
        else
            throw new BrainSyntaxIncorrectException("Ill-formed sensedir");

        return tokens;
    }

    private List<Token> parseCond(List<Token> tokens) throws BrainSyntaxIncorrectException {
        if (tokens.get(0) instanceof Friend){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof Foe){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof FriendWithFood){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof FoeWithFood){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof Food){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof Rock){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof Marker){
            tokens.remove(0);
            tokens = parseI(tokens);
        }
        else if(tokens.get(0) instanceof FoeMarker){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof Home){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof FoeHome){
            tokens.remove(0);
        }
        else
            throw new BrainSyntaxIncorrectException("Ill-formed cond");

        return tokens;
    }

    private List<Token> parseLr(List<Token> tokens) throws BrainSyntaxIncorrectException {
        if(tokens.get(0) instanceof Left){
            tokens.remove(0);
        }
        else if(tokens.get(0) instanceof Right){
            tokens.remove(0);
        }
        else
            throw new BrainSyntaxIncorrectException("Ill-formed direction");

        return tokens;
    }

    private List<Token> parseSt(List<Token> tokens) throws BrainSyntaxIncorrectException {
        if(tokens.get(0) instanceof Int){
            int x = ((Int) tokens.get(0)).n;
            if(x>= 0 && x <10000){
                tokens.remove(0);
            }
            else
                throw new BrainSyntaxIncorrectException("State out of range");
        }
        else
            throw new BrainSyntaxIncorrectException("State not found");

        return tokens;
    }

    private List<Token> parseI(List<Token> tokens) throws BrainSyntaxIncorrectException {
        if(tokens.get(0) instanceof Int){
            int x = ((Int) tokens.get(0)).n;
            if(x >=0 && x <6){
                tokens.remove(0);
            }
            else
                throw new BrainSyntaxIncorrectException("I out of range");
        }
        else
            throw new BrainSyntaxIncorrectException("I not found");

        return tokens;
    }

    private List<Token> parseP(List<Token> tokens) throws BrainSyntaxIncorrectException {
        if(tokens.get(0) instanceof Int){
            int x = ((Int) tokens.get(0)).n;
            if(x >0){
                tokens.remove(0);
            }
            else
                throw new BrainSyntaxIncorrectException("P out of range");
        }
        else
            throw new BrainSyntaxIncorrectException("P not found");

        return tokens;
    }


    /**
     * Loads a brain from file. This controls the behaviour of every ant in the colony.
     * The brain is fundamental to the operation of the colony and this method must be invoked before the game begins
     * @param brain the brain to be loaded
     * @return  true if brain successfully loaded into colony, false otherwise.
     */
    public boolean loadBrain(File brain) {

        state.clear();
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(String.valueOf(brain)));

            if(lexBrain(new String(encoded, StandardCharsets.UTF_8))){

                boolean passed =  parseBrain();



                if(passed){

                    loadedFile = brain;
                }

                setUpStates();


                System.out.println(passed);
                if(passed)
                    loadedFile = brain;

                return passed;
            }
            else
                return false;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Executes the next instruction for an ant. Should be invoked by the game
     * @param id the id of the ant
     */
    public void step(int id) {

        Colour enemyColour;
        if(colony.getColonyColour() == Colour.RED){
            enemyColour = Colour.BLACK;
        }else {
            enemyColour = Colour.RED;
        }

        Position p = null;
        Ant a = null;

        if (colony.isAntAlive(id)){
            try {
                a = colony.getAnt(id);
                p = colony.getAnt(id).getPosition();
            } catch (AntNotFoundException e) {
                e.printStackTrace();
            }
            if(a.isResting()){
                if(map.getAdjacentEnemyAnts(p, enemyColour) == 5 || map.getAdjacentEnemyAnts(p, enemyColour) == 6){ // checks if the ant is surrounded while resting
                    try {
                        colony.remove(a.getID());      // if the ant is surrounded then it dies and removes it from the colony
                    } catch (AntNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(map.getCellContents(p) == Content.EMPTY){    // the ant turns into food and this food is dropped on to the cell its currently in
                        try {
                            map.setCellContents(p,Content.THREE);
                        } catch (InvalidContentCharacterException e) {
                            e.printStackTrace();
                        }
                    }else if( (Content.getFoodValue(map.getCellContents(p))!= -1 )){
                        int content = Content.getFoodValue(map.getCellContents(p));
                        if(a.hasFood()){
                            if(content<5) {
                                content = content + 4;  // if the ant is carrying foo then add 4 food particles to the cell
                            }else if(content >= 6){
                                content = 9;           // cap food particles in a cell to 9 of it goes over this value
                            }
                        }else{
                            if(content <5){
                                content = content +3;
                            }else if(content >=6){
                                content =9;
                            }
                        }
                        try {
                            map.setCellContents(p,Content.getFoodEnumValue(content));
                        } catch (InvalidContentCharacterException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    a.incrementRest();
                }
            }
            else {
                int currentState = 0;
                try {
                    currentState = colony.getAnt(id).getState();
                } catch (AntNotFoundException e) {
                    e.printStackTrace();
                }

                Token command = state.get(currentState).get(0);

                if(map.getAdjacentEnemyAnts(p, enemyColour) == 5 || map.getAdjacentEnemyAnts(p, enemyColour) == 6){  // check if the ant gets surrounded when its about to invoke an instruction
                    try {
                        colony.remove(a.getID());
                    } catch (AntNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(map.getCellContents(p) == Content.EMPTY){
                        try {
                            map.setCellContents(p,Content.THREE);
                        } catch (InvalidContentCharacterException e) {
                            e.printStackTrace();
                        }
                    }else if( (Content.getFoodValue(map.getCellContents(p))!= -1 )){
                        int content = Content.getFoodValue(map.getCellContents(p));
                        if(a.hasFood()){
                            if(content<5) {
                                content = content + 4;
                            }else if(content >= 6){
                                content = 9;
                            }
                        }else{
                            if(content <5){
                                content = content +3;
                            }else if(content >=6){

                                content =9;
                            }
                        }
                        try {
                            map.setCellContents(p,Content.getFoodEnumValue(content));
                        } catch (InvalidContentCharacterException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if (command instanceof Sense) {  // sense the cell the ant is facing
                    Position p1 = sensedCell(p, a.getDirection(), state.get(currentState).get(1));

                    if (state.get(currentState).get(4) instanceof Marker) {   // checks if the instruction is to sense for a marker

                        if (cellMatchCheckMarker(p1, a.getColour(), ((Int)state.get(currentState).get(5)).n)) {

                            a.setState(((Int)state.get(currentState).get(2)).n);  // set the state of the ant depending on what is sense

                        } else {
                            a.setState(((Int)state.get(currentState).get(3)).n);
                        }

                    } else if (state.get(currentState).get(4) instanceof FoeMarker) { // checks if the instruction is to sense for foemarker

                        if (cellMatchCheckEnemyMarker(p1, a.getColour())) {

                            a.setState(((Int)state.get(currentState).get(2)).n);

                        } else {

                            a.setState(((Int)state.get(currentState).get(3)).n);

                        }
                    } else if (cellMatches(p1, state.get(currentState).get(4), a.getColour())) {

                        a.setState(((Int)state.get(currentState).get(2)).n);

                    }else{

                        a.setState(((Int)state.get(currentState).get(3)).n);

                    }
                } else if (command instanceof Mark) {  // mark the cell the ant is currently in
                    if(a.getColour() == Colour.RED){

                        map.setCellScentMarker(p, ((Int)state.get(currentState).get(1)).n);

                    }else if(a.getColour() == Colour.BLACK){

                        map.setCellScentMarker(p, ((Int)state.get(currentState).get(1)).n+6);
                    }

                    a.setState(((Int)state.get(currentState).get(2)).n);  // change to the state given in the instructions
                }
                else if (command instanceof Unmark) {  // unmark the current cell the ant is in if the marker has the same colour as the ant
                    if(a.getColour() == Colour.RED){

                        if(map.getCellScentMarker(a.getColour(),p) == ((Int)state.get(currentState).get(1)).n){

                            map.setCellScentMarker(p,0);
                            a.setState(((Int)state.get(currentState).get(2)).n);
                        }
                    }else if(a.getColour() == Colour.BLACK){
                        if(map.getCellScentMarker(a.getColour(),p)-6 == ((Int)state.get(currentState).get(1)).n){
                            map.setCellScentMarker(p,0);
                            a.setState(((Int)state.get(currentState).get(2)).n);
                        }
                    }
                } else if (command instanceof PickUp) { // try to pickup food at the current cell
                    Content contents = map.getCellContents(p);
                    if (a.hasFood() ||  contents == Content.EMPTY) {  // checks if the ant has food or if the cell is empty

                        a.setState(((Int)state.get(currentState).get(2)).n);

                    } else if (Content.getFoodValue(contents)!= -1 && Content.getFoodValue(contents) > 0) {
                        int content = Content.getFoodValue(contents);
                        content = content -1;
                        if(content == 0){
                            try {
                                map.setCellContents(p,Content.EMPTY);
                            } catch (InvalidContentCharacterException e) {
                                e.printStackTrace();
                            }
                        }else{
                            try {
                                map.setCellContents(p, Content.getFoodEnumValue(content));
                            } catch (InvalidContentCharacterException e) {
                                e.printStackTrace();
                            }
                            a.setHasFood(true);
                            a.setState(((Int)state.get(currentState).get(1)).n);
                        }
                    }
                } else if (command instanceof Drop) {  // drop food if the ant has holding food
                    Content contents = map.getCellContents(p);
                    if (a.hasFood()) {
                        if (contents == Content.EMPTY) {
                            try {
                                map.setCellContents(p, Content.ONE);
                            } catch (InvalidContentCharacterException e) {
                                e.printStackTrace();
                            }
                        } else {
                            int content = Content.getFoodValue(contents);
                            content++;
                            if(content >= 9){
                                try {
                                    map.setCellContents(p,Content.NINE);
                                } catch (InvalidContentCharacterException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                try {
                                    map.setCellContents(p, Content.getFoodEnumValue(content));
                                } catch (InvalidContentCharacterException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(map.getAntHill(a.getColour()).contains(a.getPosition())){
                            colony.incrementFood();
                        }
                        a.setHasFood(false);
                    }

                    a.setState(((Int)state.get(currentState).get(1)).n);

                } else if (command instanceof Turn) { // turn left or right

                    if ((state.get(currentState).get(1)) instanceof Right) {

                        if (a.getDirection() == 5) {
                            a.setDirection(0);
                        } else {
                            a.setDirection(a.getDirection() + 1);
                        }

                    } else if ((state.get(currentState).get(1)) instanceof Left) {
                        if (a.getDirection() == 0) {
                            a.setDirection(5);
                        } else {
                            a.setDirection(a.getDirection() - 1);
                        }
                    }

                    a.setState(((Int)state.get(currentState).get(2)).n);

                } else if (command instanceof Move) {  // move the ant in the direction it is facing
                    int dir = a.getDirection();
                    Position pos = getAdjacentCell(p,dir);
                    if(map.getCellIsRocky(pos) || map.getAntAtCell(pos) != null ){

                        a.setState(((Int)state.get(currentState).get(2)).n);

                    }else{
                        try {
                            map.clearAnt(p);
                            map.setAntAtCell(pos, a);
                        } catch (CellAlreadyOccupiedException e) {
                            e.printStackTrace();
                        }
                        a.setPosition(pos);
                        a.setState(((Int) state.get(currentState).get(1)).n);
                        a.startResting();
                        if(map.getAdjacentEnemyAnts(pos, enemyColour) == 5 || map.getAdjacentEnemyAnts(pos, enemyColour) == 6){  // checks if the ant is surrounded after it has moved
                            try {
                                colony.remove(a.getID());
                            } catch (AntNotFoundException e) {
                                e.printStackTrace();
                            }
                            if(map.getCellContents(pos) == Content.EMPTY){
                                try {
                                    map.setCellContents(pos,Content.THREE);
                                } catch (InvalidContentCharacterException e) {
                                    e.printStackTrace();
                                }
                            }else if( (Content.getFoodValue(map.getCellContents(pos))!= -1 )){
                                int content = Content.getFoodValue(map.getCellContents(pos));

                                if(a.hasFood()){
                                    if(content<5) {
                                        content = content + 4;
                                    }else if(content >= 6){
                                        content = 9;
                                    }
                                }else{
                                    if(content <5){
                                        content = content +3;
                                    }else if(content >=6){
                                        content =9;
                                    }
                                }
                                try {
                                    map.setCellContents(pos,Content.getFoodEnumValue(content));
                                } catch (InvalidContentCharacterException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }else if(command instanceof Flip){
                    Random rand = new Random();
                    int check = rand.nextInt(((Int)state.get(currentState).get(1)).n);
                    if(check == 0){
                        a.setState(((Int)state.get(currentState).get(2)).n);
                    }else{
                        a.setState(((Int)state.get(currentState).get(3)).n);
                    }
                }

            }

        }

    }


    private int randomInt(int n){

        int s4 = 12345;
        //int s4=0;
        for(int x =0; x < 4; x++){

            s4 = s4 * 22695477 + 1;

        }

        for(int y=0; y < flipCounter; y++){


            s4 = s4 * 22695477 + 1;
        }

        int xAtI = (s4/65536)%16384;

        xAtI = xAtI%n;

        flipCounter++;
        return xAtI;

    }


    private boolean cellMatchCheckEnemyMarker(Position p, Colour c){

        int check = map.getCellScentMarker(c,p);
        if(c == Colour.RED && check >= 7){

            return true;


        }else if(c == Colour.BLACK && check < 7){

            return true;

        }else{


            return false;
        }



    }

    private boolean cellMatchCheckMarker(Position p,  Colour c, int Marker){

        int check = map.getCellScentMarker(c,p);
        if(c == Colour.RED && check < 7 && check == Marker){

            return true;


        }else if(c == Colour.BLACK && check >= 7 && check < 13 && check == Marker){


            return true;

        }else{

            return false;
        }

    }

    private boolean cellMatches(Position p, Token condition, Colour c){



        if(map.getCellIsRocky(p)){

            if(condition instanceof Rock){

                return true;
            }
            else{

                return false;
            }
        }else{

            if(condition instanceof Friend){

                Ant check = map.getAntAtCell(p);
                try {
                    if(colony.getAnt(p) == check){ ////


                        try {
                            if(colony.getAnt(check.getID()).getColour() == c) {
                                return true;
                            }
                        } catch (AntNotFoundException e) {
                            e.printStackTrace();
                        }


                    }////
                    else{

                        return false;
                    }
                } catch (AntNotFoundException e) {
                    e.printStackTrace();
                }

            }else if(condition instanceof Foe){

                Ant check = map.getAntAtCell(p);
                try {
                    if(colony.getAnt(p) == check){

                        try {
                            if(colony.getAnt(check.getID()).getColour() != c){

                                return true;
                            }
                        } catch (AntNotFoundException e) {
                            e.printStackTrace();
                        }


                    }else{

                        return false;
                    }
                } catch (AntNotFoundException e) {
                    e.printStackTrace();
                }


            }else if(condition instanceof FriendWithFood){


                Ant check = map.getAntAtCell(p);
                try {
                    if(colony.getAnt(p) == check){
                        try {
                            if(colony.getAnt(check.getID()).getColour() == c){

                                if(colony.getAnt(check.getID()).hasFood()) {

                                    return true;
                                }
                            }
                        } catch (AntNotFoundException e) {
                            e.printStackTrace();
                        }


                    }else{

                        return false;
                    }
                } catch (AntNotFoundException e) {
                    e.printStackTrace();
                }

            }else if(condition instanceof FoeWithFood){

                Ant check = map.getAntAtCell(p);
                try {
                    if(colony.getAnt(check.getID()).getPosition() == p){
                        if(map.getAntAtCell(p).getColour() != c){

                            if(map.getAntAtCell(p).hasFood()) { /// need to update map to get this method

                                return true;
                            }
                        }


                    }else{

                        return false;
                    }
                } catch (AntNotFoundException e) {
                    e.printStackTrace();
                }


            }else if(condition instanceof Food){

                Content content =  map.getCellContents(p);
                if(Content.getFoodValue(content) > 0){

                    return true;

                }else{


                    return false;
                }

            }else if(condition instanceof Rock){


                return false;

            }else if(condition instanceof Home){

                List<Position> checkAntHill = map.getAntHill(c);


                if(checkAntHill.contains(p)){

                    return true;

                }else {


                    return false;
                }

            } else if(condition instanceof FoeHome){

                Colour r = Colour.RED;
                Colour b = Colour.BLACK;
                List<Position> checkAntHill =null;


                if(c == Colour.RED ){

                    checkAntHill = map.getAntHill(b);
                }
                if(c == Colour.BLACK){

                    checkAntHill = map.getAntHill(r);

                }


                if(checkAntHill.contains(p)){

                    return true;

                }else {

                    return false;
                }


            }




        }
        return false;
    }

    private Position sensedCell(Position p, int direction ,Token senseDir)  {

        Position pos = null;

        if(senseDir instanceof Here){

            pos = p;

        }else if(senseDir instanceof Ahead){

            pos = getAdjacentCell(p,direction);

        }else if(senseDir instanceof LeftAhead){

            int leftDir;
            if(direction == 0){

                leftDir = 5;
            }else{

                leftDir = direction - 1;
            }

            pos = getAdjacentCell(p, leftDir);

        }else if(senseDir instanceof RightAhead){


            int rightDir;
            if(direction == 5){

                rightDir = 0;
            }else{

                rightDir = direction + 1;
            }

            pos = getAdjacentCell(p, rightDir);

        }

        return pos;

    }

    private Position getAdjacentCell(Position p, int direction) {

        Position p2 = null;

        if (direction == 0) {
            p2 = new Position(p.getX()+1, p.getY());

        } else if (direction == 1) {

            if(p.getY()%2 == 0) {
                p2 = new Position(p.getX(), p.getY()+1);


            }else{

                p2 = new Position(p.getX()+1, p.getY()+1);

            }

        }else if(direction == 2){

            if(p.getY()%2 == 0) {
                p2 = new Position(p.getX()-1, p.getY()+1);

            }else{

                p2 = new Position(p.getX(), p.getY()+1);

            }


        }else if(direction == 3){

            p2 = new Position(p.getX()-1, p.getY());

        }
        else if(direction == 4){

            if(p.getY()%2 == 0) {

                p2 = new Position(p.getX()-1, p.getY()-1);

            }else{

                p2 = new Position(p.getX(), p.getY()-1);

            }


        }else if(direction == 5){

            if(p.getY()%2 == 0) {

                p2 = new Position(p.getX(), p.getY()-1);

            }else{

                p2 = new Position(p.getX()+1, p.getY()-1);


            }
        }

        return p2;

    }


    private void setUpStates(){


        for(int x = 0; x < instructionGetter.size(); x++){

            Token tokenChecker = instructionGetter.get(x);

            if(tokenChecker instanceof Sense){
                Token check2 = instructionGetter.get(x+4);
                if(check2 instanceof Marker){


                    ArrayList<Token> temp = new ArrayList<>();

                    temp.add(instructionGetter.get(x));
                    temp.add(instructionGetter.get(x+1));
                    temp.add(instructionGetter.get(x+2));
                    temp.add(instructionGetter.get(x+3));
                    temp.add((instructionGetter.get(x+4)));
                    temp.add((instructionGetter.get(x+5)));


                    state.add(temp);
                    x = x +5;

                }else {


                    ArrayList<Token> temp = new ArrayList<>();

                    temp.add(instructionGetter.get(x));
                    temp.add(instructionGetter.get(x + 1));
                    temp.add(instructionGetter.get(x + 2));
                    temp.add(instructionGetter.get(x + 3));
                    temp.add((instructionGetter.get(x + 4)));


                    state.add(temp);
                    x = x + 4;

                }

            }
            else if(tokenChecker instanceof Mark){

                ArrayList<Token> temp = new ArrayList<>();

                temp.add(instructionGetter.get(x));
                temp.add(instructionGetter.get(x+1));
                temp.add(instructionGetter.get(x+2));

                state.add(temp);

                x = x + 2;

            }
            else
            if(tokenChecker instanceof Unmark){

                ArrayList<Token> temp = new ArrayList<>();

                temp.add(instructionGetter.get(x));
                temp.add(instructionGetter.get(x+1));
                temp.add(instructionGetter.get(x+2));

                state.add(temp);

                x = x + 2;
            }
            else
            if(tokenChecker instanceof PickUp){

                ArrayList<Token> temp = new ArrayList<>();

                temp.add(instructionGetter.get(x));
                temp.add(instructionGetter.get(x+1));
                temp.add(instructionGetter.get(x+2));

                state.add(temp);

                x = x + 2;

            }
            else
            if(tokenChecker instanceof Drop){

                ArrayList<Token> temp = new ArrayList<>();

                temp.add(instructionGetter.get(x));
                temp.add(instructionGetter.get(x+1));


                state.add(temp);

                x = x + 1;

            }
            else
            if(tokenChecker instanceof Turn){

                ArrayList<Token> temp = new ArrayList<>();

                temp.add(instructionGetter.get(x));
                temp.add(instructionGetter.get(x+1));
                temp.add(instructionGetter.get(x+2));

                state.add(temp);

                x = x + 2;

            }
            else
            if(tokenChecker instanceof Move){

                ArrayList<Token> temp = new ArrayList<>();

                temp.add(instructionGetter.get(x));
                temp.add(instructionGetter.get(x+1));
                temp.add(instructionGetter.get(x+2));

                state.add(temp);

                x = x + 2;

            }
            else
            if(tokenChecker instanceof Flip){

                ArrayList<Token> temp = new ArrayList<>();

                temp.add(instructionGetter.get(x));
                temp.add(instructionGetter.get(x+1));
                temp.add(instructionGetter.get(x+2));
                temp.add(instructionGetter.get(x+3));

                state.add(temp);

                x = x + 3;

            }


            /**
             * gets the file that was loaded into the brain
             *
             * @return the txt file loaded into the com.model.Brain object containing the brain instructions
             */




        }




    }





}
