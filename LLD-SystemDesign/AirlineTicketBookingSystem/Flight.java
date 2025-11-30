package AirlineTicketBookingSystem;

public class Flight {
    public long id;
    public String flightNumber;
    public String origin;
    public String destination;
    public int totalSeats;

    public Flight(long id, String num, String o, String d, int seats) {
        this.id = id; this.flightNumber = num;
        this.origin = o; this.destination = d;
        this.totalSeats = seats;
    }
}
