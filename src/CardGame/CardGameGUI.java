package CardGame;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * <pre>
 * File         CardGameGUI.java
 * Project      24 Points Card Game
 * Description  A class representing the GUI used in a 24 points card game
 * Platform     jdk 1.8.0_77; NetBeans IDE 8.2; Mac Os X
 * Course       CS 143, SCC
 * Hours        7 hours
 * Created on   Feb 16 2017
 </pre>
 *
 * @author:	siyu.pan
 * @see         java.util.ArrayList
 */

public class CardGameGUI extends javax.swing.JFrame {
    /**
     * ArrayList of the player objects.
     */
    private ArrayList<Player> players = new ArrayList<>();
    
    /**
     * A DeckOfCards object containing all 52 cards.
     */
    final DeckOfCards deck = new DeckOfCards();
    
    /**
     * An array of Card objects containing 4 cards.
     */
    Card[] cards = new Card[4];

    /**
     * external file name of players.
     */
    private final String fileName = "src/CardGame/Players.txt";
    
    /**
     * The time limit for answer an expression correctly.
     */
    final static int TIME_LIMIT = 60000;
    
    /**
     * the time countdown.
     */
    int timeLeft;
    
    /**
     * Timer obkect that send an action event every one second.
     */
    Timer timer= new Timer(1000, new MyTimerActionListener());
    
