package cz.czechitas.webapp.form;

public class CustomerForm {

    private String firstName;
    private String lastName;
    private String address;
    private boolean deleted;
    private long version;



    public CustomerForm() {
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newValue) {
        firstName = newValue;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newValue) {
        lastName = newValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String newValue) {
        address = newValue;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean newValue) {
        deleted = newValue;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long newValue) {
        version = newValue;
    }
}
