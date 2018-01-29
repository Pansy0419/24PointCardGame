package CardGame;
/**
 * <pre>
 * File         GenericStack.java
 * Project      24 Points Card Game
 * Description  Encapsulats the stack ADT and provides operations for msnipulating stacks
 * Platform     jdk 1.8.0_77; NetBeans IDE 8.2; Mac Os X
 * Course       CS 143, SCC
 * Hours        30 minutes
 * Created on   Feb 17 2017
 </pre>
 *
 * @author:	siyu.pan
 * @see         java.util.ArrayList
 */
import java.util.ArrayList;

public class GenericStack<E> extends ArrayList<E>
{
    /**
     * The ArrayList of objects
     */
    private java.util.ArrayList<E> list = new java.util.ArrayList<>();

    /**
     * Get the size of the stack.
     * @return int size of the stack
     */
    public int getSize() 
    {
        return list.size();
    }

    /**
     * return the last object of the stack.
     * @return E the last object in the stack
     */
    public E peek() 
    {
        return list.get(getSize() - 1);
    }

    /**
     * push an object onto the stack.
     * @param o the object being pushed
     */
    public void push(E o) 
    {
        list.add(o);
    }

    /**
     * return the last object of the stack and remove it.
     * @return E the last object in the stack
     */
    public E pop() 
    {
        E o = list.get(getSize() - 1);
        list.remove(getSize() - 1);
        return o;
    }

    @Override
    public boolean isEmpty() 
    {
        return list.isEmpty();
    }

    @Override
    public String toString() 
    {
        return "stack: " + list.toString();
    }
}
