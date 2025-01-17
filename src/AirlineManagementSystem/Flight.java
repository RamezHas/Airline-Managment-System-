package AirlineManagementSystem;

import java.time.LocalDate;

public class Flight {
    private String flightCode;
    private String flightName;
    private String flightSource;
    private String flightDestination;
    private int flightCapacity;
    private LocalDate flightDateOfJourney;

    public Flight(String flightCode, String flightName, String flightSource, String flightDestination, int flightCapacity, LocalDate flightDateOfJourney) {
        this.flightCode = flightCode;
        this.flightName = flightName;
        this.flightSource = flightSource;
        this.flightDestination = flightDestination;
        this.flightCapacity = flightCapacity;
        this.flightDateOfJourney = flightDateOfJourney;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getFlightSource() {
        return flightSource;
    }

    public String getFlightDestination() {
        return flightDestination;
    }

    public int getFlightCapacity() {
        return flightCapacity;
    }

    public LocalDate getFlightDateOfJourney() {
        return flightDateOfJourney;
    }
}