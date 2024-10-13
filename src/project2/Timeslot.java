package project2;

/**
 * This class represents a timeslot for scheduling appointments.
 * Each slot consists of the hour (using military time) and minute.
 */
public class Timeslot implements Comparable<Timeslot> {
    private int hour;
    private int minute;

    /**
     * Constructs a Timeslot with the specified hour and minute.
     * @param hour   the hour of the time slot
     * @param minute the minute of the time slot
     */
    public Timeslot(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid hour or minute.");
        }
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Gets the hour of the time slot.
     * @return the hour of the time slot
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the minute of the time slot.
     *
     * @return the minute of the time slot
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Compares this Timeslot to another based on hour and minute.
     *
     * @param other the other Timeslot to compare to
     * @return a negative integer if timeslot is less than, zero if equal to, or a positive integer
     * if greater than the specified Timeslot.
     */
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour != other.hour) {
            return Integer.compare(this.hour, other.hour);
        }
        return Integer.compare(this.minute, other.minute);
    }

    /**
     * Returns a textual representation of the time slot in HH:MM AM/PM format.
     *
     * @return String representation of the time slot in 12-hour format.
     */
    @Override
    public String toString() {
        String period = (this.hour < 12) ? "AM" : "PM";
        int displayHour = (this.hour % 12 == 0) ? 12 : this.hour % 12;

        return String.format("%d:%02d %s", displayHour, minute, period);
    }
}