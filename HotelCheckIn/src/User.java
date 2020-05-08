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
    final int FUTURE_LIMIT = 365;           // 1 Year
    final int BOOKING_ATTEMPTS = 3;         // Stop trying after X attempts

    // Thread Variables
    private final Hotel hotel;


    public User(Hotel hotel, int count) {
        super();
        this.setName("User #" + count);
        this.hotel = hotel;

        //System.out.println(this.getName() + " initialised");
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
        }
    }


    // Non-Thread Methods
    private boolean attemptToBook() {
        Random rand = new Random();
        int roomChoice;
        int[] bookingSpan;
        String bookingRef;

        int attemptsToBook = 0;
        boolean bookingSuccess;
        do {
            bookingSuccess = false;

            roomChoice = rand.nextInt(hotel.roomCount() - 1);
            bookingSpan = createBookingDuration();

            boolean newRef;
            do {
                newRef = true;
                bookingRef = createBookingRef();

                // Check if the booking already exists
                if (hotel.lock_hotel.tryLock()) {
                    try {
                        for (var bk : hotel.get_bookings()) {
                            if (bk.get_reference().equals(bookingRef)) {
                                newRef = false;
                                break;
                            }
                        }
                    } finally {
                        hotel.lock_hotel.unlock();
                    }
                }
            } while (!newRef);

            // Attempt to create the booking
            if (hotel.lock_hotel.tryLock()) {
                try {
                    if (!hotel.roomBooked(bookingSpan, roomChoice)) {
                        if (hotel.bookRoom(bookingRef, bookingSpan, roomChoice)) {
                            System.out.println("[" + this.getName() + "] Booked Room " + roomChoice + " Successfully!");
                            bookingSuccess = true;
                        }
                    } else {
                        System.out.println("Sorry [" + this.getName() + "], Room " + roomChoice + " is booked on one of those days.  Better luck next time.");
                        bookingSuccess = false;
                    }
                } catch (Exception e) {
                    System.err.println("[" + this.getName() + "] has encountered an [" + e.getClass() + "] error @User.attemptToBook: \n\t" + e);
                } finally {
                    hotel.lock_hotel.unlock();
                }
            }
            attemptsToBook++;
        } while (!bookingSuccess && attemptsToBook < BOOKING_ATTEMPTS);

        return true;
    }


    private static String createBookingRef() {
        Random rand = new Random();

        return String.valueOf(Math.abs(rand.nextLong()));
    }


    private int[] createBookingDuration() {
        Random rand = new Random();
        int startDate;
        int endDate;
        int duration;
        int[] bookingDates;

        // Don't book backwards in time, or for an unreasonable amount of time
        do {
            startDate = rand.nextInt(FUTURE_LIMIT) + 1;
            endDate = rand.nextInt(FUTURE_LIMIT) + startDate;
        } while (
                (startDate > endDate)
                        || ((endDate - startDate) > MAX_BOOKING_DURATION)
                        || ((endDate - startDate) < 1)
        );

        // Get the duration of the Booking
        duration = endDate - startDate;
        bookingDates = new int[duration];

        for (int i = 0; i < bookingDates.length; i++) {
            bookingDates[i] = startDate++;
        }

        return bookingDates;
    }
}
