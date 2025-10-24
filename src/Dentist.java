public class Dentist {

    private Integer Id;
    private Integer registration;
    private String firstName;
    private String lastName;

    public Dentist(Integer id, Integer registration, String firstName, String lastName) {
        this.Id = id;
        this.registration = registration;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getRegistration() {
        return registration;
    }

    public void setRegistration(Integer registration) {
        this.registration = registration;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Dentist{" +
                "Id=" + Id +
                ", registration=" + registration +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
