import JavaHotel.Hotel;

public class main {
    static Hotel javaHotel;

    public static void main (String[] args) {
        int[] rooms = {0,1,2,3,4,5,6,7,8,9};

        javaHotel = new Hotel(rooms);

        System.out.println("The Java Hotel:\n" + javaHotel.toString());
        
        for(var t : args) {
            System.out.println(t);
        }
    }
}
