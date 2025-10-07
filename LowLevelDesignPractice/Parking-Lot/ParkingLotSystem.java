import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    public ParkingManagerFactory parkingManagerFactory = new ParkingManagerFactory() ;;
    
    public void addTwoWheelerParkingSlots(){

        List<ParkingSpot> parkingSpotList = new ArrayList<>();
        parkingSpotList.add(new ParkingSpot(null, true, 1)) ;
        parkingSpotList.add(new ParkingSpot(null, true, 2)) ;
        parkingSpotList.add(new ParkingSpot(null, true, 3)) ;
        parkingSpotList.add(new ParkingSpot(null, true, 4)) ;

        this.parkingManagerFactory.setTwoWheelerParkingManager(parkingSpotList);

    }

    public void fourTwoWheelerParkingSlots(){

        List<ParkingSpot> parkingSpotList = new ArrayList<>();
        parkingSpotList.add(new ParkingSpot(null, true, 11)) ;
        parkingSpotList.add(new ParkingSpot(null, true, 12)) ;
        parkingSpotList.add(new ParkingSpot(null, true, 13)) ;
        parkingSpotList.add(new ParkingSpot(null, true, 14)) ;

        this.parkingManagerFactory.setFourWheelerParkingManager(parkingSpotList);

    }

    public static void main(String[] args) {

        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.addTwoWheelerParkingSlots() ;
        parkingLotSystem.fourTwoWheelerParkingSlots() ;


        // A four wheeler comes
        System.out.println("Vehicle is entering...");
        Vehicle vehicle1 = new Vehicle(VehicleType.FOUR_WHEELER, "KA-01-4444") ;
        EntranceGate entranceGate = new EntranceGate();
        entranceGate.setParkingManagerFactory(parkingLotSystem.parkingManagerFactory);
        entranceGate.bookSpot(vehicle1) ;
        Ticket vehicle1Ticket = entranceGate.getTicket();


        Vehicle vehicle2 = new Vehicle(VehicleType.TWO_WHEELER, "KA-01-2222") ;
        entranceGate.bookSpot(vehicle2) ;
        Ticket vehicle2Ticket = entranceGate.getTicket();


        //setup exitgate
        ExitGate exitGate = new ExitGate() ;
        exitGate.setParkingManagerFactory(parkingLotSystem.parkingManagerFactory);

        System.out.println("Vehicle1 is exiting...");
        exitGate.setTicket(vehicle1Ticket);
        exitGate.removeVehicle();

        System.out.println("Vehicle2 is exiting...");
        exitGate.setTicket(vehicle2Ticket);
        exitGate.removeVehicle();

        
    }
}
