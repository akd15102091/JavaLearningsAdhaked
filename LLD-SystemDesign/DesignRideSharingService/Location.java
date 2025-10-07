package DesignRideSharingService;

@SuppressWarnings("unused")
public class Location {
    private final double latitude, longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double distanceTo(Location other) {
        return Math.sqrt(Math.pow(Math.abs(other.latitude - this.latitude), 2) + Math.pow(Math.abs(other.longitude - this.longitude), 2));
    }
}
