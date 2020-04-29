/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel.Booking
 */

package JavaHotel;


public class Booking {
    private final String _reference;
    private final int[] _rooms;
    private final int[] _bookedDays;


    public Booking(String ref, int[] days, int roomNum) {
        this._reference = ref;
        this._bookedDays = days;
        this._rooms = new int[]{roomNum};
    }


    public Booking(String ref, int[] days, int[] roomNums) {
        this._reference = ref;
        this._bookedDays = days;
        this._rooms = roomNums;
    }


    public String get_reference() {
        return _reference;
    }


    public int[] get_rooms() {
        return _rooms;
    }


    public int[] get_bookedDays() {
        return _bookedDays;
    }
}
