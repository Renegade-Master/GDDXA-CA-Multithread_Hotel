/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel main
 */

import JavaHotel.Hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
    Read from file, or generate booking randomly.  Dev choice.
 */
public class Main {
    static final int MAX_ROOMS = 30;
    static final int MIN_ROOMS = 10;
    static final int MAX_BOOKING_DURATION = 31; // 1 Month
    //static final int FUTURE_LIMIT = 1826;       // 5 Years
    static final int FUTURE_LIMIT = 365;        // 1 Year
    static final int USER_COUNT = 100;          // Customers


    public static void main(String[] args) {
        Hotel javaHotel;
        int[] rooms = null;

        if (args.length > 0) {
            System.out.println("Args provided\nUsing Provided Hotel bookings...\n");

            rooms = new int[args.length];

            int i = 0;
            for (var t : args) {
                rooms[i++] = Integer.parseInt(t);
            }
        } else {
            System.out.println("No args provided\nRandomising Hotel bookings...\n");

            Random rand = new Random();
            int roomCount = rand.nextInt(MAX_ROOMS - MIN_ROOMS) + MIN_ROOMS;
            rooms = new int[roomCount];

            int i = 0;
            for (var ignored : rooms) {
                rooms[i] = i++;
            }
        }

        javaHotel = new Hotel(rooms);

        System.out.println("\nThe Java Hotel Before:\n" + javaHotel.toString());

        // Run the simulation
        runSingleThreadedSimulation(javaHotel);

        System.out.println("\nThe Java Hotel After:\n" + javaHotel.toString());
    }


    private static void runMultiThreadedSimulation(Hotel hotel) {
        List<Thread> users = new ArrayList<Thread>();
        for (int i = 0; i < USER_COUNT; i++) {
            users.add(new Thread());
        }

        for (var user : users) {
            user.start();
        }

    }


    private static void runSingleThreadedSimulation(Hotel hotel) {
        Random rand = new Random();
        int roomChoice = Integer.MIN_VALUE;
        int[] bookingSpan = null;
        String bookingRef = null;

        for (int i = 0; i < USER_COUNT; i++) {
            roomChoice = rand.nextInt(hotel.roomCount() - 1);
            bookingSpan = createBookingDuration();

            boolean newRef = false;
            do {
                newRef = true;
                bookingRef = createBookingRef();

                for (var bk : hotel.get_bookings()) {
                    if (bk.get_reference().equals(bookingRef)) {
                        newRef = false;
                        break;
                    }
                }
            } while(!newRef);

            if (!hotel.roomBooked(bookingSpan, roomChoice)) {
                if (hotel.bookRoom(bookingRef, bookingSpan, roomChoice))
                    System.out.println("Room " + roomChoice + " Booked Successfully!");
            } else {
                System.out.println("Room " + roomChoice + " is booked on one of those days.  Better luck next time.");
            }

        }
    }


    private static int[] createBookingDuration() {
        Random rand = new Random();
        int startDate = Integer.MAX_VALUE;
        int endDate = Integer.MIN_VALUE;
        int duration = Integer.MIN_VALUE;
        int[] bookingDates = null;

        // Don't book backwards in time, or for an unreasonable amount of time
        do {
            startDate = rand.nextInt(FUTURE_LIMIT);
            endDate = rand.nextInt(FUTURE_LIMIT) + startDate;
        } while ((startDate > endDate) || ((endDate - startDate) > MAX_BOOKING_DURATION));

        // Get the duration of the Booking
        duration = endDate - startDate;
        bookingDates = new int[duration];

        for (int i = 0; i < bookingDates.length; i++) {
            bookingDates[i] = startDate++;
        }

        return bookingDates;
    }


    private static String createBookingRef() {
        Random rand = new Random();

        return String.valueOf(Math.abs(rand.nextLong()));
    }
}
