import java.util.Scanner;

public class Entrada {
    
    private static Scanner in = new Scanner(System.in);

    public static char getChar() {

        return in.nextLine().charAt(0);

    }
    public static String getString() {
    
        return in.nextLine();
    }
    public static int getInt() {

        do {

            if(in.hasNext() && !in.hasNextInt() ) {

                System.out.println("Introduce un int");

            }

        }while(!in.hasNextInt());

        return in.nextInt();
    }
}
