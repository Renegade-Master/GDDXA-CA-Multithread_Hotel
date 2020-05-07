/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel.Booking
 */

package JavaHotel;


public class Booking {
    private final String _reference;
    private final Room[] _rooms;
    private final int[] _bookedDays;


    public Booking(String ref, int[] days, int roomNum) {
        this._reference = ref;
        this._bookedDays = days;
        this._rooms = new Room[] { new Room(roomNum) };
    }


    public Booking(String ref, int[] days, int[] roomNums) {
        this._reference = ref;
        this._bookedDays = days;

        this._rooms = new Room[roomNums.length];
        for (int i = 0; i < _rooms.length; i++) {
            this._rooms[i] = new Room(roomNums[i]);
        }
    }


    public String get_reference() {
        return _reference;
    }


    public Room[] get_rooms() {
        return _rooms;
    }


    public int[] get_bookedDays() {
        return _bookedDays;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Reference:\n\t\t#").append(this._reference);

        sb.append("\n\tRooms Booked: ").append(this._rooms.length).append("\n\t");

        for (var rm : this._rooms) {
            sb.append("\t#")
                    .append(rm.get_roomNumber());
        }

        sb.append("\n\tDates Booked: ").append(this._bookedDays.length).append("\n\t");

        for (var dt : this._bookedDays) {
            sb.append("\t")
                    .append(dt);
        }

        sb.append("\n");

        return sb.toString();
    }
}
