public final class HotelReservation extends Reservation {
    private Hotel hotel;
    private int guestCount;

    public HotelReservation(int confirmationNumber, String customerName,
                            String contact, Hotel hotel, int guestCount) {
        super(confirmationNumber, customerName, contact);
        this.hotel = hotel;
        this.guestCount = guestCount;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    @Override
    public void book() {
        System.out.println("Hotel booking confirmed: " + getConfirmationNumber());
    }

    @Override
    public void cancel() {
        System.out.println("Hotel reservation cancelled: " + getConfirmationNumber());
    }

    @Override
    public void display() {
        System.out.println("=== Hotel Reservation ===");
        System.out.println("Confirmation Number: " + getConfirmationNumber());
        System.out.println("Customer Name: " + getCustomerName());
        System.out.println("Contact: " + getContact());
        System.out.println("Hotel: " + hotel.getName());
        System.out.println("Location: " + hotel.getLocation());
        System.out.println("Check-in: " + hotel.getCheckIn());
        System.out.println("Check-out: " + hotel.getCheckOut());
        System.out.println("Guest Count: " + guestCount);
        System.out.println("Price per Night: " + hotel.getPricePerNight());
    }
}
