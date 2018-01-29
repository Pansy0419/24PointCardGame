package CardGame;

/**
 * <pre>
 * File         Player.java
 * Project      24 Points Card Game
 * Description  A class representing an player with fields:
 *      1.name of the player
 *      2.number of game player
 *      3.number of success
 *      4.percentage of success
 *      5.Average time fo success
 * Platform     jdk 1.8.0_77; NetBeans IDE 8.2; Mac Os X
 * Course       CS 143, SCC
 * Hours        30 minutes
 * Created on   Feb 17 2017
 </pre>
 *
 * @author:	siyu.pan
 */
public class Player extends Person{
    
    /**
     * The name of the player.
     */
    private String name;
    
    /**
     * Number of games played by the player.
     */
    private int number;
    
    /**
     * Number of success of the player.
     */
    private int numberOfSuccess;
    
    /**
     * Percentage of success out of the total number of games.
     */
    private float percent;
    
    /**
     * Average time for the player to succeed.
     */
    private int time;

    /**
     * Defaulty constructor of the player object.
     */
    public Player() {
        super();
        number = 0;
        percent = 100;
        time = 0;
    }
    
    /**
     * Constructor of the player object with only the name input.
     * @param name player's name
     */
    public Player(String name)
    {
        super(name);
        number = 0;
        percent = 100;
        time = 0;
    }
    
    /**
     * Copy contructor.
     * <p>
     * Copy the fields in the input player object to construct a new player object.
     * @param another the player object to be copied
     */
    public Player(Player another)
    {
        super(another.name);//why this doesn't work?
        this.number = another.number;
        this.numberOfSuccess = another.numberOfSuccess;
        this.percent = another.percent;
        this.time = another.time;
    }

    /**
     * Contructor of the player object with name, number, number of success and time inputs.
     * <p>
     * The percent success is calculated within the constructor.
     * @param name name of player
     * @param number number of game
     * @param numberOfSuccess number of success
     * @param time average time of success
     */
    public Player(String name, int number, int numberOfSuccess, int time) {
        super(name);
        this.number = number;
        this.numberOfSuccess = numberOfSuccess;
        this.percent = (float) (((float)numberOfSuccess / (float)number) * 100.0);
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public float getPercent() {
        return percent;
    }

    public int getNumberOfSuccess() {
        return numberOfSuccess;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setNumberOfSuccess(int numberOfSuccess) {
        this.numberOfSuccess = numberOfSuccess;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", number=" + number + ", numberOfSuccess=" + numberOfSuccess + ", percent of success= %" + percent + ", Average success time=" + time + '}';
    }
 
    

    
    
    
    
    
    
    
    
    
    
}
