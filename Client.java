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
    private byte[] buffer = new byte[4096];

    public Client(String ip, int puerto) {

        try {

            adr = InetAddress.getByName(ip);

            try {

                sc = new DatagramSocket();

            } catch (SocketException e) {
                e.printStackTrace();
            }

        } catch (UnknownHostException e) {

        }

        this.puerto = puerto;

    }

    public void enviar(String str) {
        
        str = Utilidades.ROT13(str);
        buffer = str.getBytes();
        paquete = new DatagramPacket(buffer, buffer.length, adr, puerto);
        
        try {

            sc.send(paquete);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public String respuesta() {

        String out;
        buffer = new byte[4096];
        paquete = new DatagramPacket(buffer, buffer.length);

        try {

            sc.receive(paquete);

        } catch (IOException e) {
            
            e.printStackTrace();

        }
        out = new String(buffer, 0, paquete.getLength());
        out = Utilidades.decodeROT13(out);

        return out;   
        
    }

}
