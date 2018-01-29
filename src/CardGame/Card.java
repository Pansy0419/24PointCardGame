package CardGame;

import java.util.Objects;

/**
 * <pre>
 * File         DeckOfCards.java
 * Project      24 Points Card Game
 * Description  A class representing a single card with fields
 *      1.value of card
 *      2.color of card
 *      3.name of the image of the card
 * Platform     jdk 1.8.0_77; NetBeans IDE 8.2; Mac Os X
 * Course       CS 143, SCC
 * Hours        15 minutes
 * Created on   Feb 17 2017
 </pre>
 *
 * @author:	siyu.pan
 * @see         DeckOfCards
 */
public class Card {
    /**
     * The value of the card.
     */
    private final int value;
    
    /**
     * The kind of the card.
     */
    private final String kind;
    
    /**
     * The name of the card's image file.
     */
    private final String image;
    
    /**
     * The default constructor.
     */
    public Card()
    {
        this.value = 0;
        this.kind = "";
        this.image = "";
    }
    
    /**
     * The the contructor with value and kind inputs.
     * @param value the value of the card
     * @param kind the kind of the card
     */
    public Card(int value, String kind) {
        this.value = value;
        this.kind = kind;
        this.image = kind + value + ".jpg";
    }

    public int getValue() {
        return value;
    }

    public String getKind() {
        return kind;
    }

    public String getImage() {
        return image;
    }  

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.kind, other.kind)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Card{" + "value=" + value + ", kind=" + kind + '}';
    }
    
    
}
