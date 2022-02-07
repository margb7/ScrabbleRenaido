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
    private byte[] buffer = new byte[1024];

    public Client(String ip, int puerto) {

        try {

            byte[] tmp = new byte[4];

            tmp[0] = Byte.parseByte(ip.substring(0,ip.indexOf(".") - 1 ));      // TODO: cambiar este cacho de pedazo de ladrillo
            ip = ip.substring(ip.indexOf(",") + 1, ip.length());
            tmp[1] = Byte.parseByte(ip.substring(0,ip.indexOf(".") - 1 ));
            ip = ip.substring(ip.indexOf(",") + 1, ip.length());
            tmp[2] = Byte.parseByte(ip.substring(0,ip.indexOf(".") - 1 ));
            ip = ip.substring(ip.indexOf(",") + 1, ip.length());
            tmp[3] = Byte.parseByte(ip.substring(0,ip.indexOf(".") - 1 ));
            ip = ip.substring(ip.indexOf(",") + 1, ip.length()); 

            adr = InetAddress.getByAddress(tmp);

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
 
        buffer = str.getBytes();
        paquete = new DatagramPacket(buffer, buffer.length, adr, puerto);
        
        try {

            sc.send(paquete);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public String respuesta() {
        
        try {

            sc.receive(paquete);

        } catch (IOException e) {
            
            e.printStackTrace();

        }
 
        return new String(buffer, 0, paquete.getLength());   
        
    }

}
