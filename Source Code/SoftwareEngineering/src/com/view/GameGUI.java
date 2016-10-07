package com.view;

import com.controller.GameController;
import com.model.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.model.exceptions.AntNotFoundException;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameGUI extends JFrame implements ActionListener {
    private Game game;
    private JButton b1, b2, b3, b4, b5, b6, startGame;
    private JTextField t1, t2, t3, t4;
    private JTextField n1, n2, n3, n4;
    private JLabel l1, l2, l3, l4, instruction, gameStatus;
    private ArrayList<JButton> Blist = new ArrayList<>();
    private ArrayList<Integer> list = new ArrayList<>();
    private JPanel menuPanel, tablePanel, rankPanel, fixturePanel, loadPanel;
    private JPanel mapPanel;
    private Container container;
    private JTabbedPane tab;
    private JDialog gameover;
    private Graphic c;
    Cell[][] map = new Cell[150][150];

    private List<Game> loadedGames = new ArrayList<>();

    private  File customMap ;
    public HashMap<String, File> inputBrains;
    public ArrayList<String> playerNamesOrder;
    public HashMap<Integer, String> playerNames;

    //new code*
    public HashMap<String, Integer> result ;

    //fixtures list
    private List<Game> fixtures = new ArrayList<>();

    //indicators for brain loading status
    private Boolean isSingleGame;
    private Boolean isRandomWorld;

    private Boolean p1Ready;
    private Boolean p2Ready;
    private Boolean p3Ready;
    private Boolean p4Ready;

    private Tournament tournament;
    private GameController controller;
    private GridBagConstraints gbc = new GridBagConstraints();

    public GameGUI(Tournament t) {
        super("Software Engineering");
        tournament = t;
        t.setGUI(this);
        controller = new GameController();
        CreateUI();
        setSize(960,729);
        setResizable(false);
    }


    private void CreateUI() {

        container = getContentPane();
        container.setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tab = new JTabbedPane();

        createLoadPanel();
        createMenuPanel();
        createTablePanel();
        createMapPanel();

        pack();

        setResizable(true);
        setVisible(true);

        tab.addTab("Load Brain Page", loadPanel);
        tab.addTab("Game Page", container);
        setContentPane(tab);
        tab.setEnabledAt(1, false);


    }


    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        startGame = makeButton("start game");
        startGame.setEnabled(false);
        gameStatus = new JLabel("No Brains Loaded");
        menuPanel.add(startGame);
        menuPanel.add(gameStatus);
        menuPanel.setVisible(true);
        gbc.gridy = 0;
        gbc.weightx =3;
        gbc.weighty = 3;
        gbc.insets = new Insets(2, 2, 2, 2);

        container.add(menuPanel, gbc);
    }


    private void createTablePanel() {
        b1 = new JButton("null");
        b1.setEnabled(false);
        b2 = new JButton("null");
        b2.setEnabled(false);
        b3 = new JButton("null");
        b3.setEnabled(false);
        b4 = new JButton("null");
        b4.setEnabled(false);
        b5 = new JButton("null");
        b5.setEnabled(false);
        b6 = new JButton("null");
        b6.setEnabled(false);
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2,0));
        tablePanel.setBackground(Color.BLACK);

        fixturePanel = new JPanel();
        fixturePanel.setBorder(new TitledBorder(new LineBorder(Color.black, 5), "Tournament Fixture"));
        fixturePanel.setLayout(new GridLayout(6, 1));

        //fixturePanel.add(makeButton("Setting up the Matches"));

        fixturePanel.add(b1);
        Blist.add(b1);
        fixturePanel.add(b2);
        Blist.add(b2);
        fixturePanel.add(b3);
        Blist.add(b3);
        fixturePanel.add(b4);
        Blist.add(b4);
        fixturePanel.add(b5);
        Blist.add(b5);
        fixturePanel.add(b6);
        Blist.add(b6);

        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(fixtures.size());
                if (fixtures.size() >= 2) {

                    loadedGames.clear();
                    loadedGames.add(fixtures.get(0));
                    loadedGames.add(fixtures.get(1));
                    game = loadedGames.get(0);
                    game.setup();
                    startGame.setEnabled(true);
                }
                b1.setEnabled(false);

            }
        });
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (fixtures.size() >= 4) {
                    loadedGames.clear();
                    loadedGames.add(fixtures.get(2));
                    loadedGames.add(fixtures.get(3));
                    game = loadedGames.get(0);
                    game.setup();
                    startGame.setEnabled(true);
                }
                b2.setEnabled(false);

            }
        });
        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (fixtures.size() >= 6) {
                    loadedGames.clear();
                    loadedGames.add(fixtures.get(4));
                    loadedGames.add(fixtures.get(5));
                    game = loadedGames.get(0);
                    game.setup();
                    startGame.setEnabled(true);
                }
                b3.setEnabled(false);

            }
        });
        b4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (fixtures.size() >= 8) {
                    loadedGames.clear();
                    loadedGames.add(fixtures.get(6));
                    loadedGames.add(fixtures.get(7));
                    game = loadedGames.get(0);
                    game.setup();
                    startGame.setEnabled(true);
                }
                b4.setEnabled(false);

            }
        });
        b5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (fixtures.size() == 10) {
                    loadedGames.clear();
                    loadedGames.add(fixtures.get(8));
                    loadedGames.add(fixtures.get(9));
                    game = loadedGames.get(0);
                    game.setup();
                    startGame.setEnabled(true);
                }
                b5.setEnabled(false);
            }
        });
        b6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (fixtures.size() == 12) {
                    loadedGames.clear();
                    loadedGames.add(fixtures.get(10));
                    loadedGames.add(fixtures.get(11));
                    game = loadedGames.get(0);
                    game.setup();
                    startGame.setEnabled(true);
                }
                b6.setEnabled(false);
            }
        });

        rankPanel = new JPanel();
        rankPanel.setBorder(new TitledBorder(new LineBorder(Color.black, 5), "Rank"));
        rankPanel.setLayout(new GridLayout(4, 4));

        l1 = new JLabel("First");
        t1 = new JTextField("");
        t1.setEditable(false);
        rankPanel.add(l1,new GridLayout(1,1));
        rankPanel.add(t1);
        l2 = new JLabel("Second");
        t2 = new JTextField("");
        t2.setEditable(false);
        rankPanel.add(l2);
        rankPanel.add(t2);
        l3 = new JLabel("Third");
        t3 = new JTextField("");
        t3.setEditable(false);
        rankPanel.add(l3);
        rankPanel.add(t3);
        l4 = new JLabel("Fourth");
        t4 = new JTextField("");
        t4.setEditable(false);
        rankPanel.add(l4);
        rankPanel.add(t4);

        tablePanel.add(fixturePanel, new GridLayout(1, 1));
        tablePanel.add(rankPanel, new GridLayout(2, 1));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 5;
        gbc.gridheight =5;
        gbc.weightx = 55;
        gbc.weighty = 55;
        gbc.insets = new Insets(2, 2, 2, 2);

        container.add(tablePanel, gbc);

    }



    private void createMapPanel() {
        //mapPanel = new JPanel();

        mapPanel = new JPanel(new BorderLayout());
        c = new Graphic();
        Map map = new Map();
        map.emptyMap();
        c.setMap(map.getMap(), map);
        mapPanel.add(c, BorderLayout.CENTER);
        c.setVisible(true);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = gbc.weighty = 45;
        gbc.insets = new Insets(2, 2, 2, 2);
        container.add(mapPanel, gbc);

    }

    public void displayResult(){
        //new code

        result = tournament.getResults();

        String name = playerNames.get(1);
        Integer score = result.get(name);
        t1.setText(name+" 's score:" + score);

        String name2 = playerNames.get(2);
        Integer score2 = result.get(name2);
        t2.setText(name2+" 's score:" + score2);


        String name3 = playerNames.get(3);
        Integer score3 = result.get(name3);
        t3.setText(name3+" 's score:" + score3);




        if (result.size() == 4){

            String name4 = playerNames.get(4);
            Integer score4 = result.get(name4);
            t4.setText(name4+" 's score:" + score4);
        }

    }
    public void signalGameEnd(Game game) throws AntNotFoundException {
        loadedGames.remove(game);
        controller.updateScores(game, tournament);
        if (loadedGames.size() == 1) {
            JOptionPane.showMessageDialog(this, "First round over, play second one? Click start game again.");

            displayResult();


            Game nextGame = loadedGames.get(0);
            nextGame.getMap().getClearedMap();
            nextGame.setup();
            this.game = nextGame;



        }else if(isSingleGame == true){
            if(game.getWinner() == null){
                JOptionPane.showMessageDialog(this, "This game draw");
                startGame.setEnabled(false);
            }else{
                String winner = game.getWinner().toString();
                JOptionPane.showMessageDialog(this, "Game Ended ! Winner is: " +game.getWinnerName()+" ("+ winner+")");
                startGame.setEnabled(false);
            }

        }
        else {
            JOptionPane.showMessageDialog(this, "Second round over, thanks for playing!");
            startGame.setEnabled(false);

            displayResult();
        }
    }

    public void updateGUI(Game game) {
//
//        BufferedImage image = c.createBufferedImage();
        c.setMap(game.getMap().getMap(), game.getMap());


    }

    public static void main(String[] args) {
        Tournament t = new Tournament();
        GameGUI gui = new GameGUI(t);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String n = e.getActionCommand();

        if (n == "start game") {

            if (isSingleGame){
                loadedGames.clear();
                loadedGames.add(fixtures.get(0));
                //loadedGames.add(fixtures.get(1));
                game = loadedGames.get(0);
                if(isRandomWorld == false){
                    game.loadMap(customMap);
                }
                game.setup();
                startGame.setEnabled(true);
            }

            RunnableGame g = new RunnableGame(game);
            Thread t = new Thread(g);
            t.start();
        }


    }

    private JButton makeButton(String name) {
        JButton m = new JButton(name);
        m.addActionListener(this);
        return m;
    }

    class Graphic extends Canvas {
        private CellImg ci = new CellImg(3);
        private Map m;

        public void setMap(Cell[][] map1, Map m) {
            map = map1;
            this.m = m;
            repaint();
        }

        public Graphic(){
            setBackground(Color.white);
        }

        @Override
        public void paint(Graphics g){
            Graphics2D g2;
            g2 = (Graphics2D) g;


            for(int j = 0; j < 150; j++){
                for(int i = 0; i < 150; i++){

                    if(map[i][j].getContents() == Content.EMPTY){

                        try {

                            if (map[i][j].getAnt() != null) {

                                if (map[i][j].getAnt().getColour() == Colour.RED) {
                                    ci.drawHex(i, j, g2, new Color(252, 227, 39));
                                }
                                if (map[i][j].getAnt().getColour() == Colour.BLACK) {
                                    ci.drawHex(i, j, g2, new Color(205, 97, 139));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        Content checker = map[i][j].getContents();

                        switch (checker){

                            case REDHILL:
                                ci.drawHex(i, j, g2, Color.RED);
                                break;
                            case BLACKHILL:
                                ci.drawHex(i, j, g2, Color.BLACK);
                                break;
                            case ROCKY:
                                ci.drawHex(i, j, g2, Color.gray);
                                break;
                            case NINE:
                                ci.drawHex(i, j, g2, new Color(1, 138, 5));
                                break;
                            case EIGHT:
                                ci.drawHex(i, j, g2, new Color(2, 171, 23));
                                break;
                            case SEVEN:
                                ci.drawHex(i, j, g2, new Color(1, 208, 31));
                                break;
                            case SIX:
                                ci.drawHex(i, j, g2, new Color(0, 238, 38));
                                break;
                            case FIVE:
                                ci.drawHex(i, j, g2, new Color(114, 255, 118));
                                break;
                            case FOUR:
                                ci.drawHex(i, j, g2, new Color(132, 232, 134));
                                break;
                            case THREE:
                                ci.drawHex(i, j, g2, new Color(147, 255, 156));
                                break;
                            case TWO:
                                ci.drawHex(i,j, g2, new Color(0, 102, 255));
                                break;
                            case ONE:
                                ci.drawHex(i, j, g2, new Color(238, 13, 0));
                                break;
                            default:
                                break;
                        }



                    }
                }
            }
        }
//
    }






    private void createLoadPanel() {

        //lists for storing input from user
        inputBrains = new HashMap<String, File>();
        playerNamesOrder = new ArrayList<String>();

        n1 = new JTextField();
        n1.setText("player1");

        n2 = new JTextField();
        n2.setText("player2");

        n3 = new JTextField();
        n3.setText("player3");

        n4 = new JTextField();
        n4.setText("player4");

        playerNames = new HashMap<Integer, String>();

        //local variable for checking the brain
        final Map m = new Map();
        final Colony c = new Colony(Colour.BLACK);
        final Brain brain = new Brain(m, c);


        //a label for instructions
        instruction = new JLabel("Players, enter your names and load brain for your ants ! ", SwingConstants.CENTER);
        final JLabel p1BrainStatus = new JLabel("Player 1's Brain : Not loaded", SwingConstants.CENTER);
        final JLabel p2BrainStatus = new JLabel("Player 2's Brain : Not loaded", SwingConstants.CENTER);
        final JLabel p3BrainStatus = new JLabel("Player 3's Brain : Not loaded", SwingConstants.CENTER);
        final JLabel p4BrainStatus = new JLabel("Player 4's Brain : Not loaded", SwingConstants.CENTER);
        final JLabel loadMapStatus = new JLabel("No Custom Map Loaded", SwingConstants.CENTER);


        //initialize booleans
        p1Ready = false;
        p2Ready = false;
        p3Ready = false;
        p4Ready = false;

        //setting up layout and menuPanel
        loadPanel = new JPanel();
        //getContentPane().add(menuPanel);
        loadPanel.setLayout(new GridLayout(15, 1));


        //make load map BUTTON !!!!
        final JButton loadMap = new JButton("Load Your Map Here");
        loadMap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {


                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.setDialogTitle("Load Map");


                Map testMap = new Map();


                //checking 1.if brain is correct
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION &&  testMap.loadMap(chooser.getSelectedFile())==true ) {

                    loadMapStatus.setText("Loaded Map : [ " + chooser.getName(chooser.getSelectedFile()) + " ]is loaded.");
                    customMap = chooser.getSelectedFile() ;



                } else if (testMap.loadMap(chooser.getSelectedFile())==false)
                {
                    loadMapStatus.setText("Your Map is not working, retry! ");
                }



            }
        });

        loadMap.setEnabled(false);


        //make new load brain button for player 1
        final JButton player1 = new JButton("Load Player 1's Brain");
        player1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String name = n1.getText();
                if (!playerNames.containsValue(name)) {


                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("."));
                    chooser.setDialogTitle("Brain 1");

                    //checking 1.if brain is correct
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && brain.loadBrain(chooser.getSelectedFile())) {

                        p1BrainStatus.setText(name + " 's Brain : [" + chooser.getName(chooser.getSelectedFile()) + " ]is loaded.");
                        inputBrains.put(name, chooser.getSelectedFile());
                        p1Ready = true;
                        playerNames.put(1, name);

                    } else {
                        p1BrainStatus.setText("Your Brain is not working, retry! ");
                    }
                } else {
                    p1BrainStatus.setText("Duplicated names, retry ! ");

                }


            }
        });

        //load brain button for player 2
        JButton player2 = new JButton("Load Player 2's Brain");
        player2.setPreferredSize(new Dimension(10, 10));
        player2.setBounds(50, 60, 80, 30);
        player2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {


                String name = n2.getText();
                if (!playerNames.containsValue(name)) {


                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("."));
                    chooser.setDialogTitle("Brain 2");


                    //checking if brain is correct
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && brain.loadBrain(chooser.getSelectedFile())) {

                        p2BrainStatus.setText(name + " 's Brain : [" + chooser.getName(chooser.getSelectedFile()) + " ]is loaded.");
                        inputBrains.put(name, chooser.getSelectedFile());
                        p2Ready = true;
                        playerNames.put(2, name);


                    } else {
                        p2BrainStatus.setText("Your Brain is not working, retry! ");
                    }
                } else {
                    p2BrainStatus.setText("Duplicated names, retry ! ");

                }

            }
        });


        //load brain button for player 3
        final JButton player3 = new JButton("Load Player 3's Brain");
        player3.setPreferredSize(new Dimension(10, 10));
        player3.setBounds(50, 60, 80, 30);
        player3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                String name = n3.getText();
                if (!playerNames.containsValue(name)) {


                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("."));
                    chooser.setDialogTitle("Brain 3");


                    //checking if brain is correct
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && brain.loadBrain(chooser.getSelectedFile())) {

                        p3BrainStatus.setText(name + " 's Brain : [" + chooser.getName(chooser.getSelectedFile()) + " ]is loaded.");
                        inputBrains.put(name, chooser.getSelectedFile());
                        p3Ready = true;
                        playerNames.put(3, name);


                    } else {
                        p3BrainStatus.setText("Your Brain is not working, retry! ");
                    }
                } else {
                    p3BrainStatus.setText("Duplicated names, retry ! ");

                }

            }
        });


        //load brain button for player 4
        final JButton player4 = new JButton("Load Player 4's Brain");
        player4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                String name = n4.getText();
                if (!playerNames.containsValue(name)) {


                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("."));
                    chooser.setDialogTitle("Brain 4");


                    //checking if brain is correct
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && brain.loadBrain(chooser.getSelectedFile())) {

                        p4BrainStatus.setText(name + " 's Brain : [" + chooser.getName(chooser.getSelectedFile()) + " ]is loaded.");
                        inputBrains.put(name, chooser.getSelectedFile());
                        p4Ready = true;
                        playerNames.put(4, name);
                        System.out.println(chooser.getSelectedFile());

                    } else {
                        p4BrainStatus.setText("Your Brain is not working, retry! ");
                    }
                } else {
                    p4BrainStatus.setText("Duplicated names, retry ! ");

                }

            }
        });

        //make radio buttons GROUPS
        ButtonGroup gameButtonsGroup = new ButtonGroup();
        ButtonGroup mapButtonsGroup = new ButtonGroup();


        //load map radio buttons
        final JRadioButton randomMapButton = new JRadioButton("Random World");
        randomMapButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                isRandomWorld = true;
                loadMap.setEnabled(false);

            }
        });

        final JRadioButton customMapButton = new JRadioButton("Custom World");
        customMapButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                isRandomWorld = false;
                loadMap.setEnabled(true);


            }
        });

        randomMapButton.setSelected(true);
        isRandomWorld = true;
        mapButtonsGroup.add(randomMapButton);
        mapButtonsGroup.add(customMapButton);


        JRadioButton singleGameButton = new JRadioButton("Single Game Mode");
        singleGameButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                isSingleGame = true;
                player3.setEnabled(false);
                player4.setEnabled(false);
                p3BrainStatus.setEnabled(false);
                p4BrainStatus.setEnabled(false);

                //make new brain list
                inputBrains = new HashMap<String, File>();
                playerNames = new HashMap<Integer, String>();
                p1BrainStatus.setText("Player 1's Brain : Not loaded");
                p2BrainStatus.setText("Player 2's Brain : Not loaded");
                p3BrainStatus.setText("Player 3's Brain : Not loaded");
                p4BrainStatus.setText("Player 4's Brain : Not loaded");
                p1Ready = false;
                p2Ready = false;
                p3Ready = false;
                p4Ready = false;

                customMapButton.setEnabled(true);


            }
        });

        //set defalt button as single game
        singleGameButton.setSelected(true);
        isSingleGame = true;
        player3.setEnabled(false);
        player4.setEnabled(false);
        p3BrainStatus.setEnabled(false);
        p4BrainStatus.setEnabled(false);

        JRadioButton tournamentGameButton = new JRadioButton("Tournament Mode");
        tournamentGameButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                isSingleGame = false;
                player3.setEnabled(true);
                player4.setEnabled(true);
                p3BrainStatus.setEnabled(true);
                p4BrainStatus.setEnabled(true);

                loadMap.setEnabled(false);
                randomMapButton.setSelected(true);
                isRandomWorld = true ;
                customMapButton.setEnabled(false);


                //make new brain list
                inputBrains = new HashMap<String, File>();
                playerNames = new HashMap<Integer, String>();
                p1BrainStatus.setText("Player 1's Brain : Not loaded");
                p2BrainStatus.setText("Player 2's Brain : Not loaded");
                p3BrainStatus.setText("Player 3's Brain : Not loaded");
                p4BrainStatus.setText("Player 4's Brain : Not loaded");

                p1Ready = false;
                p2Ready = false;
                p3Ready = false;
                p4Ready = false;


            }
        });
        gameButtonsGroup.add(singleGameButton);
        gameButtonsGroup.add(tournamentGameButton);

        JButton makeFixtures = new JButton("Create Games");
        makeFixtures.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                //make fixtures
                int k = 0;
                String bu = "b";

                if (isRandomWorld || (!isRandomWorld && customMap!=null)) {


                    if (isSingleGame && p1Ready && p2Ready) {
                        instruction.setText("Games is ready to start !");
                        gameStatus.setText("Single Game is Ready.");

                        fixtures = controller.createFixtures(inputBrains, tournament);
                        startGame.setEnabled(true);

                        tab.setEnabledAt(1, true);
                        tab.setSelectedIndex(1);

                        if(isRandomWorld == true){

                        }else{ // Custom map

                        }
                    } else {
                        if (!isSingleGame && p1Ready && p2Ready && p3Ready && p4Ready) {
                            for (int a = 0; a < playerNames.size() + 2; a++) {
                                for (int b = a + 1; b < playerNames.size(); b++) {
                                    System.out.println(playerNames.get(a + 1) + " " + playerNames.get(b + 1));
                                    Blist.get(k).setText(playerNames.get(a + 1) + " vs " + playerNames.get(b + 1));
                                    Blist.get(k).setEnabled(true);
                                    System.out.println(k);
                                    k++;
                                }
                            }
                            gameStatus.setText("4 Player Tournament ready.");

                            tab.setEnabledAt(1, true);

                            tab.setSelectedIndex(1);
                            fixtures = controller.createFixtures(inputBrains, tournament);


                        } else {
                            if (!isSingleGame && p1Ready && p2Ready && p3Ready) {
                                for (int a = 0; a < playerNames.size() + 2; a++) {
                                    for (int b = a + 1; b < playerNames.size(); b++) {
                                        System.out.println(playerNames.get(a + 1) + " " + playerNames.get(b + 1));
                                        Blist.get(k).setText(playerNames.get(a + 1) + " vs " + playerNames.get(b + 1));
                                        Blist.get(k).setEnabled(true);
                                        k++;
                                    }
                                }
                                gameStatus.setText("3 Player Tournament ready.");
                                tab.setEnabledAt(1, true);

                                tab.setSelectedIndex(1);
                                fixtures = controller.createFixtures(inputBrains, tournament);

                            } else {
                                instruction.setText("Please make sure all the brain is loaded!");
                                instruction.setForeground(Color.RED);

                            }
                        }


                    }
                }else{
                    instruction.setText("Please load your map or set it to random map !");
                    instruction.setForeground(Color.RED);

                }
            }
        });

        JButton clear = new JButton("Clear All");
        clear.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                //make fixtures

                playerNames = new HashMap<Integer, String>();
                inputBrains = new HashMap<String, File>();
                playerNamesOrder = new ArrayList<String>();
                p1BrainStatus.setText("Player 1's Brain : Not loaded");
                p2BrainStatus.setText("Player 2's Brain : Not loaded");
                p3BrainStatus.setText("Player 3's Brain : Not loaded");
                p4BrainStatus.setText("Player 4's Brain : Not loaded");
            }
        });

        JLabel blank = new JLabel("");

        loadPanel.add(randomMapButton);
        loadPanel.add(customMapButton);
        loadPanel.add(loadMapStatus);
        loadPanel.add(loadMap);

        loadPanel.add(singleGameButton);
        loadPanel.add(tournamentGameButton);

        loadPanel.add(instruction);
        loadPanel.add(blank);
        loadPanel.add(n1);
        loadPanel.add(player1);
        loadPanel.add(n2);
        loadPanel.add(player2);
        loadPanel.add(n3);
        loadPanel.add(player3);
        loadPanel.add(n4);
        loadPanel.add(player4);
        loadPanel.add(p1BrainStatus);
        loadPanel.add(p2BrainStatus);
        loadPanel.add(p3BrainStatus);
        loadPanel.add(p4BrainStatus);
        loadPanel.add(makeFixtures);
        loadPanel.add(clear);


        setTitle("Please load your brains");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}

