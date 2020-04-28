/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel.Hotel
 */

package JavaHotel;


import java.util.ArrayList;
import java.util.List;


public class Hotel {
    private final List<Room> _rooms;
    private final List<Booking> _bookings;

    /*  ToDo: write a class which stores information about the relationship
         between BOOKING REFERENCES (Strings), ROOMS (ints), and which DAYS they
         are booked for (ints)
     */


    public Hotel(int[] rooms) {
        _rooms = new ArrayList<Room>();
        _bookings = new ArrayList<Booking>();

        for (int roomNum : rooms) {
            _rooms.add(new Room(roomNum));
        }
    }


    public boolean roomBooked(int[] days, int roomNum) {
        for (var booking : this._bookings) {
            
        }

        for (var i : days) {
            if (this._rooms.get(roomNum).isBooked()) {
                return true;
            }
        }

        return false;
    }


    public boolean bookRoom(String bookingRef, int[] days, int roomNum) {

        throw new UnsupportedOperationException();
    }


    public boolean updateBooking(String bookingRef, int[] days, int roomNum) throws NoSuchBookingException {

        throw new UnsupportedOperationException();
    }


    public void cancelBooking(String bookingRef) throws NoSuchBookingException {

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


    public void _cancelBooking(String bookingRef) throws NoSuchBookingException {

        throw new UnsupportedOperationException();
    }


    /* My functions */
    public int roomCount() { return this._rooms.size(); }

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
