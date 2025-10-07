package DesignCarRentalSystem;

@SuppressWarnings("unused")
public class Customer {
    private final String name;
    private final String contact;
    private final String driverLicense;

    public Customer(String name, String contact, String driverLicense) {
        this.name = name;
        this.contact = contact;
        this.driverLicense = driverLicense;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getDriverLicense() {
        return driverLicense;
    }
}
