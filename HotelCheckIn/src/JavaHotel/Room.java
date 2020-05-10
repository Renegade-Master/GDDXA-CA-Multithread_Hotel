/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel.Room
 */

package JavaHotel;


/**
 * The Room Class
 * <p>
 * The Room Class contains a simple integer value representing the Room number
 */
public class Room {
    private final int _roomNumber; /**< The number for this Room */


    /**
     * Parameterised Room Constructor Method
     *
     * @param roomNum The number to assign this Room
     */
    public Room(int roomNum) {
        this._roomNumber = roomNum;
    }


    /**
     * Getter method for returning the Room number of this Room
     *
     * @return The integer value of the Room number of this Room
     */
    public int get_roomNumber() {
        return _roomNumber;
    }


    /**
     * The overridden `toString()` method for printing this Room
     *
     * @return The structure of this Room in String format, ready for printing
     */
    @Override
    public String toString() {

        return "Room Number " + this._roomNumber;
    }
}
