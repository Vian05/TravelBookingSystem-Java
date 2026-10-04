public class TravelAppTest {
    public static void main(String[] args) throws Exception {
        TravelApp app = new TravelApp();

        // Search tests
        assertTrue(!app.searchFlights("Jakarta", "Bali", "2026-10-20", 1).isEmpty(),
                "Valid flight search should return results.");
        assertTrue(app.searchFlights("Jakarta", "Bali", "2099-01-01", 1).isEmpty(),
                "Invalid date should return no flights.");
        assertTrue(!app.searchHotels("Bali", "2026-10-20", "2026-10-22", 1).isEmpty(),
                "Valid hotel search should return results.");

        // Flight booking test
        FlightReservation flightReservation =
                app.bookFlight("GA101", "Test User", "08123456789", 2);
        assertTrue(flightReservation != null,
                "Flight booking should succeed.");
        assertTrue(String.valueOf(flightReservation.getConfirmationNumber()).matches("\\d{6}"),
                "Confirmation number should contain 6 digits.");

        int seatsAfterBooking =
                flightReservation.getFlight().getAvailableSeats();
        assertTrue(seatsAfterBooking == 18,
                "Two flight seats should be deducted.");

        // Hotel booking test
        HotelReservation hotelReservation =
                app.bookHotel("H001", "Test User", "08123456789", 2);
        assertTrue(hotelReservation != null,
                "Hotel booking should succeed.");
        assertTrue(String.valueOf(hotelReservation.getConfirmationNumber()).matches("\\d{6}"),
                "Hotel confirmation number should contain 6 digits.");
        assertTrue(hotelReservation.getHotel().getAvailableRooms() == 8,
                "Two hotel rooms should be deducted.");

        // Pattern matching / polymorphism is exercised by cancellation.
        app.cancelReservation(flightReservation.getConfirmationNumber());
        assertTrue(app.getReservations().size() == 1,
                "Flight reservation should be removed after cancellation.");
        assertTrue(flightReservation.getFlight().getAvailableSeats() == 20,
                "Cancelled flight seats should be restored.");

        // Exception test
        boolean exceptionThrown = false;
        try {
            app.cancelReservation(999999);
        } catch (ReservationNotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown,
                "Cancelling an unknown confirmation number should throw an exception.");

        // Cancel hotel and verify room restoration.
        app.cancelReservation(hotelReservation.getConfirmationNumber());
        assertTrue(app.getReservations().isEmpty(),
                "All reservations should be removed.");
        assertTrue(hotelReservation.getHotel().getAvailableRooms() == 10,
                "Cancelled hotel rooms should be restored.");

        System.out.println("All TravelApp tests passed.");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
