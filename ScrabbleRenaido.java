import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class ScrabbleRenaido {

    private static Server srv;
    private static Client cli;
    private static String nombreJugador;
    private static Partida partida; 

    public static void main(String[] args) {

        char op;

        System.out.println("####");
        System.out.println("\tScrabble");
        System.out.println("####");
        System.out.println("");
        System.out.println("Introduce un nombre/apodo");

        nombreJugador = Entrada.getString();

        System.out.println("-------------------");

        do {

            System.out.println("A) Crear sala\nB) Unirse a sala");
            op = Character.toLowerCase(Entrada.getChar());

        } while (op != 'a' && op != 'b');

        if (op == 'a') {

            crearSala();

        } else {

            unirseSala();

        }

    }



    public static void crearSala() {

        int numJugadores;
        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();      
        String str;
        int turno = 0;

        System.out.println("Introduce el numero de jugadores: ");
        numJugadores = Entrada.getInt();

        srv = new Server("", 7555);


        try {
            
            jugadores.add(new Jugador(nombreJugador, turno++, InetAddress.getLocalHost()));   // TODO: por ahora el jugador del servidor empieza con la primera palabra

        } catch (UnknownHostException e) {

            e.printStackTrace();

        }       
        

        // Esperar por los jugadores       
                // - solo recibe el nombre y guarda 
        do {

            str = srv.recibir(2040);            // TODO: necesario tam buffer?

            if(!Utilidades.ipRegistrada(jugadores, srv.getAddress()) ) {        // para que no se pueda registrar dos veces en la misma partida

                jugadores.add(new Jugador(str, turno++, srv.getAddress()));

            }

        }while(jugadores.size() != numJugadores);


        // Enviar info de la partida a los jugadores 
        System.out.println("Lista jugadores: ");

        for(Jugador jug : jugadores) {

            System.out.println(jug);

        }

        enviarInfo(jugadores);

        /*while(true) {  codigo basura 
            
            str = srv.recibir();    
            System.out.println(str);
            System.out.println("Envia:");
            srv.enviar(Entrada.getString());
            
        }*/

        

        // Jugar ->

    }

    public static void enviarInfo(ArrayList<Jugador> listaJug ) {

        StringBuilder strb = new StringBuilder("");

        for(int i = 0; i < listaJug.size(); i++ ) {
            
            if(!listaJug.get(i).getNombre().equals(nombreJugador) ) {

                srv.enviar(listaJug.get(i).getAddr(), "" + listaJug.size());

            }
            

        }

        for(int i = 0; i < listaJug.size(); i++ ) { 

            strb.append(listaJug.get(i));
            strb.append("|");
            
        }

        for(int i = 0; i < listaJug.size(); i++ ) {             // enviar a los jugadores la informacion de la partida

            if(!listaJug.get(i).getNombre().equals(nombreJugador) ) {

                srv.enviar(listaJug.get(i).getAddr(), strb.toString());

            }

        }

    }


    
    public static void unirseSala() {

        int numJugadores;
        String str, linea, nombre;
        int num;
        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

        System.out.println("Ip del servidor de la sala: ");

        cli = new Client(Entrada.getString(), 7555);
        cli.enviar(nombreJugador);

        numJugadores = Integer.parseInt(cli.respuesta());

        str = cli.respuesta();

        for(int i = 0; i < numJugadores; i++ ) {            // no quiero volver a saber de parsear strings

            linea = str.substring(0,str.indexOf("|"));
            str = str.substring(str.indexOf("|") + 1, str.length());

            nombre = linea.substring(0, linea.indexOf(" "));

            linea = linea.substring(linea.indexOf(" ") + 1 , linea.length());

            num = Integer.parseInt(linea);

            jugadores.add(new Jugador(nombre, num));
            
        }                 


        System.out.println("Lista de jugadores:");

        for(Jugador jug : jugadores) {

            System.out.println(jug);

        }


        // Jugar ->
    }

}
