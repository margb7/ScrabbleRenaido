import java.net.InetAddress;

public class Jugador {
    
    private int num;
    private int puntos;
    private String nombre;
    private InetAddress addr = null;
    
    public Jugador(String nombre,int num, InetAddress addr) {

        this.nombre = nombre;
        this.num = num;
        this.addr = addr;
        puntos = 0;

    }
    public Jugador(String nombre, int num ) {       // Solo lo usa el cliente 

        this.nombre = nombre;
        this.num = num;
        puntos = 0;

    }

    public InetAddress getAddr() {
        return addr;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public int getNum() {
        return this.num;
    }

    public String toString() {

        return nombre + " " + num;

    }
}
