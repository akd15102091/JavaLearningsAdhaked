package DesignHotelManagementSystem;

import java.util.Date;

public class Reservation {
    private int reservationId;
    private Guest guest;
    private Room room;
    private ReservationStatus status;
    private Date fromDate;
    private Date toDate;
    
    public Reservation(Guest guest, Room room, Date fromDate, Date toDate) {
        this.reservationId = ReservationIdGenerator.getInstance().generateId();
        this.guest = guest;
        this.room = room;
        this.status = ReservationStatus.BOOKED;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public Date getFromDate() {
        return fromDate;
    }
    
    public Date getToDate() {
        return toDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    
}
