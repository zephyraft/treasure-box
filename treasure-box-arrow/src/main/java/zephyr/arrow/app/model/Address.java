package zephyr.arrow.app.model;

public class Address {
    private final String street;
    private final int streetNumber;
    private final String city;
    private final int postalCode;

    public Address(String street, int streetNumber, String city, int postalCode) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
    }


    public String getStreet() {
        return street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public int getPostalCode() {
        return postalCode;
    }

}
