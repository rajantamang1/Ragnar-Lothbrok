package com.game;

import com.util.CombatEngine;
import com.util.TextParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.util.Statistics.setMessage;

public class ApplicationRenderer{

    JPanel titlePanel,backgroundPanel, startBtnPanel, mainTxtPanel,
            playerHeaderPanel, inputTextPanel, inventoryPanel;
    JLabel titleLabel, backgroundLabel,
            soldierCountLabel,
            weaponCountLabel,
            currentCountryLabel, currentCountryValueLabel,
            inputTextFieldLabel, inventoryLabel, inventoryValueLabel;

    public static JFrame window;
    JFrame map;
    Container con;
    //Font styling
    Font titleFont = new Font("Times New Roman", Font.BOLD,70);
    Font btnFont = new Font("Times New Roman", Font.PLAIN,30);
    Font textAreaFont = new Font("Times New Roman", Font.PLAIN,22);
    Font playerHeaderFont = new Font("Times New Roman", Font.PLAIN,22);
    JButton startButton, inputButton, showMapButton;
    ImageIcon backgroundImage;
    JTextField inputTextField;

    public static JTextArea mainTxtArea;
    public static JLabel soldierCountNumberLabel, weaponCountNumberLabel,
            vicIreland,vicRussia,vicSweden,vicDenmark,vicFrance,vicNorthum, victorySign;

    //instantiating classes
    TitleScreenRenderer tSR = new TitleScreenRenderer();
    ProcessInput pInput = new ProcessInput();
    DisplayMap dMap = new DisplayMap();

    Country country = new Country();
    public static Set<String> defeatedCountry = new HashSet<>();
    CombatEngine combatEngine = new CombatEngine();

    String curCountry = "kattegat";
    Map<String, String> inventoryMap = new HashMap<>();
    Set<String> availableInventory = new HashSet<>();
    Set<String> availableInventoryKey = new HashSet<>();
    Set<String> availableDirections = new HashSet<>();
    Set<Node> inspectableItems = new HashSet<>();
    public static final String[] directionList = {"east","west", "north", "south"};

    //Accessing XML file
    File file = new File("Ragnar-lothbrok/files/test.xml");
    File synonymFile = new File("Ragnar-lothbrok/files/synonyms.xml");
    DocumentBuilderFactory myBuilder = DocumentBuilderFactory.newInstance();
    DocumentBuilder myDocBuilder;
    Document document;
    Document synonymDocument;
    {
        try {
            myDocBuilder = myBuilder.newDocumentBuilder();
            //text parser for synonym file
            synonymDocument = myDocBuilder.parse(synonymFile);
            //main document
            document = myDocBuilder.parse(file);
            document.getDocumentElement().normalize();
            synonymDocument.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    //getters
    public static JTextArea getMainTxtArea() {
        return mainTxtArea;
    }
    public static JLabel getSoldierCountNumberLabel() {
        return soldierCountNumberLabel;
    }
    public static JLabel getWeaponCountNumberLabel() {
        return weaponCountNumberLabel;
    }

    private Clip clip;
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new ApplicationRenderer();
    }

    //Constructor
    public ApplicationRenderer() {
        clip = playSound();
        //design window
        window = new JFrame("Ragnar Lothbrok: We shall drink with Odin one day !");
        window.setSize(1050, 920);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);

        //container
        con = window.getContentPane();

        //title label design
        titleLabel = new JLabel("RAGNAR LOTHBROK");
        titleLabel.setForeground(Color.lightGray);
        titleLabel.setFont(titleFont);

        // title panels design
        titlePanel = new JPanel();
        titlePanel.setBounds(200, 100, 920, 125);
        //titlePanel.setBackground(Color.black);
        //setting its transparency
        titlePanel.setOpaque(false);

        //add label to panel
        titlePanel.add(titleLabel);
        //add panel to container
        con.add(titlePanel);

        //background panel designs
        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(0, 0, window.getWidth(), window.getHeight());

        //background label design
        backgroundLabel = new JLabel();
        backgroundLabel.setSize(backgroundPanel.getWidth(),backgroundPanel.getHeight());
        //fetching image icon from device
        backgroundImage = new ImageIcon(new ImageIcon("Ragnar-lothbrok/files/rag.jpg")
                .getImage().getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH));
        backgroundLabel.setIcon(backgroundImage);
        backgroundPanel.add(backgroundLabel);
        //add panel to container
        con.add(backgroundPanel);

