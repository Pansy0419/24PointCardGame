
package CardGame;

import java.util.Objects;

/**
 * <pre>
 * File         Person.java
 * Project      24 Points Card Game
 * Description  A class representing an person with a name field
 * Platform     jdk 1.8.0_77; NetBeans IDE 8.2; Mac Os X
 * Course       CS 143, SCC
 * Hours        5 minutes
 * Created on   Feb 17 2017
 </pre>
 *
 * @author:	siyu.pan
 */
public class Person {
    /*
     * the name of the person
     */
    private String name;

    /*
     * default constructor of the person object.
     */
    public Person() {
        name = "";
    }
  
    /*
     * construction of the person object with the name input
     */
    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        final Person other = (Person) obj;
        return Objects.equals(this.name, other.name);
    }
    
    
}
