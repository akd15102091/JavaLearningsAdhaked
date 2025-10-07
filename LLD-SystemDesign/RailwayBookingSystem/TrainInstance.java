package RailwayBookingSystem;

import java.util.ArrayList;
import java.util.List;

// === TrainInstance (Train on a specific date) ===
public class TrainInstance {
    Train train;
    String date;
    List<SeatInstance> seatInstances = new ArrayList<>();

    public TrainInstance(Train train, String date) {
        this.train = train;
        this.date = date;
        for (int i = 1; i <= train.totalSeats; i++) {
            seatInstances.add(new SeatInstance(i));
        }
    }

    public boolean bookSeat(String passengerName, String from, String to) {
        int fromIndex = train.getStationIndex(from);
        int toIndex = train.getStationIndex(to);

        for (SeatInstance seat : seatInstances) {
            if (seat.isAvailable(fromIndex, toIndex)) {
                seat.addBooking(passengerName, fromIndex, toIndex);
                System.out.println("✅ Seat " + seat.seatNumber + " booked for " + passengerName +
                        " on " + train.name + " (" + date + ")");
                return true;
            }
        }

        System.out.println("❌ No seats available for " + passengerName + " on " + train.name + " (" + date + ")");
        return false;
    }

    public void printBookings() {
        for (SeatInstance seat : seatInstances) {
            System.out.println("Seat " + seat.seatNumber + ":");
            for (Booking b : seat.getBookings()) {
                System.out.println("  " + b.passengerName + " -> " +
                        train.stations.get(b.fromIndex).name + " to " + train.stations.get(b.toIndex).name);
            }
        }
    }
}
