/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel.Room
 */

package JavaHotel;


public class Room {
    private final int _roomNumber;


    public Room(int roomNum) {
        this._roomNumber = roomNum;
    }


    public int get_roomNumber() {
        return _roomNumber;
    }


    @Override
    public String toString() {

        return "Room Number " + this._roomNumber;
    }
}
