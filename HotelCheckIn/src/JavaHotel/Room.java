package JavaHotel;

public class Room {
    private final int _roomNumber;
    private boolean _booked;

    public Room(int roomNum) {
        this._roomNumber = roomNum;
        this._booked = false;
    }

    public int get_roomNumber() {
        return _roomNumber;
    }

    public boolean isBooked() {
        return this._booked;
    }

    public void book() {
        this._booked = true;
    }

    public void free() {
        this._booked = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Room Number ").append(this._roomNumber)
                .append(" is ");

        sb.append((this._booked) ? "Booked" : "Not Booked");

        return sb.toString();
    }
}
