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
    static final int MAX_ROOMS = 50;            // 1000 Rooms
    static final int MIN_ROOMS = 10;
    static final int MAX_BOOKING_DURATION = 91; // 3 Months
    static final int FUTURE_LIMIT = 18262;      // 50 Years
    static final double FILL_PERCENT = 0.7;
    static final int USER_COUNT = 10;


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

        System.out.println("The Java Hotel Before:\n" + javaHotel.toString());

        runSingleThreadedSimulation(javaHotel);

        System.out.println("The Java Hotel After:\n" + javaHotel.toString());
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

        for (int i = 0; i < (int)(hotel.roomCount() * FILL_PERCENT); i++) {
            roomChoice = rand.nextInt(hotel.roomCount() - 1);
            bookingSpan = createBookingDuration();
            bookingRef = createBookingRef();

            if (!hotel.roomBooked(bookingSpan, roomChoice)) {
                if (hotel.bookRoom(bookingRef, bookingSpan, roomChoice))
                    System.out.println("Room " + roomChoice + " Booked!");
            }
        }
    }


    private static int[] createBookingDuration() {
        Random rand = new Random();
        int startDate = Integer.MAX_VALUE;  // 0 is 1970-01-01
        int endDate = Integer.MIN_VALUE;    // No defined end date
        int duration = Integer.MIN_VALUE;
        int[] booking = null;

        // Don't book backwards in time, or for an unreasonable amount of time
        do {
            startDate = rand.nextInt(FUTURE_LIMIT);
            endDate = rand.nextInt(FUTURE_LIMIT) + startDate;
        } while ((startDate > endDate) || ((endDate - startDate) > MAX_BOOKING_DURATION));

        duration = endDate - startDate;
        booking = new int[duration];

        int i = 0;
        for (var ignored : booking) {
            booking[i] = i++;
        }

        return booking;
    }


    private static String createBookingRef() {
        Random rand = new Random();

        return String.valueOf(rand.nextLong()) +
                rand.nextLong();
    }
}
