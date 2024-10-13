package project2;
import java.util.Scanner;
/**
 * The Scheduler class manages appointment scheduling, through a command-line interface. 
 * It allows user to manipulate appointments and print them by various criteria
 * until terminated by Q command.
 * 
 * @author Shahnaz Khan, Vy Nguyen
 */
public class Scheduler {

    // Instance variable
    private List allAppointments;

    /**
     * Default constructor that creates an empty list of appointments
     */
    public Scheduler() {
        allAppointments = new List();
    }

    /**
     * Helper method: Check if the command is valid
     * 
     * @param command   the command to check
     * @return true if the command is valid, false otherwise
     */
    private boolean isValidCommand(String command) {
        String[] validCommands = { "S","C","R","PA","PP","PL","PS","Q"};
        for(String validCommand : validCommands) {
            if(command.equals(validCommand)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method: Process each command 
     * 
     * @param command   the command to process
     * @param parts     the parts of the command
     * @return true if the command is processed successfully, false otherwise.
     */
    private boolean processCommand(String command, String[] parts) {
        if(!isValidCommand(command)) {
            System.out.println("Invalid command!");
            return true; // return true to keep accepting commands
        }

        switch (command) {
            case "S":
                scheduleAppointment(parts);
                break;
            case "C":
                cancelAppointment(parts);
                break;
            case "R":
                recheduleAppointment(parts);
                break;
            case "PA":
                if (allAppointments.size() == 0){
                    System.out.println("The schedule calendar is empty.");
                }
                else {
                    System.out.println();
                    System.out.println("** Appointments ordered by date/timeslot/provider **");
                    allAppointments.printByAppointment();
                    System.out.println("** end of list **");
                    System.out.println();
                }
                break;
            case "PL":
                if (allAppointments.size() == 0){
                    System.out.println("The schedule calendar is empty.");
                }
                else {
                    System.out.println();
                    System.out.println("** Appointments ordered by county/date/time **");
                    allAppointments.printByLocation();
                    System.out.println("** end of list **");
                    System.out.println();
                }
                break;
            case "PP":
                if (allAppointments.size() == 0){
                    System.out.println("The schedule calendar is empty.");
                }
                else {
                    System.out.println();
                    System.out.println("**Appointments ordered by Patient/time/date **");
                    allAppointments.printByPatient();
                    System.out.println("** end of list **");
                    System.out.println();
                }
                break;
            case "PS":
                if (allAppointments.size() == 0){
                    System.out.println("The schedule calendar is empty.");
                }
                else {
                    System.out.println();
                    printBillingStatements();
                }
                break;
            case "Q":
                System.out.println("Scheduler terminated.");
                return false;
            default:
                System.out.println("Invalid command!");
        }
        return true;   
    }

    /**
     * Helper method: Check if appointment data is valid
     * 
     * @param apptDateStr the date of appointment 
     * @param timeslotStr the timeslot of appointment
     * @param fName       the first name of patient
     * @param lName       the last name of patient
     * @param dob         the date of birth of patient
     * @param providerName  the providers name for this appointment
     *  @return true if the appointment data is valid, false otherwise.
     */
    private boolean isValidApptData(String apptDateStr, String timeslotStr, String fName, String lName, String dob, String providerName) {
        Timeslot timeslot = getTimeslot(timeslotStr);

        // 1. Validate provider
        if(!isValidProvider(providerName)) return false;
        // 2. Validate patient dob
        if(!isValidDOB(dob)) return false;
        // 3. Validate timeslot
        if (timeslot == null) return false;                               
        // 4. Validate appointment date                                    
        if(!isValidApptDate(apptDateStr)) return false;
        // All validation passed, return true
        return true;
    }

    /**
     * Helper method: Finds a provider by name 
     * @param providerName the name of provider
     * @return the provider if found, null otherwise.
     */
    private Provider findProvider(String providerName) {
        // Looping through all the providers in the Provider enum class
        for(Provider provider: Provider.values()) {
            //Compare
            if (provider.getProfile().getLastName().equalsIgnoreCase(providerName)){
                return provider;
            }
        }
        // if no match
        return null;
    }
    
    /**
     * Helper method: Check if a provider is valid 
     * 
     * @param providerName the name of the provider
     * @return true if the provider is valid, false otherwise.
     */
    private boolean isValidProvider(String providerName) {
        Provider provider = findProvider(providerName.trim());
        if (findProvider(providerName) == null) {
            System.out.println(providerName + " - provider does not exist.");
            return false;
        }
        return true;
    } 

    /**
     * Helper method: Check if a date of birth is valid
     * 
     * @param dobStr the date of birth
     * @return true if the date of birth is valid, false otherwise.
     */
    private boolean isValidDOB(String dobStr) {
        Date dobDate = new Date(dobStr);
        if (!dobDate.isValid() || dobDate.isToday() || dobDate.isFutureDate()) {
            if(dobDate.isToday() || dobDate.isFutureDate()){
                System.out.println("Patient dob: " + dobDate + " is today or a date after today.");
            }
            else {
                System.out.println("Patient dob: " + dobDate + " is not a valid calendar date.");
            }
            return false;
        }
        return true;
    }

    /**
     * Retrieves a Timeslot object based on the provided timeslot string. 
     * @param timeslotStr   the string representation of the timeslot index.
     * @return the corresponding Timeslot object, or null if the timeslot is invalid.
     */
    private Timeslot getTimeslot(String timeslotStr){
        int timeslotIndex;
        try{
            timeslotIndex = Integer.parseInt(timeslotStr);
        } catch (NumberFormatException e) {
            System.out.println(timeslotStr + " is not a valid time slot.");
            return null;
        }

        Timeslot timeslot = Timeslot.fromIndex(timeslotIndex);
        if (timeslot == null) {
            System.out.println( timeslotStr + " is not a valid timeslot.");
            return null;
        }
        return timeslot;
    }

    /**
     * Helper method: Check if the appointment date is valid.
     * This method checks if the provided appointment date string represents 
     * is not today or a date before today, 
     * it not a weekend, and is within six months.
     * 
     * @param appointmentDateStr the string representation of the appointment 
     * @return true if the appointment date is valid, false otherwise.
     */
    private boolean isValidApptDate(String appointmentDateStr) {
        Date appointmentDate = new Date(appointmentDateStr);                                   
        if (!appointmentDate.isValid()) {
            System.out.println("Appointment date: " + appointmentDate + " is not a valid calendar date.");
            return false;
        }
        if(appointmentDate.isToday() ||appointmentDate.isPastDate()) {
            System.out.println("Appointment date: " + appointmentDate + " is today or a date before today.");
            return false;
        }
        if(appointmentDate.isWeekend()) {
            System.out.println("Appointment date: " + appointmentDate + " is a Saturday or Sunday.");
            return false;
        }
        if(appointmentDate.isSixMonthsFromToday()) {
            System.out.println("Appointment date: " + appointmentDate + " is not within six months.");
            return false;
        }
        return true;
    }

    /**
     * Check if there is conflict with the given appointment details.
     * 
     * This method checks for two types of conflicts:
     * - If the same patient already has an appointment at the same time slot.
     * - If any other patient has an appointment with the same provider at the same time slot.
     * 
     * @param patient   the patient's profile
     * @param apptDate  the appointment date
     * @param timeslot  the appointment time slot
     * @param provider  the appointment provider
     * @return true if there is a conflict, false otherwise.
     */
    private boolean hasConflict(Profile patient, Date apptDate, Timeslot timeslot, Provider provider){
        Appointment tempAppt = new Appointment(apptDate, timeslot, patient,provider);
        // Validate provider and throw an exception or handle the case if provider is null
        if (provider == null) {
            System.out.println("Provider cannot be null.");
            return true; // Assuming null provider means conflict for safety
        }

        if (allAppointments.contains(tempAppt)) {
            System.out.println(patient.getFirstName()+" "
                                + patient.getLastName()+" "
                                + patient.getDOB()+" "
                                + " has an existing appointment at the same time slot.");
            return true;
        }

        for(int i = 0; i < allAppointments.size(); i++) {
            Appointment appt = allAppointments.get(i);
            if(appt.getDate().equals(apptDate) && appt.getTimeslot().equals(timeslot) && appt.getProvider().equals(provider)) {
                System.out.println(provider.toString() + " is not available at slot " + (timeslot.ordinal() + 1) + ".");
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method: create temprary appointment from provided tokens.
     * This method extract the appointment details from the tokens and create appointment object.
     * 
     * @param tokens the array of tokens containing the appointment details
     * @param includeProvider whether to include a provider in the appointment
     * @param timeslotIndex  the index of the timeslot
     * @return the temporary appointment object, or null if the timeslot if invalid.
     * 
     */
    private Appointment createTempAppt(String[] tokens, boolean includeProvider, Integer timeslotIndex){
        String apptDateStr = tokens[1].trim();
        String timeslotIndexStr = tokens[2].trim();
        String firstName = tokens[3].trim();
        String lastName = tokens[4].trim();
        String dobStr = tokens[5].trim();

        Date apptDate = new Date (apptDateStr);
        Timeslot timeslot = getTimeslot(timeslotIndexStr);
        Date dob = new Date(dobStr);
        Profile profile = new Profile(firstName, lastName, dob);

        if(timeslot == null) {
            System.out.println(timeslotIndexStr + " is not a valid time slot");
            return null;
        }

        // If a provider is included (for scheduling and rescheduling)
        Provider provider = null;
        if(includeProvider) {
            String providerName = tokens[6].trim();
            provider = findProvider(providerName);
            return new Appointment(apptDate, timeslot, profile, provider);
        }

        // If no provider is needed (for canceling)
        return new Appointment(apptDate, timeslot, profile);
    }

    /**
     * Schedules an appointment based on the provided tokens.
     * 
     * @param tokens the array of tokens containing the appointment details
     * @return true if the appointment is scheduled successfully, false otherwise.
     */
    private boolean scheduleAppointment(String[] tokens){
        if (tokens.length != 7) {
            System.out.println("Invalid command!");
            return false;
        }

        // Parse the input tokens
        String apptDateStr = tokens[1].trim();
        String timeslotIndexStr = tokens[2].trim();
        String firstName = tokens[3].trim();
        String lastName = tokens[4].trim();
        String dobStr = tokens[5].trim();
        String providerName = tokens[6].trim();

        if(!isValidApptData(apptDateStr, timeslotIndexStr, firstName, lastName, dobStr, providerName)){
            return false;
        }

        Date apptDate = new Date(apptDateStr);
        Timeslot timeslot = getTimeslot(timeslotIndexStr);
        Date dob = new Date(dobStr);
        Profile profile = new Profile(firstName, lastName, dob);
        Provider provider = findProvider(providerName);
        
        // Check for appointment conflict 
        if(hasConflict(profile, apptDate, timeslot, provider)) {
            return false;
        }

        // Create and add appointment
        Appointment appt = new Appointment( apptDate, timeslot, profile, provider);
        allAppointments.add(appt);
        System.out.println(appt.toString() + " booked.");

        return true;
    }

    /**
     * Reschedules an appointment based on the provided tokens.
     * 
     * @param tokens the array of tokens containing the appointment details.
     * @return true if the appointment is rescheduled successfully, false otherwise.
     */
    private boolean recheduleAppointment(String[] tokens){
        if (tokens.length !=7) {
            System.out.println("Invalid command!");
            return false;
        }

        // Check if the appointment exists
        Appointment originalAppt = createTempAppt(tokens, false, 2);
        if (!allAppointments.contains(originalAppt)) {
            System.out.println(originalAppt.getDate().toString() + " "
                    + originalAppt.getTimeslot().toString() + " "
                    + originalAppt.getProfile().toString()
                    + " does not exist.");
            return false;
        }

        Appointment apptFromList = null;
        for (int i = 0; i < allAppointments.size(); i++) {
            Appointment appt = allAppointments.get(i);
            if (appt.equals(originalAppt)) {
                apptFromList = appt;
                break;
            }

        }

        // Check if the new slot is valid
        Timeslot newTimeslot = getTimeslot(tokens[6].trim());
        if (newTimeslot == null) {
            return false;
        }
        if(hasConflict(originalAppt.getProfile(), originalAppt.getDate(),
                newTimeslot,apptFromList.getProvider())) {
            return false;
        }

        // Reschedule: Remove the original appointment and add the new one
        Appointment newAppt = new Appointment(apptFromList.getDate(), newTimeslot, apptFromList.getProfile(), apptFromList.getProvider());
        allAppointments.remove(originalAppt);
        allAppointments.add(newAppt);

        // Print confirmation of rescheduled appointment
        System.out.println("Rescheduled to " + newAppt.getDate() + " "
                + newAppt.getTimeslot().toString() + " " 
                + newAppt.getProfile().toString()
                + newAppt.getProvider().toString());
        return true;

    }

    /**
     * Cancels an appointment based on the provided tokens.
     * 
     * @param tokens the array of tokens containing the appointment details
     * @return true if the appointment is canceled successfully, false otherwise.
     */
    private boolean cancelAppointment(String[] tokens){
        if (tokens.length != 7) {
            System.out.println("Invalid command!");
            return false;
        }

        Appointment tempAppt = createTempAppt(tokens, true, 2);

        if (allAppointments.contains(tempAppt)) {
            allAppointments.remove(tempAppt);
            System.out.println(tempAppt.getDate().toString() + " "
                    + tempAppt.getTimeslot().toString() + " " 
                    + tempAppt.getProfile().toString()
                    + " has been canceled.");
            return true;
        } else {
            System.out.println(tempAppt.getDate().toString() + " "
                                + tempAppt.getTimeslot().toString() + " " 
                                + tempAppt.getProfile().toString()
                                + " does not exist.");
            return false;
        }
    }


    /**
     * Prints the billing statement for all appoinments.
     */
    private void printBillingStatements(){
        if (allAppointments.size() == 0) {
            System.out.println("No appointments to bill.");
            return;
        }

        allAppointments.sortbyPatient();
        System.out.println("\n** Billing statement ordered by patient **");

        Profile currentProfile = null;
        double totalDue = 0.0;
        int count = 1;

        for (int i = 0; i < allAppointments.size(); i++) {
            Profile profile = allAppointments.get(i).getProvider().getProfile();
            Provider provider = allAppointments.get(i).getProvider();
            double amountDue = provider.getSpecialty().getCharge();

            // If we're on a new patient, print the previous patient's statement
            if (currentProfile == null || !profile.equals(currentProfile)) {
                if (currentProfile != null) {
                    System.out.println("(" + count + ") "
                            + currentProfile.getFirstName() + " "
                            + currentProfile.getLastName() + " "
                            + currentProfile.getDOB() + " [amount due: $"
                            + String.format("%.2f", totalDue) + "]");
                    count++;
                }

                // Update current profile info
                currentProfile = profile;
                totalDue = 0.0; // Reset total due for the new patient
            }

            // Accumulate the amount due for the current patient
            totalDue += amountDue;
        }

        // Print the last patient's statement
        if (totalDue > 0) {
            System.out.println("(" + count + ") "
                    + currentProfile.getFirstName() + " "
                    + currentProfile.getLastName() + " "
                    + currentProfile.getDOB() + " [amount due: $"
                    + String.format("%.2f", totalDue) + "]");
        }
    }

    /**
     * Runs the scheduler 
     * 
     * This method continously reads and process the commandline 
     * until the Q command is entered.
     */
    public void run() {
        System.out.println("Scheduler is running.");
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            // Split each part of a command for processing
            String[] parts = input.split(",");
            String command = parts[0].trim(); 

            // Process the command
            if(!processCommand(command, parts)) {
                break;
            }
        }
    }
}
