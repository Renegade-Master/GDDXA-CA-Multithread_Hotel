/**
 * Ciaran Bent [K00221230]
 * Concurrent & Distributed Systems - Assignment 01
 * Java JavaHotel.Booking
 */

package JavaHotel;

public class Booking {
    private final String _reference;
    private final int _room;
    private final int[] _bookedDays;

    public Booking(String ref, int[] days, int roomNum) {
        this._reference = ref;
        this._bookedDays = days;
        this._room = roomNum;
    }

    public int[] get_bookedDays() {
        return _bookedDays;
    }
}
