package DesignRideSharingService;

@SuppressWarnings("unused")
public class Driver {
    private final String name;
    private Location location;

    public Driver(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Driver [name=" + name + "]";
    }

    
}