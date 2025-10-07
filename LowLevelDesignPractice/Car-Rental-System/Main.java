import java.util.*;

public class Main {

    public static List<User> addUsers(){
        List<User>userList = new ArrayList<>();
        // add users
        User user1 = new User("AKD","12345678234");
        User user2 = new User("NK","91011121314");        
        userList.add(user1);
        userList.add(user2);
        return userList;
    }
    public static List<Vehicle> addVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle1 = new Vehicle("skoda", "1425", VehicleType.CAR, Status.AVAILABLE);
        Vehicle vehicle2 = new Vehicle("swift", "1855", VehicleType.CAR, Status.AVAILABLE);
        Vehicle vehicle3 = new Vehicle("mini cooper", "4518", VehicleType.CAR, Status.AVAILABLE);
        Vehicle vehicle4 = new Vehicle("tigun", "7207", VehicleType.CAR, Status.AVAILABLE);
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        return vehicles;
    }
    public static List<Store> addStore(List<Vehicle>vehicles){
        List<Store> store = new ArrayList<>();
        Store store1 = new Store(1,"firstStore","jaipur");
        store1.setVehicle(vehicles);
        store.add(store1);
        return store;
    } 

    public static void main(String[] args) {
       
        // Users list
        List<User> users = addUsers();

        // Vehicles list
        List<Vehicle> vehicles = addVehicles();

        // Stores list
        List<Store> stores = addStore(vehicles);


        CarRentalSystem carRentalSystem = new CarRentalSystem(users, stores);

        System.out.println("\n-------------------------- FLOW STARTED --------------------------\n");

        //0. User comes
        User user = users.get(0);
        System.out.println("User 0 : "+user.getName()+" comes");

        //1. user search store based on location
        List<Store> storesByCity = carRentalSystem.getAllStoresByCity("jaipur");

        //2. get All vehicles you are interested in (based upon different filters) (for let say store1)
        Store store1 = storesByCity.get(0 ) ;
        List<Vehicle> storeVehicles =  store1.getStoreVehiclesByType(VehicleType.CAR) ;

        //3. reserving the particular vehicle
        Reservation reservation1 = store1.createReservation(storeVehicles.get(0), user);

        //4. generate the bill
        Bill bill = new Bill(reservation1);

        //5. make payment
        Payment payment = new Payment(bill);
        payment.payBill();

       //6. trip completed, submit the vehicle and close the reservation
        store1.completeReservation(reservation1.getReservationId());

        System.out.println("\n-------------------------- FLOW END --------------------------\n");
    }
}
