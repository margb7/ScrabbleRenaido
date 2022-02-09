import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements UDPinterface{

    private String nombreJugador;
    private InetAddress adr; // server
    private DatagramSocket sc;
    private DatagramPacket paquete;
    private int puerto;
    private byte[] buffer = new byte[4096];
    private Jugador jug;

    public Client(String ip, int puerto, String nombre) {

        try {

            adr = InetAddress.getByName(ip);

            try {

                sc = new DatagramSocket();

            } catch (SocketException e) {
                e.printStackTrace();
            }

        } catch (UnknownHostException e) {

        }

        this.nombreJugador = nombre;
        this.puerto = puerto;

    }

    public Partida unirseSala(String ip) {

        int numJugadores;
        String str, linea, nombre;
        Jugador jugador;
        int num;
        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

        
        enviar(nombreJugador);

        String re = recibir().trim();

        numJugadores = Integer.parseInt(re);

        str = recibir();

        for(int i = 0; i < numJugadores; i++ ) {            

            linea = str.substring(0,str.indexOf("|"));              // TODO: TODO: limpiar este cristo
            str = str.substring(str.indexOf("|") + 1, str.length());

            nombre = linea.substring(0, linea.indexOf(" "));
            linea = linea.substring(linea.indexOf(" ") + 1 , linea.length());
            num = Integer.parseInt(linea);

            jugador = new Jugador(nombre, num);
            jugadores.add(jugador);

            if(nombre.trim().equals(nombreJugador) ) {

                jug = jugador;

            }
            
        }
        
        return new Partida(jugadores, jug, this);
    }

    public void enviar(String str) {
        
        System.out.println(str);
        str = Utilidades.ROT13(str);
        buffer = str.getBytes();
        paquete = new DatagramPacket(buffer, buffer.length, adr, puerto);
        
        try {

            sc.send(paquete);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public String recibir() {

        String out;
        buffer = new byte[4096];
        paquete = new DatagramPacket(buffer, buffer.length);

        try {

            sc.receive(paquete);

        } catch (IOException e) {
            
            e.printStackTrace();

        }

        out = new String(paquete.getData());
        out = Utilidades.decodeROT13(out);

        return out;   
        
    }

}
