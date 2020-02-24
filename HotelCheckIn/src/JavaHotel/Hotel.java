/**
 * Ciaran Bent [K00221230]
 * Concurrent & Distributed Systems - Assignment 01
 * Java JavaHotel.Hotel
 */

package JavaHotel;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Booking> _bookings;
    private List<Room> _rooms;

    public Hotel(int[] rooms) {
        _bookings = new ArrayList<Booking>();
        _rooms = new ArrayList<Room>();

        for (int roomNum : rooms) {
            _rooms.add(new Room(roomNum));
        }
    }

    public boolean roomBooked(int[] days, int roomNum) {

        throw new UnsupportedOperationException();
    }

    public boolean bookRoom(String bookingRef, int[] days, int roomNum) {
        Booking newBooking = new Booking(
                bookingRef,
                days,
                roomNum);

        for (Booking existingBooking : _bookings) {
            for (int day : newBooking.get_bookedDays()) {
                for (int _day : days) {
                    if (_day ==) {

                    }
                }
            }
        }
    }

    public boolean updateBooking(String bookingRef, int[] days, int roomNum) {

        throw new UnsupportedOperationException();
    }

    public void cancelBooking(String bookingRef) {

        throw new UnsupportedOperationException();
    }

    /* Bonus Functions */

    public boolean _roomsBooked(int[] days, int[] roomNums) {

        throw new UnsupportedOperationException();
    }

    public boolean _bookRooms(String bookingRef, int[] days, int[] roomNums) {

        throw new UnsupportedOperationException();
    }

    public boolean _updateBooking(String bookingRef, int[] days, int[] roomNums) {

        throw new UnsupportedOperationException();
    }

    /* My functions */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Room Count: ").append(this._rooms.size())
                .append("\nRooms: ");

        for (Room rm : this._rooms) {
            sb.append("\n\t")
                .append(rm.toString());
        }

        return sb.toString();
    }
}