        //start button panel designs
        startBtnPanel = new JPanel();
        startBtnPanel.setBounds(800,400,200,80);
        //startBtnPanel.setBackground(Color.DARK_GRAY);
        //setting it's transparency
        startBtnPanel.setOpaque(false);
        //adding start button panel to background label on top of background panel
        backgroundLabel.add(startBtnPanel);

        //start button design
        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.black);
        startButton.setFont(btnFont);
        //adding event listener to the button
        startButton.addActionListener(tSR);
        startBtnPanel.add(startButton);
        startButton.setFocusPainted(false); //removes extra box in the button
        //rendering the window.
        window.setVisible(true);
    }

    //New Game screen method
    public void createGameScreen(){

        //making panels invisible
        titlePanel.setVisible(false);
        startButton.setVisible(false);
        backgroundPanel.setVisible(false);
        clip.stop();
        con.setBackground(Color.CYAN);

        //main text panel design
        mainTxtPanel = new JPanel();
        mainTxtPanel.setBounds(100,145,800,300);
        mainTxtPanel.setBackground(Color.gray);
        // adding panel to container
        con.add(mainTxtPanel);
        //main text area design
        mainTxtArea = new JTextArea();
        setMessage("You are King Ragnar Lothbrok. \n" +
                "You dream to sail west and raid the western countries.\n" +
                "Your goal is to invade and rule over all possible countries");
        mainTxtArea.setBounds(100,145,800,300);
        mainTxtArea.setBackground(Color.GREEN);
        mainTxtArea.setForeground(Color.BLACK);
        mainTxtArea.setFont(textAreaFont);
        mainTxtArea.setLineWrap(true);
        //add text area to panel
        mainTxtPanel.add(mainTxtArea);

        //header panel design
        playerHeaderPanel = new JPanel();
        playerHeaderPanel.setBounds(100,15,800,50);
        playerHeaderPanel.setBackground(Color.blue);
        playerHeaderPanel.setLayout(new GridLayout(1,4));
        //add header panel to container
        con.add(playerHeaderPanel);

        //header label design
        currentCountryLabel = new JLabel(" Country: ");
        currentCountryLabel.setFont(playerHeaderFont);
        currentCountryLabel.setForeground(Color.white);
        //add label to panel
        playerHeaderPanel.add(currentCountryLabel);

        currentCountryValueLabel = new JLabel("Kattegat");
        currentCountryValueLabel.setFont(playerHeaderFont);
        currentCountryValueLabel.setForeground(Color.white);
        playerHeaderPanel.add(currentCountryValueLabel);

        soldierCountLabel = new JLabel("Soldier# :");
        soldierCountLabel.setFont(playerHeaderFont);
        soldierCountLabel.setForeground(Color.white);
        //add label to panel
        playerHeaderPanel.add(soldierCountLabel);

        soldierCountNumberLabel = new JLabel("800");
        soldierCountNumberLabel.setFont(playerHeaderFont);
        soldierCountNumberLabel.setForeground(Color.white);
        playerHeaderPanel.add(soldierCountNumberLabel);

        weaponCountLabel = new JLabel("Weapon: ");
        weaponCountLabel.setFont(playerHeaderFont);
        weaponCountLabel.setForeground(Color.white);
        //add label to panel
        playerHeaderPanel.add(weaponCountLabel);

        weaponCountNumberLabel = new JLabel("300");
        weaponCountNumberLabel.setFont(playerHeaderFont);
        weaponCountNumberLabel.setForeground(Color.white);
        playerHeaderPanel.add(weaponCountNumberLabel);

        //header panel design
        inventoryPanel = new JPanel();
        inventoryPanel.setBounds(100,80,800,50);
        inventoryPanel.setBackground(Color.blue);
        //inventoryPanel.setLayout(new GridLayout(1,2));
        //add header panel to container
        con.add(inventoryPanel);

        inventoryLabel = new JLabel("Possessions : ");
        //inputTextFieldLabel.setBounds(100,565,200,50);
        inventoryLabel.setFont(playerHeaderFont);
        inventoryLabel.setForeground(Color.white);
        inventoryPanel.add(inventoryLabel);

        inventoryValueLabel = new JLabel();
        inventoryValueLabel.setFont(playerHeaderFont);
        inventoryValueLabel.setForeground(Color.white);
        inventoryPanel.add(inventoryValueLabel);

        inputTextPanel = new JPanel();
        inputTextPanel.setBounds(100,565,800,50);
        inputTextPanel.setBackground(Color.blue);
        inputTextPanel.setLayout(new GridLayout(1,3));
        //inputTextPanel.setLayout(new GridLayout(1,4));
        //add header panel to container
        con.add(inputTextPanel);

        inputTextFieldLabel = new JLabel("  Next move: ");
        inputTextFieldLabel.setBounds(100,565,200,50);
        inputTextFieldLabel.setFont(textAreaFont);
        inputTextFieldLabel.setForeground(Color.white);
        inputTextPanel.add(inputTextFieldLabel);

        inputTextField =new JTextField();
        inputTextField.setBounds(400, 565, 500,50);
        inputTextField.setBackground(Color.GREEN);
        inputTextField.setFont(textAreaFont);
        inputTextPanel.add(inputTextField);

        inputButton = new JButton("Commit");
        inputButton.setBounds(400, 565, 100,50);
        inputButton.setFont(btnFont);
        inputButton.setBackground(Color.black);
        inputButton.setForeground(Color.black);
        //adding event listener to the button
        inputButton.addActionListener(pInput);

        //Enter key configured to process the input
        inputTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    makeNextMove();
                    inputTextField.setText("");
                }
            }
        });

        inputTextPanel.add(inputButton);
        inputButton.setFocusPainted(false); //removes extra box in the button

        //Designing button for viewing map
        showMapButton = new JButton("Show Map");
        showMapButton.setBounds(400, 565, 100,50);
        showMapButton.setFont(btnFont);
        showMapButton.setBackground(Color.black);
        showMapButton.setForeground(Color.black);
        //adding event listener to the button
        showMapButton.addActionListener(dMap);
        //adding button to the main panel
        inputTextPanel.add(showMapButton);
        showMapButton.setFocusPainted(false); //removes extra box in the button

        // panel to display available commands
        JPanel commandPanel = new JPanel();
        commandPanel.setBounds(150,645,700,200);
        commandPanel.setBackground(Color.blue);
        commandPanel.setLayout(new GridLayout(6,1));
        con.add(commandPanel);

        JLabel commandLabel = new JLabel("     Commands : ");
        commandLabel.setFont(playerHeaderFont);

        commandLabel.setForeground(Color.white);
        commandPanel.add(commandLabel);

        JLabel goCommands = new JLabel("    To Travel: sail, travel, go, proceed, move [East, West, North, South]");
        goCommands.setFont(playerHeaderFont);
        goCommands.setForeground(Color.white);
        commandPanel.add(goCommands);

        JLabel attackCommands = new JLabel("    To Attack: invade, attack, engage, destroy, fight, annihilate");
        attackCommands.setFont(playerHeaderFont);
        attackCommands.setForeground(Color.white);
        commandPanel.add(attackCommands);

        JLabel pickCommands = new JLabel("    To Pick: pick, choose, select, collect [items]");
        pickCommands.setFont(playerHeaderFont);
        pickCommands.setForeground(Color.white);
        commandPanel.add(pickCommands);

        JLabel lookCommands = new JLabel("    To Look: view, overlook, observe, look, overview");
        lookCommands.setFont(playerHeaderFont);
        lookCommands.setForeground(Color.white);
        commandPanel.add(lookCommands);

        JLabel inspectCommands = new JLabel("    To Inspect: inspect, investigate, examine, check, monitor, study [items]");
        inspectCommands.setFont(playerHeaderFont);
        inspectCommands.setForeground(Color.white);
        commandPanel.add(inspectCommands);

    }
    //method that populates the map on a button click
    private void show(){

        //Designing a map frame
        map = new JFrame();
        map.setSize(400, 400);
        map.setResizable(false);
        map.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        map.getContentPane().setBackground(Color.BLACK);
        map.setLayout(null);

        //Instantiating a container for map
        Container mapCon = map.getContentPane();

        //background panel designs
        JPanel mapBackgroundPanel = new JPanel();
        mapBackgroundPanel.setBounds(0,0, map.getWidth(), map.getHeight());

        //setting background label with map as image icon
        JLabel mapBackgroundLabel = new JLabel();
        mapBackgroundLabel.setSize(map.getWidth(),map.getHeight());
        //fetching image icon from device
        ImageIcon mapImage = new ImageIcon(new ImageIcon("Ragnar-lothbrok/files/map.png")
                .getImage().getScaledInstance(map.getWidth()-5, map.getHeight()-5, Image.SCALE_SMOOTH));
        mapBackgroundLabel.setIcon(mapImage);
        mapBackgroundPanel.add(mapBackgroundLabel);

        //victory picture
        ImageIcon victoryImage = new ImageIcon(new ImageIcon("Ragnar-lothbrok/files/victory.png")
                .getImage().getScaledInstance(50,30, Image.SCALE_SMOOTH));

        //Russia victory label
        vicRussia = new JLabel();
        vicRussia.setIcon(victoryImage);
        vicRussia.setBounds(310, 110, 50,30);
        mapBackgroundLabel.add(vicRussia);
       vicRussia.setVisible(false);
        if(defeatedCountry.contains("russia")){
            vicRussia.setVisible(true);
        }

        //Sweden victory Label
        vicSweden = new JLabel();
        vicSweden.setIcon(victoryImage);
        vicSweden.setBounds(200, 105, 50,30);
        mapBackgroundLabel.add(vicSweden);
        vicSweden.setVisible(false);
        System.out.println(defeatedCountry);
        if(defeatedCountry.contains("sweden")){
            vicSweden.setVisible(true);
        }

        //Denmark victory Label
        vicDenmark = new JLabel();
        vicDenmark.setIcon(victoryImage);
        vicDenmark.setBounds(125, 175, 50,30);
        mapBackgroundLabel.add(vicDenmark);
        vicDenmark.setVisible(false);
        if(defeatedCountry.contains("denmark")){
            vicDenmark.setVisible(true);
        }

        //France victory Label
        vicFrance = new JLabel();
        vicFrance.setIcon(victoryImage);
        vicFrance.setBounds(110, 265, 50,30);
        mapBackgroundLabel.add(vicFrance);
        vicFrance.setVisible(false);
        if(defeatedCountry.contains("france")){
            vicFrance.setVisible(true);
        }

        //Northumbria victory Label
        vicNorthum = new JLabel();
        vicNorthum.setIcon(victoryImage);
        vicNorthum.setBounds(30, 225, 50,30);
        mapBackgroundLabel.add(vicNorthum);
        vicNorthum.setVisible(false);
        if(defeatedCountry.contains("northumbria")){
            vicNorthum.setVisible(true);
        }

        //Ireland victory Label
        vicIreland = new JLabel();
        vicIreland.setIcon(victoryImage);
        vicIreland.setBounds(5, 175, 50,30);
        mapBackgroundLabel.add(vicIreland);
        vicIreland.setVisible(false);
        if(defeatedCountry.contains("ireland")){
            vicIreland.setVisible(true);
        }
        //add background panel to container
        mapCon.add(mapBackgroundPanel);
        map.setLocation(showMapButton.getLocationOnScreen().x-400,showMapButton.getLocationOnScreen().y-400);
        //make map frame visible
        map.setVisible(true);
    }

    public class TitleScreenRenderer implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            createGameScreen();
        }
    }

    public class ProcessInput implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            makeNextMove();
            inputTextField.setText("");
        }
    }
    public class DisplayMap implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            show();
        }
    }

    private Set<String> getSynonymSet(String synonymType) {
        Set <String> set = new HashSet<>();
        String [] goList = TextParser.getValueByType(synonymDocument, "synonyms", synonymType)
                .get(0)
                .getTextContent()
                .split(",");

        set.addAll(Arrays.asList(goList));
        return set;
    }

    //Added sound clip
    private static Clip playSound(){
        Clip result = null;
        try {
            File audioFile = new File("Ragnar-lothbrok/files/theme_music_vikings.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.open(audioInputStream);
            audioClip.start();
            result = audioClip;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return  result;
    }


    //game logic
    public void makeNextMove() {

        //adding list of direction to the HashSet
        availableDirections.addAll(Arrays.asList(directionList));
        //retrieving list of items to pick from country and store in variable
        String[] availableList= country.look(document).split(",");
        availableInventory.addAll(Arrays.asList(availableList));

        inspectableItems.addAll(TextParser.getValueByType(document, curCountry, "inspectable"));
        //converting lambda into string
        inspectableItems.forEach(item-> System.out.println(item.getTextContent()));

        String userInput = inputTextField.getText();

            if(userInput.equals("")) {
                mainTxtArea.setText("Invalid input");
            }else{
                userInput = userInput.toLowerCase();
                String[] inputArray = userInput.split(" ", 2);
                String itemsVisible = "";
                if (getSynonymSet("goset").contains(inputArray[0])) {
                    String[] neighbors = {};
                    Node countryChild =null;
                    if(!inventoryMap.containsKey("boat")){
                        countryChild= (TextParser.getValueByType(document, curCountry, "neighbors")).get(0);
                    }
                    if(countryChild != null){
                        neighbors = countryChild.getTextContent().split(",");
                        System.out.println(neighbors[0] + " " + neighbors[1]);
                    }

                    Set<String> neighborsSet = new HashSet<>(Arrays.asList(neighbors));
                    if(inputArray.length == 2 && availableDirections.contains(inputArray[1])) {
                        String travelCountry = TextParser.getValueByType(document, curCountry, inputArray[1]).get(0).getTextContent();
                        if (inventoryMap.containsKey("boat") | neighborsSet.contains(travelCountry)){
                            curCountry = country.sail(inputArray[1], document);
                            country.setNameOfCountry(curCountry);
                            currentCountryValueLabel.setText(String.valueOf(curCountry.charAt(0)).toUpperCase()+ curCountry.substring(1));
                            if(curCountry.equals("iceland")){
                                JOptionPane.showMessageDialog(ApplicationRenderer.window,"You and your soldiers have been frozen to death... You lost !!");
                                System.exit(0);
                            }
                            if(!defeatedCountry.contains(curCountry)){
                                setMessage(TextParser.getValueByType(document,curCountry,"entrymessage").get(0).getTextContent());
                            }else{
                                setMessage("This is your territory now..");
                            }

                        }else if(!neighborsSet.contains(travelCountry)){
                            curCountry = country.getNameOfCountry();
                            System.out.println(curCountry);
                            setMessage("Missing Inventory to travel");
                        }else{
                            setMessage("Not working");
                        }
                    }else{
                        setMessage("Need valid direction !");
                    }
                } else if (getSynonymSet("lookset").contains(inputArray[0])){
                     String visibleItems = country.look(document);
                    setMessage("You can see ["+ visibleItems + "]");
                } else if (getSynonymSet("pickset").contains(inputArray[0])&& curCountry.equals("kattegat")) {

                    availableInventory.forEach(a->availableInventoryKey.add(a.split(" ")[0]));
                    if(inputArray.length == 2 && availableInventoryKey.contains(inputArray[1])) {
                        if(!inputArray[1].equals("houses")){
                            String[] keyValue = country.getItem(inputArray[1], curCountry,document).split(",", 2);
                            setMessage(keyValue[1] +" "+ keyValue[0] + "s picked");
                            inventoryMap.put(keyValue[0], keyValue[1]);
                            //setMessage("Your possessions: " + inventoryMap);
                            inventoryValueLabel.setText(inventoryMap.toString());
                        }else{
                            setMessage("Cannot pick " + inputArray[1]);
                        }
                    }else{
                        setMessage("Pick from inventory list: "+ availableInventory);
                    }
                } else if (getSynonymSet("inspectset").contains(inputArray[0])){
                    if(inputArray[1].equals("houses")){
                        String inspectedValues =country.inspect(inputArray[1], curCountry,document);
                        setMessage("You found "+ inspectedValues);
                    }else{
                        setMessage("You can't inspect" + inputArray[1]);
                    }

                }else if (getSynonymSet("attackset").contains(inputArray[0])) {

                    if(!(curCountry.equals("kattegat") || curCountry.equals("iceland"))){
                        String countryAttacked = "";

                        if(!defeatedCountry.contains(curCountry)){
                            setMessage("The battle is about to begin.;Bloods are about to be shattered everywhere.;The sound of soldiers marching can be heard from distance.");
                           // try {
                                //Thread.sleep(3000);
                                countryAttacked = country.attack(curCountry,document);
                                defeatedCountry.add(countryAttacked);
                            //} catch (InterruptedException e) {
                             //   e.printStackTrace();
                            //}
                        }else{
                            setMessage(curCountry + " already defeated");
                            setMessage(TextParser.getValueByType(document,curCountry,"ragnarsoldiers").get(0).getTextContent());
                        }
                    }else {
                        setMessage("you cannot attack!!");
                    }
                }else if (inputArray[0].equals("flee")){
                    String fleeCountry = "";
                    for(int i = 0; i < 3; i++){
                        fleeCountry = TextParser.getValueByType(document,curCountry,directionList[i]).get(0)
                                .getTextContent();
                        if(!fleeCountry.equals("nowhere")){
                            curCountry = fleeCountry;
                            break;
                        }
                    }
                    country.flee(document);
                    country.setNameOfCountry(curCountry);
                    currentCountryValueLabel.setText(String.valueOf(curCountry.charAt(0)).toUpperCase()+ curCountry.substring(1));
                }else{
                    setMessage("Doesn't seem to work");
                }
                System.out.println(defeatedCountry);
                if(defeatedCountry.size()==6){
                    JOptionPane.showMessageDialog(ApplicationRenderer.window,"You have conquered all the nations. All Hail! King Ragnar. You are the mighty King of the Kings.");
                    System.exit(0);
                }
        }
    }
}