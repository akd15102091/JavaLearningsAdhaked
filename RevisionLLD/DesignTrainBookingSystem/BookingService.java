package DesignTrainBookingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class BookingService {
    private final TrainRepository trainRepo;
    private final TrainInstanceRepository instanceRepo;


    public BookingService(TrainRepository trainRepo, TrainInstanceRepository instanceRepo) {
        this.trainRepo = trainRepo;
        this.instanceRepo = instanceRepo;
    }


    // Search trains that pass through source -> destination (in that order) and have availability on date
    public List<TrainInstance> search(String source, String destination, LocalDate date) {
        List<TrainInstance> result = new ArrayList<>();
        for (Train t : trainRepo.allTrains()) {
            int srcIdx = t.indexOfStation(source);
            int dstIdx = t.indexOfStation(destination);
            if (srcIdx >= 0 && dstIdx >= 0 && dstIdx > srcIdx) {
                TrainInstance ti = instanceRepo.getOrCreate(t, date);
                if (ti.hasAvailability(srcIdx, dstIdx)) result.add(ti);
            }
        }
        return result;
    }


    // Make a booking; returns Booking if success, otherwise null
    public Booking book(String trainId, LocalDate date, String passengerName, String source, String destination) {
        Optional<Train> ot = trainRepo.getTrain(trainId);
        if (ot.isEmpty()) throw new NoSuchElementException("Train not found: " + trainId);
    
        Train t = ot.get();
        int srcIdx = t.indexOfStation(source);
        int dstIdx = t.indexOfStation(destination);
        if (!(srcIdx >=0 && dstIdx > srcIdx)) throw new IllegalArgumentException("Invalid source/destination for train");

        TrainInstance ti = instanceRepo.getOrCreate(t, date);
        return ti.tryBook(passengerName, source, destination, srcIdx, dstIdx);
    }


    // Print bookings per train per date
    public void printBookingsForTrainOnDate(String trainId, LocalDate date) {
        Optional<TrainInstance> oti = instanceRepo.get(trainId, date);
        if (oti.isEmpty()) {
            System.out.println("No bookings (or train instance) for " + trainId + " on " + date);
            return;
        }
        TrainInstance ti = oti.get();
        System.out.println("Bookings for " + ti + ":");
        List<Booking> list = ti.getBookings();
        if (list.isEmpty()) System.out.println(" (none)");
        else list.forEach(b -> System.out.println(" " + b));
    }


    public void printAllBookingsForDate(LocalDate date) {
        List<TrainInstance> tis = instanceRepo.getAllInstancesForDate(date);
        if (tis.isEmpty()) { System.out.println("No instances on " + date); return; }
        for (TrainInstance ti : tis) {
            printBookingsForTrainOnDate(ti.train.trainId, date);
        }
    }
}
