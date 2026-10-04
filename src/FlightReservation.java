public final class FlightReservation extends Reservation {
    private Flight flight;
    private int passengerCount;

    public FlightReservation(int confirmationNumber, String customerName,
                             String contact, Flight flight, int passengerCount) {
        super(confirmationNumber, customerName, contact);
        this.flight = flight;
        this.passengerCount = passengerCount;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    @Override
    public void book() {
        System.out.println("Flight booking confirmed: " + getConfirmationNumber());
    }

    @Override
    public void cancel() {
        System.out.println("Flight reservation cancelled: " + getConfirmationNumber());
    }

    @Override
    public void display() {
        System.out.println("=== Flight Reservation ===");
        System.out.println("Confirmation Number: " + getConfirmationNumber());
        System.out.println("Customer Name: " + getCustomerName());
        System.out.println("Contact: " + getContact());
        System.out.println("Flight: " + flight.getFlightNumber());
        System.out.println("Route: " + flight.getOrigin() + " -> " + flight.getDestination());
        System.out.println("Date: " + flight.getDate());
        System.out.println("Departure: " + flight.getDepartureTime());
        System.out.println("Arrival: " + flight.getArrivalTime());
        System.out.println("Passenger Count: " + passengerCount);
        System.out.println("Price per Passenger: " + flight.getPrice());
    }
}
