import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server implements UDPinterface{

    private int puerto;
    private String ip;
    private Jugador jug;
    private int puertoJugador;
    private ArrayList<Jugador> jugadores;

    private InetAddress adr = null;
    private DatagramSocket sc;
    private DatagramPacket paquete;
    private byte[] buffer = new byte[2024];

    public Server(String ip, int puerto,String nombreJug, int turno) {

        this.puerto = puerto;
        this.ip = ip;

        try {

            sc = new DatagramSocket(puerto);

        } catch (SocketException e) {

            System.out.println("**Error al crear el servidor");
            System.exit(-1);

        }

        jug = new Jugador(nombreJug, turno);

    }

    public InetAddress getAddress() {
        return adr;
    }

    public String getIp() {

        return ip;
    }

    public int getPuerto() {

        return puerto;
    }

    public Partida crearSala(int numJugadores) {

        String str;
        int turno = 0;

        jugadores = new ArrayList<Jugador>();

        try {
            
            jugadores.add(new Jugador(jug.getNombre(), turno++, InetAddress.getLocalHost(), 7557));   // TODO: por ahora el jugador del servidor empieza con la primera palabra

        } catch (UnknownHostException e) {

            e.printStackTrace();

        }       

        // Esperar por los jugadores       
                // - solo recibe el nombre y guarda 

        System.out.println("Esperando por los jugadores\n");

        do {

            str = recibir(); 

            if(!Utilidades.ipRegistrada(jugadores, getAddress()) ) {        // para que no se pueda registrar dos veces en la misma partida

                jugadores.add(new Jugador(str, turno++, getAddress(), puertoJugador));
                System.out.println("- Nuevo jugador : " + str);

            }

        }while(jugadores.size() != numJugadores);


        // Enviar info de la partida a los jugadores

        enviarInfoPartida();
        
        Utilidades.listarJugadores(jugadores);

        // Partida para jugar
        return new Partida(jugadores, jug, this);
    }

    private void enviarInfoPartida() {

        StringBuilder strb = new StringBuilder("");

        for(int i = 0; i < jugadores.size(); i++ ) {
            
            if(!jugadores.get(i).getNombre().equals(jug.getNombre()) ) {

                enviar(jugadores.get(i), "" + jugadores.size() );

            }
            

        }

        for(int i = 0; i < jugadores.size(); i++ ) { 

            strb.append(jugadores.get(i));
            strb.append("|");
            
        }

        for(int i = 0; i < jugadores.size(); i++ ) {             // enviar a los jugadores la informacion de la partida

            if(!jugadores.get(i).getNombre().equals(jug.getNombre()) ) {

                enviar(jugadores.get(i), strb.toString());

            }

        }

    }

    public String recibir() {

        String out;
        
        paquete = new DatagramPacket(buffer, buffer.length);

        try {

            sc.receive(paquete);

        } catch (IOException e) {

            e.printStackTrace();

        }

        adr = paquete.getAddress(); // TODO: cambiar
        puertoJugador = paquete.getPort();

        buffer = new byte[2024];
        out = new String(paquete.getData());
        System.out.println("out");
        out = Utilidades.decodeROT13(out);
        return out;
    }

    public void enviar(String str) {

        for(Jugador j : jugadores ) {
        
            if(j.getAddr() != jug.getAddr() ) {

                enviar(j,str);

            }

        }

    }

    public void enviar(Jugador j, String str ) {

        str = Utilidades.ROT13(str);
        buffer = str.getBytes();
        paquete = new DatagramPacket(buffer, buffer.length, j.getAddr(), j.getPort());

        try {

            sc.send(paquete);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public String toString() {

        return "IP:" + ip + ":" + puerto;
    }

}
