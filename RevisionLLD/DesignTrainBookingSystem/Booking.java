package DesignTrainBookingSystem;

import java.time.LocalDate;

public class Booking {
    final String bookingId;
    final String trainId;
    final LocalDate date;
    final String passengerName;
    final String fromStation;
    final String toStation;
    final int seatNo; // 0-based seat index
    final int fromIndex, toIndex; // station indices
    final LocalDate bookedAt = LocalDate.now();


    public Booking(String bookingId, String trainId, LocalDate date, String passengerName,
        String fromStation, String toStation, int seatNo, int fromIndex, int toIndex) {
        this.bookingId = bookingId;
        this.trainId = trainId;
        this.date = date;
        this.passengerName = passengerName;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.seatNo = seatNo;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }


    @Override
    public String toString() {
        return String.format("Booking[id=%s, train=%s, date=%s, passenger=%s, %s->%s, seat=%d]",
        bookingId, trainId, date, passengerName, fromStation, toStation, seatNo + 1);
    }
}
