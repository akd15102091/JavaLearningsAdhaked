package DesignHotelManagementSystem;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DriverMain {
    public static void main(String[] args) {
        HotelManagementSystem hotel = new HotelManagementSystem();
        hotel.addRoom(RoomFactory.createRoom(101, RoomType.SINGLE));
        hotel.addRoom(RoomFactory.createRoom(102, RoomType.DOUBLE));
        
        Guest guest1 = new Guest("John Doe", "1234567890");
        Date fromDate = new GregorianCalendar(2025, Calendar.MARCH, 28).getTime();
        Date toDate = new GregorianCalendar(2025, Calendar.APRIL, 2).getTime();
        Reservation reservation = hotel.bookRoom(guest1, RoomType.SINGLE, fromDate, toDate);
        
        if (reservation != null) {
            System.out.println("Reservation successful.");
        } else {
            System.out.println("No rooms available for the given period.");
        }
        
        hotel.generateReport();
    }
}
