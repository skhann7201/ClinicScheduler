package project2;

/**
 * This class represents a doctor's charge rate and National Provider Identifier (NPI),
 * extending the Provider class.
 *
 * @author Vy Nguyen, Shahnaz Khan
 */
public class Doctor extends Provider {
    private int chargeRate; // Charge rate for the doctor
    private String npi; // National Provider Identifier

    /**
     * Constructor to create a Doctor instance.
     * @param profile    the profile of the doctor
     * @param location   the location of the doctor's practice
     * @param chargeRate the charge rate for the doctor per visit
     * @param npi       the National Provider Identifier for the doctor
     */
    public Doctor(Profile profile, Location location, int chargeRate, String npi) {
        super(profile, location);
        this.chargeRate = chargeRate;
        this.npi = npi;
    }

    /**
     * Gets the National Provider Identifier for the doctor.
     *
     * @return the NPI of the doctor
     */
    public String getNPI() {
        return this.npi;
    }

    /**
     * Implements the rate method from the Provider class.
     * Returns the doctor's charge rate per visit.
     * @return the charge rate for the doctor
     */
    @Override
    public int rate() {
        return chargeRate;
    }
}