import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server{
    
    private int puerto;
    private String ip;

    private InetAddress adr = null;
    private DatagramSocket sc;
    private DatagramPacket paquete;
    private byte[] buffer;

    Server(String ip,int puerto){
    
        this.puerto = puerto;
        this.ip = ip;

        try{

            adr = InetAddress.getByName(ip);

            try{

                sc = new DatagramSocket(puerto);

            }catch(SocketException e){

            }

        }catch(UnknownHostException e){
            
        }
    }

    public String getIp() {
        
        return ip;
    }

    public int getPuerto() {

        return puerto;
    }

    public byte[] escuchar(){

        try{

            sc.receive(paquete);
            buffer = paquete.getData();

        }catch(IOException e){

            return null;  // codigo ilegalismo 
            
        }

        return this.buffer;
    }

    public void enviar(byte[] buffer ) {

        try {

            sc.send(paquete);

        } catch (IOException e) {

        }

    }

    public String toString() {

        return "IP:" + ip + ":" + puerto; 
    }

}
