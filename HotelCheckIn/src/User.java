/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java User
 */


import JavaHotel.Hotel;

import java.util.Random;


public class User extends Thread {
    // Constants
    final int MAX_BOOKING_DURATION = 31;    // 1 Month
    //final int FUTURE_LIMIT = 1826;          // 5 Years
    final int FUTURE_LIMIT = 365;           // 1 Year
    final int BOOKING_ATTEMPTS = 3;         //

    // Thread Variables
    Hotel hotel;


    public User(Hotel hotel, int count) {
        super();
        this.setName("User #" + count);
        this.hotel = hotel;

        System.out.println("User initialised");
    }


    private static String createBookingRef() {
        Random rand = new Random();

        return String.valueOf(Math.abs(rand.nextLong()));
    }


    // Thread Methods
    public void run() {
        try {
            for (int i = 0; i < BOOKING_ATTEMPTS; i++) {
                if (!attemptToBook()) {
                    sleep(100); // Unsuccessful booking, try again
                } else {
                    return; // Booked successfully
                }
            }
        } catch (Exception e) {
            System.err.println("[" + this.getName() + "] has encountered an [" + e.getClass() + "] error @User.run: \n\t" + e);
        } finally {
            // Do something to signify Thread failure
            this.interrupt();
        }
    }


    // Non-Thread Methods
    private boolean attemptToBook() {
        Random rand = new Random();
        int roomChoice = Integer.MIN_VALUE;
        int[] bookingSpan = null;
        String bookingRef = null;


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
        } while (!newRef);

        if (!hotel.roomBooked(bookingSpan, roomChoice)) {
            if (hotel.bookRoom(bookingRef, bookingSpan, roomChoice))
                System.out.println("Room " + roomChoice + " Booked Successfully!");
            return true;
        } else {
            System.out.println("Room " + roomChoice + " is booked on one of those days.  Better luck next time.");
            return false;
        }
    }


    private int[] createBookingDuration() {
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
}
