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

    public static String ROT13(String str ) {
        return ROT13(str, 13);
    }

    public static String decodeROT13(String str ) {
        return ROT13(str, - 13);
    }

    private static String ROT13(String str, int rot ) {

        StringBuilder strb = new StringBuilder("");
        boolean isUpper;
        int pos;

        for (int x = 0; x < str.length(); x++) {

            char c = str.charAt(x);

            if (!Character.isLetter(c)) {

                strb.append(c);
                continue;

            }

            isUpper = Character.isUpperCase(c);

            pos = (((int) c - (isUpper ? 65 : 97)) + rot) % 26;

            if (pos < 0){
                pos += 26;
            }

            strb.append((char) ((isUpper ? 65 : 97) + pos) );
        }

        return strb.toString();

    }
}
