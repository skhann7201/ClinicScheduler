package project2;

/**
 * This  class defines the providers’ last names with, each provider
 * having a location and a specialty.
 * @author Shahnaz Khan, Vy Nguyen
 */
public abstract class Provider extends Person{
    private Location location;

    /**
     * Constructor to create a Provider instance.
     * @param profile the profile of the provider.
     * @param location the location of the provider's practice.
     */
    public Provider(Profile profile, Location location) {
        super(profile);
        this.location = location;
    }

    /**
     * Returns the location of the provider.
     * @return the provider's location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Abstract method to get the charging rate for the provider.
     * @return the charging rate per visit.
     */
    public abstract int rate();

    /**
     * Overrides the toString method to include provider details.
     * @return a string representation of the provider's profile and location.
     */
    @Override
    public String toString() {
        return super.toString() + "\nLocation: " + location.toString();
    }
}

