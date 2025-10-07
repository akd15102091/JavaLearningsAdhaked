package DesignHotelManagementSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Hotel Management System (SRP - Separate responsibilities for rooms, reservations, and reporting)
class HotelManagementSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;
    
    public HotelManagementSystem() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public Reservation bookRoom(Guest guest, RoomType type, Date fromDate, Date toDate) {
        for (Room room : rooms) {
            if (room.getType() == type && room.isAvailable(fromDate, toDate, reservations)) {
                Reservation reservation = new Reservation(guest, room, fromDate, toDate);
                reservations.add(reservation);
                return reservation;
            }
        }
        return null;
    }
    
    public void generateReport() {
        System.out.println("Total Reservations: " + reservations.size());
    }
}