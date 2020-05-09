/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel.Hotel
 */

package JavaHotel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * The Hotel Class
 *
 * A Hotel is a collection of Rooms, with a record for Bookings
 */
public class Hotel {
    public final Lock lock_hotel = new ReentrantLock();                 /**< The Lock for controlling access to the Hotel */
    public final Condition addNewBooking = lock_hotel.newCondition();   /**< A Condition for fine-grained access control */
    public final Condition readBookings = lock_hotel.newCondition();    /**< A Condition for fine-grained access control */
    private final List<Room> _rooms;                                    /**< The list of Rooms in the Hotel */
    private final List<Booking> _bookings;                              /**< The list of Bookings in the Hotel */


    /**
     * Parameterised Hotel Constructor method
     *
     * @param rooms The Numbered Rooms to create the Hotel Class
     */
    public Hotel(int[] rooms) {
        _rooms = new ArrayList<>();
        _bookings = Collections.synchronizedList(new ArrayList<>()); // Suggestion from ArrayList Oracle API

        for (int roomNum : rooms) {
            _rooms.add(new Room(roomNum));
        }
    }


    /**
     * Method for checking to see if a given Room is booked on the given Day(s)
     *
     * @param days      The Day(s) to check the Room occupancy during
     * @param roomNum   The number of the Room to check
     * @return  True if the Room is booked on the given Day(s), False if it is not
     */
    public boolean roomBooked(int[] days, int roomNum) {
        for (var booking : this._bookings) {                    // Search the Bookings
            for (var room : booking.get_rooms()) {              // Search the Rooms in that Booking
                if (room.get_roomNumber() == roomNum) {         // If the room is the requested room
                    for (var _day : booking.get_bookedDays()) { // Search the days that the room is booked
                        for (var day_ : days) {                 // Search the days requested to book
                            if (_day == day_) {                 // If any day is the same day
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }


    /**
     * Attempt to book a Room with the given parameters
     *
     * @param bookingRef    The Reference to assign the created Booking
     * @param days          The Day(s) to book the Room for
     * @param roomNum       The Room number for this Booking
     * @return  True if the Booking was successful, False if it was not
     */
    public boolean bookRoom(String bookingRef, int[] days, int roomNum) {
        Booking newBooking = new Booking(
                bookingRef,
                days,
                roomNum);
        try {
            _bookings.add(newBooking);
        } catch (Exception e) {
            System.err.println("Problem booking a room: " + e);
            return false;
        }

        System.out.println("Booking [#" + bookingRef + "] booked successfully!");
        return true;
    }


    /**
     * Update an existing Booking from a given Reference with new details
     *
     * @param bookingRef    The Reference to update with a new Booking
     * @param days          The Day(s) to update the Booking with
     * @param roomNum       The Room number to update the Booking to
     * @return  True if the Booking update process was successful, False if it was not
     * @throws NoSuchBookingException   If the given Booking Reference does not exist, throw an Exception
     */
    public boolean updateBooking(String bookingRef, int[] days, int roomNum) throws NoSuchBookingException {
        boolean bookingExists = false;
        int index = -1;

        for (var bk : this._bookings) {
            if (bk.get_reference().equals(bookingRef)) {
                bookingExists = true;
                index = this._bookings.indexOf(bk);
                break;
            }
            bookingExists = false;
        }

        if (!bookingExists) {
            throw new NoSuchBookingException(bookingRef);
        } else {
            Booking updtBooking = new Booking(
                    bookingRef,
                    days,
                    roomNum);
            this._bookings.set(index, updtBooking);
            System.out.println("Booking [#" + bookingRef + "] updated successfully!");
            return true;
        }
    }


    /**
     * Cancel the Booking with the given Reference
     *
     * @param bookingRef    The Reference of the Booking to cancel
     * @throws NoSuchBookingException   If the given Booking Reference does not exist, throw an Exception
     */
    public void cancelBooking(String bookingRef) throws NoSuchBookingException {
        if (!this._bookings.removeIf(booking -> booking.get_reference().equals(bookingRef))) {
            throw new NoSuchBookingException(bookingRef);
        } else {
            System.out.println("Booking [#" + bookingRef + "] cancelled successfully!");
        }
    }


    ///////////////////
    // Bonus Methods //
    ///////////////////


    /**
     * Method for checking to see if any of the given Rooms are booked on the given Day(s)
     *
     * @param days      The Day(s) to check the Room occupancy during
     * @param roomNums  The numbers of the Rooms to check
     * @return  True if any of the given Rooms are booked on any of the given Days, False if all of them are free
     */
    public boolean _roomsBooked(int[] days, int[] roomNums) {

        throw new UnsupportedOperationException();
    }


    /**
     * Alternative version of the original `bookRoom` method.  Creates a Booking with Multiple Rooms
     *
     * @param bookingRef    The Reference to assign to a new Booking
     * @param days          The Day(s) to assign to the new Booking
     * @param roomNums      The Room numbers to assign to the new Booking
     * @return  True if the Booking process was successful, False if it was not
     */
    public boolean _bookRooms(String bookingRef, int[] days, int[] roomNums) {

        throw new UnsupportedOperationException();
    }


    /**
     * Alternative version of the original `updateBooking` method.  Updates a Booking to contain multiple Rooms
     *
     * @param bookingRef    The Reference to update with a new Booking
     * @param days          The Day(s) to update the Booking with
     * @param roomNums      The Room numbers to update the Booking to
     * @return  True if the Booking update process was successful, False if it was not
     * @throws NoSuchBookingException   If the given Booking Reference does not exist, throw an Exception
     */
    public boolean _updateBooking(String bookingRef, int[] days, int[] roomNums) throws NoSuchBookingException {

        throw new UnsupportedOperationException();
    }


    /**
     * Alternative version of the original `cancelBooking` method.  Cancels a booking with multiple Rooms
     *
     * @param bookingRef    The Reference of the Booking to cancel
     * @throws NoSuchBookingException   If the given Booking Reference does not exist, throw an Exception
     */
    public void _cancelBooking(String bookingRef) throws NoSuchBookingException {

        throw new UnsupportedOperationException();
    }


    ////////////////
    // My Methods //
    ////////////////


    /**
     * Getter method for the count of Rooms in this Hotel
     *
     * @return  The count of Rooms contained in this Hotel
     */
    public int get_roomCount() {
        return this._rooms.size();
    }


    /**
     * Getter method for the Bookings registered with this Hotel
     *
     * @return  The list of Bookings
     */
    public List<Booking> get_bookings() {
        return _bookings;
    }


    /**
     * The overridden `toString()` method for printing this Hotel
     *
     * @return  The structure of this Hotel in String format, ready for printing
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Room Count: ").append(this._rooms.size())
                .append("\nRooms: ");

        for (var rm : this._rooms) {
            sb.append("\n\t")
                    .append(rm.toString());
        }

        if (this._bookings.size() > 0) {
            sb.append("\nBookings: ").append(this._bookings.size());

            for (var bk : this._bookings) {
                sb.append("\n\t")
                        .append(bk.toString());
            }
        } else {
            sb.append("\nThere are currently no Bookings.");
        }

        sb.append("\n");

        return sb.toString();
    }
}
