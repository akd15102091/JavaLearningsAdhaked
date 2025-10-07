package DesignRideSharingService.RideTypeFactory;

public class RideFactory {
    public static RideType getRideType(String type){
        switch (type.toLowerCase()) {
            case "premium":
                return new PremiumRide();
            default :
                return new RegularRide();
        }
    }
}
