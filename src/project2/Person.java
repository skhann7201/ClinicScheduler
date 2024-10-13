package project2;

/**
 * This class
 * @author Shahnaz Khan, Vy Nguyen
 */
public class Person implements Comparable<Person>{
    protected Profile profile;

    /**
     * Default constructor that initializes profile instance to null
     */
    Person(){
        profile = null;
    }

    /**
     * Constructor that initializes profile instance to profile parameter
     * @param profile
     */
    Person(Profile profile){
        this.profile = profile;
    }

    /**
     * Getter method that returns
     * @return Profile of instance
     */
    public Profile getProfile(){
        return this.profile;
    }

    /**
     *
     * @param o the object to be compared.
     * @return >=1 if instance is greater than parameter, <1 if
     * instance is less than parameter, 0 if they are equal
     */
    @Override
    public int compareTo(Person o) {
        return this.profile.compareTo(o.profile);
    }

    @Override
    public String toString(){
        return (profile.toString());
    }
}

