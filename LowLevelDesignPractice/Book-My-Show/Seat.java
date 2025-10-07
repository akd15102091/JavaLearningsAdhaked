public class Seat {
    int id;
    int rowNumber;
    SeatType seatType;
    boolean isOccupied =false ;
    public Seat(int id, int rowNumber, SeatType seatType, boolean isOccupied) {
        this.id = id;
        this.rowNumber = rowNumber;
        this.seatType = seatType;
        this.isOccupied = isOccupied;
    }
    
}
