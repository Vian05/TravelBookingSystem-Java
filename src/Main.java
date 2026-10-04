import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TravelApp app = new TravelApp();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> searchFlights();
                    case 2 -> searchHotels();
                    case 3 -> bookFlight();
                    case 4 -> bookHotel();
                    case 5 -> cancelReservation();
                    case 6 -> viewReservations();
                    case 0 -> {
                        running = false;
                        System.out.println("Thank you for using Travel Booking System.");
                    }
                    default -> System.out.println("Invalid menu choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("=== Travel Booking System ===");
        System.out.println("1. Search Flights");
        System.out.println("2. Search Hotels");
        System.out.println("3. Book Flight");
        System.out.println("4. Book Hotel");
        System.out.println("5. Cancel Reservation");
        System.out.println("6. View Reservations");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void searchFlights() {
        System.out.println();
        System.out.println("=== Search Flights ===");

        String origin = readRequiredString("Origin: ");
        String destination = readRequiredString("Destination: ");
        String date = readRequiredString("Date (YYYY-MM-DD): ");
        int passengerCount = readPositiveInt("Passenger count: ");

        List<Flight> results = app.searchFlights(
                origin, destination, date, passengerCount
        );

        if (results.isEmpty()) {
            System.out.println("No flights found.");
            return;
        }

        System.out.println();
        System.out.println("Available Flights:");
        for (Flight flight : results) {
            System.out.println(
                    flight.getFlightNumber() + " | "
                            + flight.getOrigin() + " -> " + flight.getDestination()
                            + " | Date: " + flight.getDate()
                            + " | " + flight.getDepartureTime()
                            + " - " + flight.getArrivalTime()
                            + " | Price: Rp" + flight.getPrice()
                            + " | Seats: " + flight.getAvailableSeats()
            );
        }
    }

    private static void searchHotels() {
        System.out.println();
        System.out.println("=== Search Hotels ===");

        String location = readRequiredString("Location: ");
        String checkIn = readRequiredString("Check-in (YYYY-MM-DD): ");
        String checkOut = readRequiredString("Check-out (YYYY-MM-DD): ");
        int guestCount = readPositiveInt("Guest count: ");

        List<Hotel> results = app.searchHotels(
                location, checkIn, checkOut, guestCount
        );

        if (results.isEmpty()) {
            System.out.println("No hotels found.");
            return;
        }

        System.out.println();
        System.out.println("Available Hotels:");
        for (Hotel hotel : results) {
            System.out.println(
                    hotel.getHotelId() + " | "
                            + hotel.getName()
                            + " | Location: " + hotel.getLocation()
                            + " | " + hotel.getCheckIn()
                            + " to " + hotel.getCheckOut()
                            + " | Price/night: Rp" + hotel.getPricePerNight()
                            + " | Rooms: " + hotel.getAvailableRooms()
            );
        }
    }

    private static void bookFlight() {
        System.out.println();
        System.out.println("=== Book Flight ===");

        String flightNumber = readRequiredString("Flight number: ");
        String customerName = readRequiredString("Passenger name: ");
        String contact = readRequiredString("Contact: ");
        int passengerCount = readPositiveInt("Passenger count: ");

        FlightReservation reservation = app.bookFlight(
                flightNumber, customerName, contact, passengerCount
        );

        if (reservation == null) {
            System.out.println(
                    "Booking failed. Check the flight number and available seats."
            );
            return;
        }

        System.out.println("Flight booking successful.");
        System.out.println(
                "Confirmation Number: " + reservation.getConfirmationNumber()
        );
    }

    private static void bookHotel() {
        System.out.println();
        System.out.println("=== Book Hotel ===");

        String hotelId = readRequiredString("Hotel ID: ");
        String customerName = readRequiredString("Guest name: ");
        String contact = readRequiredString("Contact: ");
        int guestCount = readPositiveInt("Guest count: ");

        HotelReservation reservation = app.bookHotel(
                hotelId, customerName, contact, guestCount
        );

        if (reservation == null) {
            System.out.println(
                    "Booking failed. Check the hotel ID and available rooms."
            );
            return;
        }

        System.out.println("Hotel booking successful.");
        System.out.println(
                "Confirmation Number: " + reservation.getConfirmationNumber()
        );
    }

    private static void cancelReservation() {
        System.out.println();
        System.out.println("=== Cancel Reservation ===");

        int confirmationNumber = readPositiveInt("Confirmation number: ");

        try {
            app.cancelReservation(confirmationNumber);
            System.out.println("Reservation cancelled successfully.");
        } catch (ReservationNotFoundException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }

    private static void viewReservations() {
        System.out.println();
        System.out.println("=== Reservations ===");

        List<Reservation> reservations = app.getReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for (Reservation reservation : reservations) {
            reservation.display();
            System.out.println();
        }
    }

    private static String readRequiredString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                int value = Integer.parseInt(scanner.nextLine());

                if (value > 0) {
                    return value;
                }

                System.out.println("Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
