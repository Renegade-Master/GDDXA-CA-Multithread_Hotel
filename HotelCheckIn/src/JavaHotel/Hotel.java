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


public class Hotel {
    public final Lock lock_hotel = new ReentrantLock();
    public final Condition addNewBooking = lock_hotel.newCondition();
    public final Condition readBookings = lock_hotel.newCondition();
    private final List<Room> _rooms;
    private final List<Booking> _bookings;


    public Hotel(int[] rooms) {
        _rooms = new ArrayList<>();
        _bookings = Collections.synchronizedList(new ArrayList<>()); // Suggestion from ArrayList Oracle API

        for (int roomNum : rooms) {
            _rooms.add(new Room(roomNum));
        }
    }


    public boolean roomBooked(int[] daysToBook_, int roomNumToBook_) {
        for (var booking : this._bookings) {                    // Search the Bookings
            for (var room : booking.get_rooms()) {              // Search the Rooms in that Booking
                if (room.get_roomNumber() == roomNumToBook_) {  // If the room is the requested room
                    for (var _day : booking.get_bookedDays()) { // Search the days that the room is booked
                        for (var day_ : daysToBook_) {          // Search the days requested to book
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


    public boolean bookRoom(String bookingRef_, int[] days_, int roomNum_) {
        Booking newBooking = new Booking(
                bookingRef_,
                days_,
                roomNum_);
        try {
            _bookings.add(newBooking);
        } catch (Exception e) {
            System.err.println("Problem booking a room: " + e);
            return false;
        }

        return true;
    }


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


    public void cancelBooking(String bookingRef) throws NoSuchBookingException {
        if (!this._bookings.removeIf(booking -> booking.get_reference().equals(bookingRef))) {
            throw new NoSuchBookingException(bookingRef);
        } else {
            System.out.println("Booking [#" + bookingRef + "] cancelled successfully!");
        }
    }

    /* Bonus Functions */


    public boolean _roomsBooked(int[] days, int[] roomNums) {

        throw new UnsupportedOperationException();
    }


    public boolean _bookRooms(String bookingRef, int[] days, int[] roomNums) {

        throw new UnsupportedOperationException();
    }


    public boolean _updateBooking(String bookingRef, int[] days, int[] roomNums) throws NoSuchBookingException {

        throw new UnsupportedOperationException();
    }


    public void _cancelBooking(String bookingRef) throws NoSuchBookingException {

        throw new UnsupportedOperationException();
    }


    /* My functions */
    public int roomCount() {
        return this._rooms.size();
    }


    public List<Booking> get_bookings() {
        return _bookings;
    }


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
