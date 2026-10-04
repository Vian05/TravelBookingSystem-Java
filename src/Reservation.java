public sealed abstract class Reservation implements Bookable
        permits FlightReservation, HotelReservation {

    private int confirmationNumber;
    private String customerName;
    private String contact;

    protected Reservation(int confirmationNumber, String customerName, String contact) {
        this.confirmationNumber = confirmationNumber;
        this.customerName = customerName;
        this.contact = contact;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public abstract void book();

    @Override
    public abstract void cancel();

    public abstract void display();
}
