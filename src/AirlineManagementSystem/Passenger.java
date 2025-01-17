package AirlineManagementSystem;

public class Passenger {
    private int passengerId;
    private String passengerName;
    private String passengerPhone;
    private String passengerNationality;
    private String passengerPassport;
    private String flightCode;

    public Passenger(int passengerId, String passengerName, String passengerPhone, String passengerNationality, String passengerPassport, String flightCode) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.passengerNationality = passengerNationality;
        this.passengerPassport = passengerPassport;
        this.flightCode = flightCode;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public String getPassengerNationality() {
        return passengerNationality;
    }

    public String getPassengerPassport() {
        return passengerPassport;
    }

    public String getFlightCode() {
        return flightCode;
    }
}