package finalmission.reservation.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class CustomerInfo {

    private String name;
    private String phoneNumber;
    private String email;

    public CustomerInfo(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    protected CustomerInfo() {
    }

    public boolean isEqualCustomer(String name, String phoneNumber) {
        return this.name.equals(name) && this.phoneNumber.equals(phoneNumber);
    }

    public void editPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void editName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
