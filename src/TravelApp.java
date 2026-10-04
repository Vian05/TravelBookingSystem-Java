import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TravelApp {
    private final List<Flight> flights;
    private final List<Hotel> hotels;
    private final List<Reservation> reservations;
    private final Random random;

    public TravelApp() {
        flights = new ArrayList<>();
        hotels = new ArrayList<>();
        reservations = new ArrayList<>();
        random = new Random();
        loadSampleData();
    }

    private void loadSampleData() {
        flights.add(new Flight(
                "GA101", "Jakarta", "Bali", "2026-10-20",
                "08:00", "11:00", 1500000, 20
        ));
        flights.add(new Flight(
                "ID202", "Jakarta", "Surabaya", "2026-10-20",
                "10:00", "11:30", 900000, 15
        ));
        flights.add(new Flight(
                "QZ303", "Bali", "Jakarta", "2026-10-21",
                "13:00", "14:00", 1200000, 10
        ));
        flights.add(new Flight(
                "JT404", "Jakarta", "Bali", "2026-10-22",
                "15:00", "18:00", 1350000, 8
        ));

        hotels.add(new Hotel(
                "H001", "Bali Paradise Hotel", "Bali",
                "2026-10-20", "2026-10-22", 750000, 10
        ));
        hotels.add(new Hotel(
                "H002", "Jakarta Grand Hotel", "Jakarta",
                "2026-10-20", "2026-10-23", 650000, 8
        ));
        hotels.add(new Hotel(
                "H003", "Surabaya City Hotel", "Surabaya",
                "2026-10-20", "2026-10-22", 500000, 12
        ));
        hotels.add(new Hotel(
                "H004", "Bali Sunset Resort", "Bali",
                "2026-10-21", "2026-10-24", 900000, 5
        ));
    }

    public List<Flight> searchFlights(String origin, String destination,
                                      String date, int passengerCount) {
        return flights.stream()
                .filter(flight ->
                        flight.getOrigin().equalsIgnoreCase(origin)
                                && flight.getDestination().equalsIgnoreCase(destination)
                                && flight.getDate().equals(date)
                                && flight.getAvailableSeats() >= passengerCount)
                .collect(Collectors.toList());
    }

    public List<Hotel> searchHotels(String location, String checkIn,
                                    String checkOut, int guestCount) {
        return hotels.stream()
                .filter(hotel ->
                        hotel.getLocation().equalsIgnoreCase(location)
                                && hotel.getCheckIn().equals(checkIn)
                                && hotel.getCheckOut().equals(checkOut)
                                && hotel.getAvailableRooms() >= guestCount)
                .collect(Collectors.toList());
    }

    public Flight getFlightByNumber(String flightNumber) {
        return flights.stream()
                .filter(flight -> flight.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public Hotel getHotelById(String hotelId) {
        return hotels.stream()
                .filter(hotel -> hotel.getHotelId().equalsIgnoreCase(hotelId))
                .findFirst()
                .orElse(null);
    }

    public FlightReservation bookFlight(String flightNumber, String customerName,
                                        String contact, int passengerCount) {
        Flight flight = getFlightByNumber(flightNumber);

        if (flight == null || passengerCount <= 0
                || flight.getAvailableSeats() < passengerCount) {
            return null;
        }

        int confirmationNumber = generateConfirmationNumber();
        FlightReservation reservation = new FlightReservation(
                confirmationNumber, customerName, contact, flight, passengerCount
        );

        flight.setAvailableSeats(flight.getAvailableSeats() - passengerCount);
        reservations.add(reservation);
        reservation.book();

        return reservation;
    }

    public HotelReservation bookHotel(String hotelId, String customerName,
                                      String contact, int guestCount) {
        Hotel hotel = getHotelById(hotelId);

        if (hotel == null || guestCount <= 0
                || hotel.getAvailableRooms() < guestCount) {
            return null;
        }

        int confirmationNumber = generateConfirmationNumber();
        HotelReservation reservation = new HotelReservation(
                confirmationNumber, customerName, contact, hotel, guestCount
        );

        hotel.setAvailableRooms(hotel.getAvailableRooms() - guestCount);
        reservations.add(reservation);
        reservation.book();

        return reservation;
    }

    public void cancelReservation(int confirmationNumber)
            throws ReservationNotFoundException {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getConfirmationNumber() == confirmationNumber)
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException(
                        "Reservation not found: " + confirmationNumber
                ));

        if (reservation instanceof FlightReservation flightReservation) {
            Flight flight = flightReservation.getFlight();
            flight.setAvailableSeats(
                    flight.getAvailableSeats() + flightReservation.getPassengerCount()
            );
        } else if (reservation instanceof HotelReservation hotelReservation) {
            Hotel hotel = hotelReservation.getHotel();
            hotel.setAvailableRooms(
                    hotel.getAvailableRooms() + hotelReservation.getGuestCount()
            );
        }

        reservation.cancel();
        reservations.remove(reservation);
    }

    private int generateConfirmationNumber() {
        int confirmationNumber;

        do {
            confirmationNumber = 100000 + random.nextInt(900000);
        } while (isConfirmationNumberUsed(confirmationNumber));

        return confirmationNumber;
    }

    private boolean isConfirmationNumberUsed(int confirmationNumber) {
        return reservations.stream()
                .anyMatch(reservation ->
                        reservation.getConfirmationNumber() == confirmationNumber);
    }

    public List<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    public List<Hotel> getHotels() {
        return new ArrayList<>(hotels);
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}
