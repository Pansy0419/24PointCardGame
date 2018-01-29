/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CardGame;

/**
 * File         Validation.java
 * Description  A validation class use in famous ballets application to make s
 * ure all the user inputs are valid.
 * Platform     Mac OS, Netbean 8.2, jdk 1.8.0 101
 * Date         1/29/2017
 * Hours        1 hour
 * Class     CS143, SCC
 * @author      siyu.pan
 */
public class Validation {
    
    /**
     * Method isInteger
     * Description Decide if a string input is an integer or not. Return true if it 
     * is, false if it is not.
     * @param integer string
     * @return boolean
     */
    public static boolean isPositiveInteger(String integer)
    {
        try{
            int i = Integer.parseInt(integer);
            return i >= 0;
        }
        catch(NumberFormatException ex)
        {
            return false;
        } 
    }
    
    /**
     * Method isEmpty
     * Description Decide if a string is empty or not.
     * @param s string
     * @return boolean
     */
    public static boolean isEmpty(String s)
    {
        return s.length() <= 0;
    }
    
    /**
     * Method isValidName
     * Description Decide if a string a valid name for a person or not. Which 
     * means it shouldn't contain any character that is not a letter. Return 
     * true if it is valid, false if it is not.
     * @param name
     * @return boolean
     */
    public static boolean isValidName(String name)
    {
        for(int i = 0; i < name.length(); i++)
        {
            if(!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ')
                return false;
        }
        return true;
    }  
    
    /**
     * Decide if a string input is a valid time by checking if it contains an integer and if it is no larger than the time limit
     * @param time the string input
     * @param limit the time limit
     * @return boolean true if valid, false if not
     */
    public static boolean isValidTime(String time, int limit)
    {
        return isPositiveInteger(time) && Integer.parseInt(time) <= limit/1000;
    }
}
