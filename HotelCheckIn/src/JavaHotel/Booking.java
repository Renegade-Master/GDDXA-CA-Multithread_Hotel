/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel.Booking
 */

package JavaHotel;


/**
 * The Booking Class
 * <p>
 * A Booking is a custom datatype that links a Reference number, at
 * least one Room, and at least one Day together
 */
public class Booking {
    private final String _reference;    //!< The Reference number for this Booking
    private final Room[] _rooms;        //!< The Rooms that are assigned to this Booking
    private final int[] _bookedDays;    //!< The Days that this Room is booked for


    /**
     * Default Parameterised Booking Constructor method
     *
     * @param ref     The unique Reference String for the Booking
     * @param days    The count of days to book the Room for
     * @param roomNum The number of the Room to book
     */
    public Booking(String ref, int[] days, int roomNum) {
        this._reference = ref;
        this._bookedDays = days;
        this._rooms = new Room[] { new Room(roomNum) };
    }


    /**
     * Alternative Parameterised Booking Constructor method
     *
     * @param ref      The unique Reference String for the Booking
     * @param days     The count of days to book the Room for
     * @param roomNums The numbers of the Rooms to book
     */
    public Booking(String ref, int[] days, int[] roomNums) {
        this._reference = ref;
        this._bookedDays = days;

        this._rooms = new Room[roomNums.length];
        for (int i = 0; i < _rooms.length; i++) {
            this._rooms[i] = new Room(roomNums[i]);
        }
    }


    /**
     * Getter method for returning the Reference of this Booking
     *
     * @return The Reference String for this Booking
     */
    public String get_reference() {
        return _reference;
    }


    /**
     * Getter method for returning the Room(s) that this Booking applies to
     *
     * @return The Room(s) that this Booking applies to
     */
    public Room[] get_rooms() {
        return _rooms;
    }


    /**
     * Getter method for returning the Days that this Booking applies to
     *
     * @return The Day(s) that this Booking applies to
     */
    public int[] get_bookedDays() {
        return _bookedDays;
    }


    ////////////////
    // My Methods //
    ////////////////


    /**
     * The overridden `toString()` method for printing this Booking
     *
     * @return The structure of this Booking in String format, ready for printing
     */
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
