package cz.czechitas.webapp.domain;

public class Customer {

    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private boolean deleted;
    private long version;

    public Customer() {
    }

    public Customer(long id, String firstName, String lastName, String address, boolean deleted, long version) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.deleted = deleted;
        this.version = version;
    }

    public Customer(String firstName, String lastName, String address, boolean deleted, long version) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.deleted = deleted;
        this.version = version;
        this.id = 0L;         // když se nenastaví natvrdo - tak mi to místo null, vrací hodnotu 0 - což mi pak zas nebere při porovnání, protože nejde o Long ???
                                // co když pak budu chtít použít konstruktor bez ID?!
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long newValue) {
        id = newValue;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        return getId() == customer.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

}
