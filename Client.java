import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

    private InetAddress adr;
    private DatagramSocket sc;
    private DatagramPacket paquete;
    private int puerto;

    public Client(String ip, int puerto) {

        try {

            adr = InetAddress.getByName(ip);

        } catch (UnknownHostException e) {

        }

    }

    public void enviar(byte[] buffer) {

        try {

            sc = new DatagramSocket();

        } catch (SocketException e) {

            sc = null;

        }

        paquete = new DatagramPacket(buffer, buffer.length, adr, puerto);

        try {

            sc.send(paquete);

        } catch (IOException e) {

        }

    }

    public byte[] recibir() {

        byte[] buffer = null;

        try {

            sc.receive(paquete);
            buffer = paquete.getData();

        } catch(IOException e )  {

        } 
        return buffer; 
    }

}
