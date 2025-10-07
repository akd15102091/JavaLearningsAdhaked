import java.util.ArrayList;
import java.util.List;

public class CarRentalSystem{
        List<User> users;
        List<Store>stores;
       
        public CarRentalSystem(List<User> users, List<Store> stores) {
            this.users = users;
            this.stores = stores;
        }
       
        public List<Store> getAllStoresByCity(String city){
            System.out.println("Fetched all stores in city "+city);

            List<Store> allStores = new ArrayList<>();
            for(int i=0;i<stores.size();i++){
                if(stores.get(i).getLocation().equals(city)){
                    allStores.add(stores.get(i));
                }
            }
            return allStores;
        }
}