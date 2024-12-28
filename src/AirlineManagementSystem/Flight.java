package AirlineManagementSystem;  // Make sure this is the same package as AddFlightController

public class Flight {
    private String flightCode;
    private String flightName;
    private String source;
    private String destination;
    private int capacity;
    private String dateOfJourney;

    // Constructor
    public Flight(String flightCode, String flightName, String source, String destination, int capacity, String dateOfJourney) {
        this.flightCode = flightCode;
        this.flightName = flightName;
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
        this.dateOfJourney = dateOfJourney;
    }

    // Getters and Setters
    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }
}
