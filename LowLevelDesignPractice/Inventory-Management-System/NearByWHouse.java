import java.util.List;

public class NearByWHouse implements WareHouseSelectionStretegy {

    @Override
    public WareHouse selectWareHouse(List<WareHouse> warehouse , Address address) {
        System.out.println("nearby warehouse selected");
        for(int i=0;i<warehouse.size();i++){
            if(warehouse.get(i).address.pinCode == address.pinCode){
                return warehouse.get(i);
            }
        }
        return null;
    }
    
}   
