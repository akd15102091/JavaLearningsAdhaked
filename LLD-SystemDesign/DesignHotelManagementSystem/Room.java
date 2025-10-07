package DesignHotelManagementSystem;

import java.util.Date;
import java.util.List;

public class Room {
    private int roomNumber;
    private RoomType type;
    
    public Room(int roomNumber, RoomType type) {
        this.roomNumber = roomNumber;
        this.type = type;
    }
    
    public RoomType getType() {
        return type;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    
    public boolean isAvailable(Date fromDate, Date toDate, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(this) && 
                !(toDate.before(reservation.getFromDate()) || fromDate.after(reservation.getToDate()))) {
                return false;
            }
        }
        return true;
    }
}
