package project2;

/**
 * The Imaging class represents an appointment for imaging services.
 * It extends the Appointment class and includes details about the
 * Radiology room assigned to the appointment.
 * @author Vy Nguyen, Shahnaz Khan
 */
public class Imaging extends Appointment {
    private Radiology room;

    /**
     * Constructs an Imaging appointment with the specified date, timeslot,
     * patient, and radiology room.
     * @param date      the date of the appointment
     * @param timeslot  the timeslot of the appointment
     * @param patient   the patient associated with the appointment
     * @param room      the Radiology room for the imaging appointment
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Radiology room) {
        super(date, timeslot, patient);
        this.room = room;
    }

    /**
     * Returns the Radiology room assigned to this imaging appointment.
     * @return the Radiology room
     */
    public Radiology getRoom() {
        return this.room;
    }

    /**
     * Returns a string representation of the Imaging appointment, including
     * the details from the Appointment class and the Radiology room.
     * @return a string representation of the Imaging appointment
     */
    @Override
    public String toString() {
        return super.toString() + " " + this.room;
    }
}