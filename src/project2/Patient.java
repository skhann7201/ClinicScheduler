package project2;

/**
 * This class represents a Patient with a profile and a linked list of completed visits.
 * The class implements Comparable to enable sorting and comparison based on profile information
 *
 * @author Vy Nguyen, Shahnaz Khan
 */
public class Patient extends Person{

    private Profile profile;
    private Visit visits; // linked list of completed visits ( appointments)

    /**
     * Constructor to create a Patient instance
     * @param profile   the profile of the patient
     */
    public Patient(Profile profile) {
        super(profile);
        this.visits = new Visit();
    }

    /**
     * Computes the total charge for the patient by traversing the linked list of visits.
     * The charge for each visit depends on the provider's specialty.
     *
     * @return the total charge for the patient's visits
     */
    public int charge(){
        int totalCharge = 0;
        Visit currentVisit = visits;

        while (currentVisit != null) {
            totalCharge += currentVisit.getVisit().getAppointment().getProvider().rate();
            currentVisit = currentVisit.getVisit(); // Move to the next visit
        }
        return totalCharge;
    }

    /**
     * Overrides the equals method to check if two patients have the same profile.
     *
     * @param obj   the object to compare
     * @return true if the profiles are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient) {
            Patient other = (Patient) obj;
            return this.profile.equals(other.profile);
        }
        return false;
    }

    /**
     * Overrides the compareTo method to compare patients based on their profile.
     * @param other the other patient to compare to
     * @return negative number if this patient is less than other, 0 if equals to other patient
     * positive number if this patient is equal to other
     */
    @Override
    public int compareTo(Patient other){
        return this.profile.compareTo(other.profile);
    }

    /**
     * Overrides the toString method to provide a textual representation of the patient.
     *
     * @return a string representation of the patient's profile and visits.
     */
    @Override
    public String toString() {
        String result = profile.toString() + "\nVisits:\n";

        Visit currentVisit = visits;
        while (currentVisit !=null) {
            result += currentVisit.toString() + "\n";
            currentVisit = currentVisit.getVisit();
        }

        return result;
    }
}