import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    private int puerto;
    private String ip;

    private int clientPort = 0;
    private InetAddress adr = null;
    private DatagramSocket sc;
    private DatagramPacket paquete;
    private byte[] buffer = new byte[2024];

    public Server(String ip, int puerto) {

        this.puerto = puerto;
        this.ip = ip;

        try {

            sc = new DatagramSocket(puerto);

        } catch (SocketException e) {

        }

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

    public String recibir(int bSize) {          // TODO:importa ???
        buffer = new byte[bSize];
        return recibir();
    }

    public String recibir() {

        
        paquete = new DatagramPacket(buffer, buffer.length);

        try {

            sc.receive(paquete);

        } catch (IOException e) {

            e.printStackTrace();

        }

        adr = paquete.getAddress(); // TODO: cambiar
        clientPort = paquete.getPort();

        buffer = new byte[2024];

        return new String(paquete.getData());
    }

    public void enviar(String str) {

        buffer = str.getBytes();
        paquete = new DatagramPacket(buffer, buffer.length, adr, clientPort);

        try {

            sc.send(paquete);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void enviar(InetAddress clientAddr, String str ) {

        buffer = str.getBytes();
        paquete = new DatagramPacket(buffer, buffer.length, clientAddr, clientPort);

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
