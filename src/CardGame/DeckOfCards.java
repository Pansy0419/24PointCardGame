
package CardGame;

import java.util.Random;

/**
 * <pre>
 * File         DeckOfCards.java
 * Project      24 Points Card Game
 * Description  A class representing a deck of 52 cards in a standard poker with an array of 52 Card objects
 * Platform     jdk 1.8.0_77; NetBeans IDE 8.2; Mac Os X
 * Course       CS 143, SCC
 * Hours        15 minutes
 * Created on   Feb 17 2017
 </pre>
 *
 * @author:	siyu.pan
 * @see         java.util.ArrayList
 * @see         Card
 */
public class DeckOfCards {
    
    /**
     * ArrayList of 52 Cards objects
     */
    private final Card[] deck= new Card[52];

    /**
     * The default contructor.
     * <p>
     * Create a array of 52 cards with different colors and kinds 
     */
    public DeckOfCards() {
        for(int i = 0; i < 13; i++)
        {
            deck[i] = new Card(i + 1, "spade");
        }
        for(int i = 0; i < 13; i++)
        {
            deck[i + 13] = new Card(i + 1, "heart");
        }
        for(int i = 0; i < 13; i++)
        {
            deck[i + 26] = new Card(i + 1, "diamond");
        }
        for(int i = 0; i < 13; i++)
        {
            deck[i + 39] = new Card(i + 1, "club");
        }
    }
      
    /**
     * Select 4 Card objects randomly form the DeckOfCard.
     * <p>
     * calls the removeEqualValue method to make sure none of the cards has the same value
     * @return An Array of 4 cards selected
     * @see #removeEqualValue(CardGame.Card[]) 
     * @see Card
     * @see java.util.Random
     */
    public Card[] draw()
    {
        Card[] cards = new Card[4];
        //draw 4 cards randomly from the deck of cards
        Random rand = new Random();
        for(int i = 0; i < 4; i++)
        {
            cards[i] = deck[rand.nextInt(52)];
        }      
        
        removeEqualValue(cards);
        return cards;
    }
    
    /**
     * replace the card with equal values with a new card.
     * <p>
     * Called by the draw method
     * @param cards the array of the 4 cards selected
     * @see #draw() 
     * @see Card
     * @see java.util.Random
     */
    //replace the card with equal values with a new card
    public void removeEqualValue(Card[] cards)
    {
        Random rand = new Random();
        for(int i = 0; i < cards.length; i++)
        {
            for(int j = 1; j < cards.length - i; j++)
            {
                if(cards[i].getValue() == cards[i + j].getValue())
                {
                    //for every card that has a equal value with another card
                    //replace it with a new random card
                    int random = 0;
                    
                    //make sure the new number does not equal to any numebrs in the cards
                    boolean equal = true;
                    while(equal)
                    {
                        equal = false;
                        random = rand.nextInt(52);
                        for(int k = 0; k < 4; k++)
                        {
                            if(cards[k].getValue() == deck[random].getValue())
                                equal = true;
                        }
                    }
                    
                    cards[i] = deck[random];
                }               
            }
        }
    }
}
