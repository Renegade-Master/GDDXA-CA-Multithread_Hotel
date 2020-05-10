/*
  Ciaran Bent [K00221230]
  Concurrent & Distributed Systems - Assignment 01
  Java JavaHotel main
 */

import JavaHotel.Hotel;

import java.util.Random;


/**
 * The Main Class
 * <p>
 * The Main Class is for running the Hotel simulation
 */
public class Main {
    static final int MAX_RUNTIME = 15000;   //!< The maximum runtime of a Thread in milliseconds
    static final int MAX_ROOMS = 30;        //!< The maximum number of Rooms that a Hotel can have
    static final int MIN_ROOMS = 10;        //!< The minimum number of Rooms that a Hotel can have
    static final int USER_COUNT = 1000;     //!< The count of Users that will interact with the Hotel


    /**
     * The main execution point to run the program from.  Callable from command line
     *
     * @param args Command line arguments to be passed to the program.  These are used to build the Rooms of the Hotel
     */
    public static void main(String[] args) {
        Hotel javaHotel;
        int[] rooms;
        User[] users;

        // Set up the Hotel
        if (args.length > 0) {
            System.out.println("Args provided\nUsing Provided Hotel bookings...\n");

            rooms = new int[args.length];

            int i = 0;
            for (var t : args) {
                rooms[i++] = Integer.parseInt(t);
            }
        } else {
            System.out.println("No args provided\nRandomising Hotel bookings...\n");

            Random rand = new Random();
            int roomCount = rand.nextInt(MAX_ROOMS - MIN_ROOMS) + MIN_ROOMS;
            rooms = new int[roomCount];

            int i = 0;
            for (var ignored : rooms) {
                rooms[i] = i++;
            }
        }

        javaHotel = new Hotel(rooms);

        // Set up the users
        users = new User[USER_COUNT];

        for (int i = 0; i < users.length; i++) {
            users[i] = new User(javaHotel, i);
        }

        //System.out.println("\nThe Java Hotel Before:\n" + javaHotel.toString());

        try {
            // Run the simulation
            for (var user : users) {
                //System.out.println("Starting user [" + user.getName() + "]");
                user.start();
            }

            // Complete the simulation
            for (var user : users) {
                user.join(MAX_RUNTIME);
                //System.out.println("User [" + user.getName() + "] stopped");
            }
        } catch (InterruptedException ie) {
            System.err.println("Users Booking a Room has encountered an error @Main.main: \n\t" + ie);
        }

        System.out.println("\nALL MODIFICATIONS HAVE BEEN MADE");

        System.out.println("\nThe Java Hotel After:\n" + javaHotel.toString());
    }
}