    /**
     * Boolean object to keep track of if the game is still going on or not.
     */
    boolean inGame = true;
    /**
     * Creates new form CardGameGUI
     */
    public CardGameGUI() {
        initComponents();
        //set buttonAdd as default
        this.getRootPane().setDefaultButton(verifyJButton);
        //set icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/CardGame/Poker/Heart1.jpg"));
        
        //centers the form at start.
        this.setLocationRelativeTo(null);
        
        // Read form an external file Ballets.txt and create an
        // ArrayList of Ballet objects, cities        
        readFromFile(fileName);
        
        findSolutionJButton.setEnabled(false);
        
        //prompt the user to input name
        String name = null;
        while(name == null || name.length() <= 0)
            name = JOptionPane.showInputDialog("Please enter your name: ");
        int index;
        if(players.size() <= 0 || searchPlayers(name) == -1)//player doesn't exist already
        {
            try {
                JOptionPane.showMessageDialog(null,"Hi " + name +". It's the first time you play 24 points card game.", "Welcome!", JOptionPane.INFORMATION_MESSAGE);
                //create a new player
                players.add(new Player(name));  
                savePlayers();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(CardGameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else//if the player already exist
        {     
            JOptionPane.showMessageDialog(null,"Hi " + name +". Welcome back!", "Welcome back", JOptionPane.INFORMATION_MESSAGE);
        }
        
        //display the players name in the JList
        displayPlayers();
        //set the name input as the selected player
        playersJList.setSelectedIndex(searchPlayers(name));
        //display the 4 cards drawn       
        displayCards();
        
        //start the timer countdown
        timer.start(); 
        timeLeft = TIME_LIMIT;
    }
    
    class MyTimerActionListener implements ActionListener 
    {
        /**
        * The actionEvent sent by the timer object. 
        * <p>
        * Everytime the event is sent, the remaining time will decrese by 1 s. 
        * <br>If the remaining time is less than or equal to 0, a message will be shown
        * to show the player failed
        * <p>
        * @param  e the action event sent be the timer. 
        */
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            //decrease the coundown timer for 1s
            timeLeft-= 1000;
            SimpleDateFormat df=new SimpleDateFormat("mm:ss");
            timeJLabel.setText(df.format(timeLeft));
            
            //if there is no time left, show the player fails
            if(timeLeft <= 0)
            {
                try 
                {
                    //stop the timer
                    timer.stop();
                    
                    //create a new player object with new avarage time and number of game
                    Player oldPlayer = players.get(playersJList.getSelectedIndex());
                    Player newPlayer = new Player(oldPlayer.getName(), oldPlayer.getNumber() + 1 , oldPlayer.getNumberOfSuccess(), oldPlayer.getTime());
                    
                    //replace the old player with the new one
                    players.set(playersJList.getSelectedIndex(), newPlayer);
                    savePlayers();
                    
                    JOptionPane.showMessageDialog(null, "Time up! You failed.", "Time Up", JOptionPane.WARNING_MESSAGE);
                    findSolutionJButton.setEnabled(true);
                    inGame = false;
                    verifyJButton.setEnabled(false);
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(CardGameGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * Reads players from a text file that is pipe (|) delimited and
     * creates an instance of the player class with the data read.
     * <p>
     * Then the newly created ballet is added to the ballets database.
     * Uses an object from the ReadFile class to read record.
     * <p>
     * @param file name of the text file
     * @see java.io.FileReader
     * @see players
     */
    public void readFromFile(String file)            
    {
        players.clear();
        try
        {
            FileReader inputFile = new FileReader(file);
            //read the first line
            try (BufferedReader input = new BufferedReader(inputFile)) {
                //read the first line
                String line = input.readLine();
                while(line != null)
                {
                    Player player = new Player();
                    StringTokenizer token = new StringTokenizer(line, "|");
                    while(token.hasMoreTokens())
                    {
                        player.setName(token.nextToken());
                        player.setNumber(Integer.parseInt(token.nextToken()));
                        player.setNumberOfSuccess(Integer.parseInt(token.nextToken()));
                        player.setPercent(Float.parseFloat(token.nextToken()));
                        player.setTime(Integer.parseInt(token.nextToken()));
                    }
                    //add player to the arraylist
                    players.add(player);
                    line = input.readLine();
                }
            }
        }
        catch(FileNotFoundException exp)
        {
            JOptionPane.showMessageDialog(null, file + " doesnot exist", "File Input Error", JOptionPane.WARNING_MESSAGE);
            //add JFileChooser to find the file
        }
        
        catch(IOException exp)
        {
            JOptionPane.showMessageDialog(null, file + " is corrupt", "File Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Displays Players in JList sorted by name using the insertion sort algorithm.
     * @see #insertionSort(java.util.ArrayList) 
     */
    private void displayPlayers()
    {
        int location = playersJList.getSelectedIndex();//index of the ballet selected
        String[] balletsList = new String[players.size()];
        //sort players using insertion sort by names only and dispaly in JList
        insertionSort(players);
        for(int i = 0; i < balletsList.length; i++)
        {
            balletsList[i] = players.get(i).getName();
        }
        playersJList.setListData(balletsList);
        if(location < 0)
            playersJList.setSelectedIndex(0);
        else
            playersJList.setSelectedIndex(location);
    }
    
    /**
     * Sorts ArrayList ballets in ascending order by year.
     * <p>
     * Uses the insertion sort algorithm which inserts ballet at correct 
     * position and shuffles everyone else below that position.
     * <p>
     * @param players the ArrayList of player objects
     * @see players
     */
    public void insertionSort(ArrayList<Player> players)
    {
        int i, j;
        for(i = 0; i < players.size(); i++)
        {
            Player temp = players.get(i);
            j = i - 1;
            while(j >= 0 && players.get(j).getName().compareToIgnoreCase(temp.getName()) > 0)
            {
                players.set(j+1, players.get(j));
                j--;
            }
            players.set(j + 1, temp);
        }
    }
    
    /**
     * Search for a specific player by its name in the arrayList 
     * players by going through the list linearily
     * @param playerName name of the player 
     * @see players
     */
    private int searchPlayers(String playerName)
    {
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getName().equalsIgnoreCase(playerName))
            {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * This method is called when a player is added or deleted to 
     * save the new players list in the text file.
     * <p>
     * Uses a FileWriter Object
     * <p>
     * pre-condition    Uses the textfile and te ArrayList ballets
     * post-condition   players ArrayList is saved in the text file
     * @param file name of the text file
     * @see java.io.FileWriter
     * @throws IOException
     */
    private void savePlayers() throws IOException
    {
        try
        {
            FileWriter fileW = new FileWriter(fileName, false);
            PrintWriter output = new PrintWriter(fileW);
            //loop thorugh and write all the players to file
            for(int i = 0; i < players.size(); i++)
            {
                String line = players.get(i).getName() + "|"
                        +players.get(i).getNumber()+ "|"
                        +players.get(i).getNumberOfSuccess() + "|"
                        +players.get(i).getPercent()+ "|"
                        +players.get(i).getTime() + "\n";  
                output.write(line);
            }
            output.close();
        }
        catch(IOException exp)
        {
            JOptionPane.showMessageDialog(null, fileName + " does not exist", "File input Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Search for a specific player by its name in the arrayList 
     * players by going through the list linearily
     * @param array the string array of names of the players
     * @param key the name of the player being searched
     * @return the index of the player. -1 if not found
     */    
    public int linearSearch(String[] array, String key)
    {
        for(int i = 0; i < array.length; i++)
        {
            if(array[i].toLowerCase().contains(key.toLowerCase()))
                return i;     
        }
        return -1; 
    }
    
    /**
     * Display the pictures of the 4 card object randomlt drawn from the deck of 52 cards
     * @see #deck
     * @see #cards
     */
    public void displayCards()
    {
        cards = deck.draw();
        card1JLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CardGame/Poker/" + cards[0].getImage())));   
        card2JLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CardGame/Poker/" + cards[1].getImage())));   
        card3JLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CardGame/Poker/" + cards[2].getImage())));   
        card4JLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CardGame/Poker/" + cards[3].getImage())));   
    }
    
    /**
     * Evaluate the result of a string expression containing +, -, *, / operations.
     * <p>
     * Create two GenericStack Objects to hold operators and operands.
     * <br>Call the insertBlanks and processAnOperator methods.
     * <p>
     * @param expression the string of expression
     * @return the result of the expression
     * @see GenericStack
     * @see #insertBlanks(java.lang.String) 
     * @see #processAnOperator(CardGame.GenericStack, CardGame.GenericStack) 
     */
    public int evaluate(String expression)
    {
        //create two stacks : operand and oparotor
        GenericStack<Integer> operand = new GenericStack<>();
        GenericStack<Character> operator = new GenericStack<>();
        
        //insert blanks around '+', '-', '*', '/'
        expression = insertBlanks(expression);
        //Extract operand and operator
        //Phase 1: scan the tokens
        String[] tokens = expression.split(" ");
        for(String token: tokens)
        {
            if(token.length() == 0)//blank space
                continue;
            else if((token.charAt(0) == '+')  || (token.charAt(0) == '-'))
            {
                //Process all +, - inside the stack
                while(!operator.isEmpty() && 
                        (operator.peek() == '+' || 
                        operator.peek() == '-' ||
                        operator.peek() == '*' ||
                        operator.peek() == '/'))
                    processAnOperator(operand, operator);
                //push the result of this operation onto the operator stack
                operator.push(token.charAt(0));
            }
            else if((token.charAt(0) == '*')  || (token.charAt(0) == '/'))
            {
                //Process all *, /
                while(!operator.isEmpty() && 
                        (
                            operator.peek() == '+' || 
                            operator.peek() == '-' ||
                            operator.peek() == '*' ||
                            operator.peek() == '/')
                        )
                    processAnOperator(operand, operator);
                //push the result of this operation onto the operator stack
                operator.push(token.charAt(0));
                
            }
            else if(token.trim().charAt(0) == '(')
                    operator.push('(');
            else if(token.trim().charAt(0) == ')')
            {
                //process all the operator in the stack util another '(' shows up
                while(operator.peek() != '(')
                {
                    processAnOperator(operand, operator);
                }
                operator.pop(); //pop"("
            }
            else
            {
                //push the operand onto the stack
                operand.push(new Integer(token));
            }
        }
        //Phase 2: process the remaining operators on the stacks
        while(!operator.isEmpty())
        {
            processAnOperator(operand, operator);
        }
        //return the answer that remains in the operand stack
        return operand.pop();
    }
    
    /**
     * insert blanks between operators.
     * <p>
     * Called by the evaluate method
     * <p>
     * pre-condition    the string is a arithmetic containing only +,-,*,/ operations.
     * <br>
     * post-condition   there is at least one blanks around each '+', '-', '*', '/'.
     * @param s the string to insert blanks
     * @return the string with blanks inserted
     * @see #evaluate(java.lang.String) 
     */
    public static String insertBlanks(String s)
    {
        String result = "";
        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) == '(' || s.charAt(i) == ')' ||
               s.charAt(i) == '+' || s.charAt(i) == '-' ||
               s.charAt(i) == '*' || s.charAt(i) == '/')
                result += " " + s.charAt(i) + " ";
            else
                result += s.charAt(i);
            
        }
        return result;
    }
    
    /**
     * Take and operator from the stack and apply it on the operands in the operand stack.
     * <p>
     * Called by the evaluate method
     * <p>
     * pre-condition    there is at least two integers in the operand stack and 
     * the last operator in the operator stack is one of '+', '-', '*', '/'
     * <br>post-condition   the result is returned to the operand stack
     * @param operand the GenericStack of operands
     * @param operator the GenericStack of operators
     * @see #evaluate(java.lang.String) 
     * @see GenericStack
     */
    public static void processAnOperator(GenericStack<Integer> operand, GenericStack<Character> operator)
    {
        char op = operator.pop();
        int op1 = operand.pop();
        int op2 = operand.pop();
        if(op == '+')
            operand.push(op2 + op1);
        else if(op == '-')
            operand.push(op2 - op1);
        else if(op == '*')
            operand.push(op2 * op1);
        else if(op == '/')
            operand.push(op2 / op1); 
    }
    
    /**
     * Take an ArrayList of integers and determind if they correspond to the 
     * values of the four cards draw.
     * <p>
     * Create an ArrayList of Integers to represent the values of cards.
     * <br>Called by the verify button.
     * <p>
     * @param numbers the numbers found in the expression entered
     * @return true if the numbers correspond to the values of the card, false if not
     * @see #verifyJButtonActionPerformed(java.awt.event.ActionEvent) 
     * @see #verifyJButton
     */
    public boolean isValidNumbers(ArrayList<Integer> numbers)
    {
        //if the expression dose not contain 4 numbers
        if(numbers.size() != 4)
            return false;
        
        //create an arrayList of the values of 4 cards
        ArrayList<Integer> cardNumbers = new ArrayList<>();
        for(int i = 0; i < 4; i++)
        {          
            cardNumbers.add(cards[i].getValue());
        }
        
        //loop through the two arrays
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < cardNumbers.size(); j++)
            {
                //if the two numbers are equal, remove the number from the cardNumbers ArrayList
                if(numbers.get(i) == cardNumbers.get(j))
                {
                    cardNumbers.remove(j);
                    break;
                }
            }
        }
        
        //the four numebrs are valud only is all the numbers in the cardNumbers have a match
        if(cardNumbers.isEmpty())
            return true;
        return false;
    }
    
    /**
     * Take a string of expression and find all the integers in the string and put in into an ArrayList.
     * <p>
     * Called by the verify button
     * <p>
     * @param s the arithmetic expression
     * @return An ArrayList of all the integers in the string
     * @see #verifyJButton
     * @see #verifyJButtonActionPerformed(java.awt.event.ActionEvent) 
     */
    public ArrayList<Integer> getNumbers(String s)
    {
        ArrayList<Integer> numbers = new ArrayList<>();
        
        //loop through the entire string by characters
        for(int i = 0; i < s.length(); i++)
        {
            if(Character.isDigit(s.charAt(i)))//is the charater is a number
            {
                //determind if the integer is one digit or two digits
                
                //if it is two digit
                if(i < s.length() - 1 && Character.isDigit(s.charAt(i + 1)))
                {
                    String number = Character.toString(s.charAt(i)) + Character.toString(s.charAt(i + 1));
                    numbers.add(Integer.parseInt(number));
                    i++;
                }
                
                //if it is one digit
                else
                    numbers.add(Character.getNumericValue(s.charAt(i)));
            }
        }
        return numbers;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        titleJPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        topJPane = new javax.swing.JPanel();
        resultJLabel = new javax.swing.JLabel();
        timeJLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        shuffleJButton = new javax.swing.JButton();
        findSolutionJButton = new javax.swing.JButton();
        middleJPane = new javax.swing.JPanel();
        card1JLabel = new javax.swing.JLabel();
        card2JLabel = new javax.swing.JLabel();
        card3JLabel = new javax.swing.JLabel();
        card4JLabel = new javax.swing.JLabel();
        bottomJPane = new javax.swing.JPanel();
        enterJLabel = new javax.swing.JLabel();
        expressionJTextField = new javax.swing.JTextField();
        verifyJButton = new javax.swing.JButton();
        playerJPane = new javax.swing.JPanel();
        player = new javax.swing.JScrollPane();
        playersJList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        deleteJButton = new javax.swing.JButton();
        addJButton = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        FileJMenu = new javax.swing.JMenu();
        clearJMenuItem = new javax.swing.JMenuItem();
        printJMenuItem = new javax.swing.JMenuItem();
        saveJMenuItem = new javax.swing.JMenuItem();
        exitJMenuItem = new javax.swing.JMenuItem();
        DataJMenu = new javax.swing.JMenu();
        addJMenuItem = new javax.swing.JMenuItem();
        deleteJMenuItem = new javax.swing.JMenuItem();
        searchJMenuItem = new javax.swing.JMenuItem();
        editJMenuItem = new javax.swing.JMenuItem();
        displayJMenuItem = new javax.swing.JMenuItem();
        helpJMenu = new javax.swing.JMenu();
        aboutJMenuItem = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("24 Points Card Game");
        setResizable(false);

        titleJPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Malayalam MN", 0, 24)); // NOI18N
        jLabel2.setText("24 Points Card Game");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CardGame/title.jpg"))); // NOI18N
        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout titleJPaneLayout = new javax.swing.GroupLayout(titleJPane);
        titleJPane.setLayout(titleJPaneLayout);
        titleJPaneLayout.setHorizontalGroup(
            titleJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleJPaneLayout.createSequentialGroup()
                .addContainerGap(415, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(51, 51, 51))
            .addGroup(titleJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(titleJPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(355, Short.MAX_VALUE)))
        );
        titleJPaneLayout.setVerticalGroup(
            titleJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleJPaneLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(titleJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(titleJPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        timeJLabel.setText("01:00");

        jLabel4.setText("Time remaining:");

        shuffleJButton.setMnemonic('S');
        shuffleJButton.setText("Shuffle");
        shuffleJButton.setToolTipText("Draw four new cards");
        shuffleJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shuffleJButtonActionPerformed(evt);
            }
        });

        findSolutionJButton.setMnemonic('F');
        findSolutionJButton.setText("Find Solution");
        findSolutionJButton.setToolTipText("Find the correct solution");
        findSolutionJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findSolutionJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topJPaneLayout = new javax.swing.GroupLayout(topJPane);
        topJPane.setLayout(topJPaneLayout);
        topJPaneLayout.setHorizontalGroup(
            topJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topJPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(findSolutionJButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resultJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shuffleJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topJPaneLayout.setVerticalGroup(
            topJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resultJLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(topJPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(findSolutionJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(timeJLabel)
                        .addComponent(jLabel4))
                    .addComponent(shuffleJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        middleJPane.setLayout(new java.awt.GridLayout(1, 4, 3, 3));
        middleJPane.add(card1JLabel);
        middleJPane.add(card2JLabel);
        middleJPane.add(card3JLabel);
        middleJPane.add(card4JLabel);

        enterJLabel.setText("Enter an expression:");

        expressionJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expressionJTextFieldActionPerformed(evt);
            }
        });

        verifyJButton.setMnemonic('V');
        verifyJButton.setText("Verify");
        verifyJButton.setToolTipText("Verify if the expression is correct");
        verifyJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomJPaneLayout = new javax.swing.GroupLayout(bottomJPane);
        bottomJPane.setLayout(bottomJPaneLayout);
        bottomJPaneLayout.setHorizontalGroup(
            bottomJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomJPaneLayout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(enterJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expressionJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(verifyJButton)
                .addGap(3, 3, 3))
        );
        bottomJPaneLayout.setVerticalGroup(
            bottomJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomJPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bottomJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enterJLabel)
                    .addComponent(expressionJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(verifyJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        playerJPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Players Information"));

        player.setViewportView(playersJList);

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout playerJPaneLayout = new javax.swing.GroupLayout(playerJPane);
        playerJPane.setLayout(playerJPaneLayout);
        playerJPaneLayout.setHorizontalGroup(
            playerJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(player, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
            .addGroup(playerJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(playerJPaneLayout.createSequentialGroup()
                    .addGap(0, 48, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 47, Short.MAX_VALUE)))
        );
        playerJPaneLayout.setVerticalGroup(
            playerJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(player, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
            .addGroup(playerJPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(playerJPaneLayout.createSequentialGroup()
                    .addGap(0, 113, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 113, Short.MAX_VALUE)))
        );

        deleteJButton.setMnemonic('D');
        deleteJButton.setText("Delete");
        deleteJButton.setToolTipText("Delete the selcted player");
        deleteJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJButtonActionPerformed(evt);
            }
        });

        addJButton.setMnemonic('A');
        addJButton.setText("Add");
        addJButton.setToolTipText("Add a new player");
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });

        FileJMenu.setText("File");

        clearJMenuItem.setMnemonic('C');
        clearJMenuItem.setText("Clear");
        clearJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJMenuItemActionPerformed(evt);
            }
        });
        FileJMenu.add(clearJMenuItem);

        printJMenuItem.setMnemonic('P');
        printJMenuItem.setText("Print");
        printJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJMenuItemActionPerformed(evt);
            }
        });
        FileJMenu.add(printJMenuItem);

        saveJMenuItem.setMnemonic('S');
        saveJMenuItem.setText("Save");
        saveJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJMenuItemActionPerformed(evt);
            }
        });
        FileJMenu.add(saveJMenuItem);

        exitJMenuItem.setMnemonic('E');
        exitJMenuItem.setText("Exit");
        exitJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitJMenuItemActionPerformed(evt);
            }
        });
        FileJMenu.add(exitJMenuItem);

        jMenuBar.add(FileJMenu);

        DataJMenu.setMnemonic('p');
        DataJMenu.setText("Database Management");
        DataJMenu.setToolTipText("");

        addJMenuItem.setMnemonic('d');
        addJMenuItem.setText("Add player");
        addJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJMenuItemActionPerformed(evt);
            }
        });
        DataJMenu.add(addJMenuItem);

        deleteJMenuItem.setMnemonic('l');
        deleteJMenuItem.setText("Delete player");
        deleteJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJMenuItemActionPerformed(evt);
            }
        });
        DataJMenu.add(deleteJMenuItem);

        searchJMenuItem.setMnemonic('r');
        searchJMenuItem.setText("Search player");
        searchJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJMenuItemActionPerformed(evt);
            }
        });
        DataJMenu.add(searchJMenuItem);

        editJMenuItem.setMnemonic('t');
        editJMenuItem.setText("Edit Player");
        editJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJMenuItemActionPerformed(evt);
            }
        });
        DataJMenu.add(editJMenuItem);

        displayJMenuItem.setMnemonic('i');
        displayJMenuItem.setText("Display Statistic");
        displayJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayJMenuItemActionPerformed(evt);
            }
        });
        DataJMenu.add(displayJMenuItem);

        jMenuBar.add(DataJMenu);

        helpJMenu.setText("Help");

        aboutJMenuItem.setMnemonic('o');
        aboutJMenuItem.setText("About");
        aboutJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutJMenuItemActionPerformed(evt);
            }
        });
        helpJMenu.add(aboutJMenuItem);

        jMenuBar.add(helpJMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(topJPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bottomJPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(playerJPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(middleJPane, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(207, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(titleJPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(playerJPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteJButton)
                            .addComponent(addJButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(topJPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
                        .addComponent(bottomJPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(163, Short.MAX_VALUE)
                    .addComponent(middleJPane, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(89, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(titleJPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(330, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void expressionJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expressionJTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expressionJTextFieldActionPerformed

    private void printJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJMenuItemActionPerformed
        timer.stop();
        //print the entire form as GUI -- thank you Marty Hall! :)
        PrintUtilities.printComponent(this);
        timer.start();
    }//GEN-LAST:event_printJMenuItemActionPerformed

    private void clearJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJMenuItemActionPerformed
        timer.stop();
        int result = JOptionPane.showConfirmDialog(null, "Are you sure in deleting all the players?", "Delete all players", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(result == JOptionPane.YES_OPTION)
        { 
            try {
                players.clear();
                displayPlayers();
                savePlayers();
            } catch (IOException ex) {
                Logger.getLogger(CardGameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        timer.start();
    }//GEN-LAST:event_clearJMenuItemActionPerformed

    private void addJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJMenuItemActionPerformed
        addJButton.doClick();
    }//GEN-LAST:event_addJMenuItemActionPerformed

    private void deleteJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJMenuItemActionPerformed
        deleteJButton.doClick();
    }//GEN-LAST:event_deleteJMenuItemActionPerformed

    private void verifyJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyJButtonActionPerformed
        String expression = expressionJTextField.getText();
        if(expression == null || expression.length() <= 0)
        {
            JOptionPane.showMessageDialog(null, "Please enter an expression to verify", "Invalid Input",JOptionPane.WARNING_MESSAGE);
        }
        else if(!isValidNumbers(getNumbers(expression)))//invalie expression
        {
            JOptionPane.showMessageDialog(null, "Please use all the numbers on the cards", "Invalid Input",JOptionPane.WARNING_MESSAGE);
        }    
        else if(evaluate(expression) == 24)//correct
        {
            try {
                resultJLabel.setText("Correct!");
                timer.stop();
                JOptionPane.showMessageDialog(null, "Congradulations! You are correct!", "Correct",JOptionPane.INFORMATION_MESSAGE);
                //set the player info
                Player oldPlayer = players.get(playersJList.getSelectedIndex());
                int newTime = (oldPlayer.getNumber() * oldPlayer.getTime() + (120 - timeLeft/1000)) / (oldPlayer.getNumber() + 1);
                Player newPlayer = new Player(oldPlayer.getName(), oldPlayer.getNumber() + 1 , oldPlayer.getNumberOfSuccess() + 1, newTime);
                players.set(playersJList.getSelectedIndex(), newPlayer);
                savePlayers();
                findSolutionJButton.setEnabled(true);
                verifyJButton.setEnabled(false);
                inGame = false;
            } catch (IOException ex) {
                Logger.getLogger(CardGameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else//incorrect
        {
            JOptionPane.showMessageDialog(null, "Incorrect! Keep trying!", "Incorrect",JOptionPane.INFORMATION_MESSAGE);
            resultJLabel.setText("Incorrect!");
        }
    }//GEN-LAST:event_verifyJButtonActionPerformed

    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJButtonActionPerformed
    // Add new player
        try
        {
            timer.stop();
            AddPlayer addPlayer = new AddPlayer(this, true);
            addPlayer.setVisible(true);
            Player newPlayer = addPlayer.getNewPlayer();
            if(newPlayer != null && !players.contains(newPlayer) && searchPlayers(newPlayer.getName()) == -1)
            {
                //proceed to add new city
                players.add(newPlayer);// add to Arraylist
                displayPlayers();
                try {
                    //highlight the newly add city
                    playersJList.setSelectedIndex(searchPlayers(newPlayer.getName()));
                    savePlayers();
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(CardGameGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
        catch(NullPointerException nullexp)
        {
            //JOptionPane.showMessageDialog(null, UNINITIALIZED_VALUE"city not saved");
            playersJList.setVisible(true);
            playersJList.setSelectedIndex(0);
        }
        finally 
        {
            if(inGame)
                timer.start();
        }
    }//GEN-LAST:event_addJButtonActionPerformed

    private void displayJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayJMenuItemActionPerformed
        timer.stop();
        int index = playersJList.getSelectedIndex();
        Player playerToDisplay = new Player(players.get(index));
        playerToDisplay.setName(players.get(index).getName());
        DisplayPlayer display = new DisplayPlayer(this, true, playerToDisplay);
        display.setVisible(true);
        if(inGame)
            timer.start();
    }//GEN-LAST:event_displayJMenuItemActionPerformed

    private void deleteJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJButtonActionPerformed
        // Delete selected city
        int index = playersJList.getSelectedIndex();
        timer.stop();
        int result = JOptionPane.showConfirmDialog(null, "Are you sure in deleting the player?", "Delete player", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(result == JOptionPane.YES_OPTION)
        {
            players.remove(index);
            displayPlayers();
            try {
                savePlayers();
            } catch (IOException ex) {
                Logger.getLogger(CardGameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(inGame)
            timer.start();
    }//GEN-LAST:event_deleteJButtonActionPerformed

    private void editJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJMenuItemActionPerformed
        timer.stop();
        //Edit existing player
        int index = playersJList.getSelectedIndex();
        Player playerToEdit = new Player(players.get(index));
        playerToEdit.setName(players.get(index).getName());
        EditPlayer editJDialog = new EditPlayer(this, true, playerToEdit) ;
        editJDialog.setVisible(true);
        if(editJDialog.getNewPlayer() != null)
        {
            try {
                //remove the old player
                players.remove(index);
                //add the new players
                players.add(editJDialog.getNewPlayer());
                //diplays cities
                displayPlayers();
                // save the citeis
                savePlayers();
            } catch (IOException ex) {
                Logger.getLogger(CardGameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(inGame)
            timer.start();
    }//GEN-LAST:event_editJMenuItemActionPerformed

    private void searchJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJMenuItemActionPerformed
        timer.stop();
        // Find specified player
        String name = JOptionPane.showInputDialog("Enter name of Player");
        String[] nameArray = new String[players.size()];
        if((name != null) && (name.length() > 0))
        {
            for(int i = 0; i< nameArray.length; i++)
            {
                nameArray[i] = players.get(i).getName();
            }
            int index = linearSearch(nameArray,name);
            if(index == -1)
                JOptionPane.showMessageDialog(null, name + " not found!", "Search result",JOptionPane.WARNING_MESSAGE);
            else
                playersJList.setSelectedIndex(index);
        } 
        if(inGame)
            timer.start();
    }//GEN-LAST:event_searchJMenuItemActionPerformed

    private void exitJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitJMenuItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitJMenuItemActionPerformed

    private void saveJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJMenuItemActionPerformed
        try {
            timer.stop();
            savePlayers();
            JOptionPane.showMessageDialog(null,"The players Information is saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(CardGameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            timer.start();
        }
    }//GEN-LAST:event_saveJMenuItemActionPerformed

    private void aboutJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutJMenuItemActionPerformed
        timer.stop();
        // diplay the about form
        About about = new About(this, true);
        about.setVisible(true);
        if(inGame)
            timer.start();
    }//GEN-LAST:event_aboutJMenuItemActionPerformed

    private void findSolutionJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findSolutionJButtonActionPerformed
        timer.stop();
        // diplay the about form
        FindSolution solution = new FindSolution(this, true, cards);
        solution.setVisible(true);
    }//GEN-LAST:event_findSolutionJButtonActionPerformed

    private void shuffleJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shuffleJButtonActionPerformed
        displayCards();
        resultJLabel.setText("");
        expressionJTextField.setText("");
        findSolutionJButton.setEnabled(false);
        verifyJButton.setEnabled(true);
        timeJLabel.setText("01:00");
        timeLeft = TIME_LIMIT;
        timer.start();
        inGame = true;
    }//GEN-LAST:event_shuffleJButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CardGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CardGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CardGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CardGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CardGameGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu DataJMenu;
    private javax.swing.JMenu FileJMenu;
    private javax.swing.JMenuItem aboutJMenuItem;
    private javax.swing.JButton addJButton;
    private javax.swing.JMenuItem addJMenuItem;
    private javax.swing.JPanel bottomJPane;
    private javax.swing.JLabel card1JLabel;
    private javax.swing.JLabel card2JLabel;
    private javax.swing.JLabel card3JLabel;
    private javax.swing.JLabel card4JLabel;
    private javax.swing.JMenuItem clearJMenuItem;
    private javax.swing.JButton deleteJButton;
    private javax.swing.JMenuItem deleteJMenuItem;
    private javax.swing.JMenuItem displayJMenuItem;
    private javax.swing.JMenuItem editJMenuItem;
    private javax.swing.JLabel enterJLabel;
    private javax.swing.JMenuItem exitJMenuItem;
    private javax.swing.JTextField expressionJTextField;
    private javax.swing.JButton findSolutionJButton;
    private javax.swing.JMenu helpJMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel middleJPane;
    private javax.swing.JScrollPane player;
    private javax.swing.JPanel playerJPane;
    private javax.swing.JList<String> playersJList;
    private javax.swing.JMenuItem printJMenuItem;
    private javax.swing.JLabel resultJLabel;
    private javax.swing.JMenuItem saveJMenuItem;
    private javax.swing.JMenuItem searchJMenuItem;
    private javax.swing.JButton shuffleJButton;
    private javax.swing.JLabel timeJLabel;
    private javax.swing.JPanel titleJPane;
    private javax.swing.JPanel topJPane;
    private javax.swing.JButton verifyJButton;
    // End of variables declaration//GEN-END:variables
}
