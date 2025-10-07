import java.util.ArrayList;
import java.util.List;

public class WareHouseController {
    List<WareHouse >warehouse = new ArrayList<>();
    WareHouseSelectionStretegy wStretegy ;

    public void addWareHouse(WareHouse wareHouse){
        warehouse.add(wareHouse);
    }

    public WareHouse getWareHouse(Address userAddress){
        this.wStretegy = new NearByWHouse() ;
        return this.wStretegy.selectWareHouse(warehouse, userAddress) ;
    }
}
