/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel.NoSuchBookingException
 */

package JavaHotel;


public class NoSuchBookingException extends Exception {
    public NoSuchBookingException(String bookingRef) {
        super("There is no booking with reference: " + bookingRef);
    }
}
