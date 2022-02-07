import java.net.InetAddress;
import java.util.ArrayList;

public class Utilidades {
    
    public static boolean ipRegistrada(ArrayList<Jugador> arr, InetAddress adr ) {

        boolean contains = false;

        for(int i = 0; i < arr.size() && contains; i++ ) {

            if(arr.get(i).getAddr().getAddress() == adr.getAddress() ) {

                contains = true;

            }

        }

        return contains;
    }
}
