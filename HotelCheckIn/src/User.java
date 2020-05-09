/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java User
 */


import JavaHotel.Hotel;
import JavaHotel.NoSuchBookingException;

import java.util.Random;


/**
 * The User Class extending the Thread Class
 *
 * The User Class represents a user of the Hotel system.  A User is a Thread, and so several can access the Hotel
 */
public class User extends Thread {
    // Constants
    final int MAX_BOOKING_DURATION = 31;    /**< The maximum permitted duration for a Booking */
    final int FUTURE_LIMIT = 365;           /**< The maximum length of time into the future to Book a Room */
    final int BOOKING_ATTEMPTS = 3;         /**< The maximum amount of attempts a User can have when trying to Book */

    // Thread Variables
    private final Hotel hotel;              /**< The Hotel that the User is interacting with */
    private String myBookingRef;            /**< The Reference for the Booking that this User has created */


    /**
     * Parameterised User Constructor Method
     *
     * @param hotel The shared Hotel object for this User to interact with
     * @param count The number to assign to this User
     */
    public User(Hotel hotel, int count) {
        super();
        this.setName("User #" + count);
        this.hotel = hotel;

        //System.out.println(this.getName() + " initialised");
    }


    ////////////////////
    // Thread Methods //
    ////////////////////


    /**
     * Main Thread `run` method
     *
     * This method is called by the `start` method inherited by the parent Thread Class.  Executes the Thread
     */
    public void run() {
        Random rand = new Random();
        boolean bookedSuccessfully = false;
        try {
            for (int i = 0; i < BOOKING_ATTEMPTS; i++) {
                if (!attemptToBook()) {
                    sleep(50); // Unsuccessful booking, try again
                } else {
                    bookedSuccessfully = true;
                    break; // Booked successfully
                }
            }

            if (bookedSuccessfully && myBookingRef != null) {
                // Small chance to decide to change the booking
                if (rand.nextInt(100) < 7) {
                    //System.out.println("[" + this.getName() + "] has changed their mind and wishes to change");
                    attemptToUpdate();
                }

                // Small chance to decide to cancel the booking
                if (rand.nextInt(100) < 3) {
                    //System.out.println("[" + this.getName() + "] has changed their mind and wishes to cancel");
                    attemptToCancel();
                }
            }
        } catch (Exception e) {
            System.err.println("[" + this.getName() + "] has encountered an [" + e.getClass() + "] error @User.run: \n\t" + e);
        }
    }


    ////////////////////////
    // Non-Thread Methods //
    ////////////////////////


    /**
     * Creates a Booking Reference number to be assigned to a new Booking
     *
     * @return  A new Booking Reference of the Long type
     */
    private static String createBookingRef() {
        Random rand = new Random();

        return String.valueOf(Math.abs(rand.nextLong()));
    }


    /**
     * Create a new randomised Booking, and attempt to send it to the Hotel Bookings list.
     *
     * @return  True if the Booking was added to the Hotel system successfully, False if it was not
     * @throws InterruptedException If the Thread is Interrupted at some point while in this method, throw an Exception
     */
    private boolean attemptToBook() throws InterruptedException {
        Random rand = new Random();
        int roomChoice;
        int[] bookingSpan;
        String bookingRef;

        boolean bookingSuccess;
        do {
            bookingSuccess = false;

            roomChoice = rand.nextInt(hotel.get_roomCount() - 1);
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
                } else {
                    sleep(50);
                }
            } while (!newRef);

            // Attempt to create the booking
            if (hotel.lock_hotel.tryLock()) {
                try {
                    if (!hotel.roomBooked(bookingSpan, roomChoice)) {
                        if (hotel.bookRoom(bookingRef, bookingSpan, roomChoice)) {
                            //System.out.println("[" + this.getName() + "] Booked Room " + roomChoice + " Successfully with BookingRef [#" + bookingRef + "]!");
                            myBookingRef = bookingRef;
                            bookingSuccess = true;
                        }
                    } else {
                        //System.out.println("Sorry [" + this.getName() + "], Room " + roomChoice + " is booked on one of those days.  Better luck next time.");
                        bookingSuccess = false;
                    }
                } catch (Exception e) {
                    System.err.println("[" + this.getName() + "] has encountered an [" + e.getClass() + "] error @User.attemptToBook: \n\t" + e);
                } finally {
                    hotel.lock_hotel.unlock();
                }
            } else {
                sleep(50);
            }
        } while (!bookingSuccess);

        return true;
    }


    /**
     * Creates a new randomised Booking, and attempt to update the Booking that this User previously created.
     *
     * @throws InterruptedException If the Thread is Interrupted at some point while in this method, throw an Exception
     */
    private void attemptToUpdate() throws InterruptedException {
        Random rand = new Random();
        boolean updateSuccessful = false;
        int roomChoice = rand.nextInt(hotel.get_roomCount() - 1);
        int[] bookingSpan = createBookingDuration();

        do {
            if (hotel.lock_hotel.tryLock()) {
                try {
//                    for (var bk : hotel.get_bookings()) {
//                        if (bk.get_reference().equals(myBookingRef)) {
//                            System.out.println("Booking [#" + myBookingRef + "] being updated from: \n\t" + (bk).toString());
//                            break;
//                        }
//                    }

                    if (!hotel.updateBooking(myBookingRef, bookingSpan, roomChoice)) {
                        updateSuccessful = false;
                    }
                } catch (NoSuchBookingException nsbe) {
                    System.err.println(nsbe.getMessage());
                } finally {
                    updateSuccessful = true;
                    hotel.lock_hotel.unlock();
                }
            } else {
                sleep(50);
            }
        } while (!updateSuccessful);
    }


    /**
     * Attempt to cancel the Booking that this User previously created.  Removes it from the Hotel Bookings list
     *
     * @throws InterruptedException If the Thread is Interrupted at some point while in this method, throw an Exception
     */
    private void attemptToCancel() throws InterruptedException {
        boolean cancelSuccessful = false;

        do {
            if (hotel.lock_hotel.tryLock()) {
                try {
                    hotel.cancelBooking(myBookingRef);
                } catch (NoSuchBookingException nsbe) {
                    System.err.println(nsbe.getMessage());
                } finally {
                    cancelSuccessful = true;
                    hotel.lock_hotel.unlock();
                }
            } else {
                sleep(50);
            }
        } while (!cancelSuccessful);
    }


    /**
     * Creates a randomised time frame to assign to a new Booking
     *
     * @return  The time frame in Days to assign to a new Booking
     */
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
