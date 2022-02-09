import java.util.ArrayList;

public class Partida {

    private final int PUNTUACION_GANAR = 50;
    private int turno;     
    private ArrayList<Jugador> jugadores;
    private Jugador usuario;
    private UDPinterface conex;
    private String ultimaPalabra;

    public Partida(ArrayList<Jugador> jugadores,Jugador usuario, UDPinterface conex) {

        this.jugadores = jugadores;
        this.usuario = usuario;
        this.conex = conex;
        turno = 0;
    }

    public void jugar() {

        System.out.println("WIP");

    }

    private void sigTurno() {

        turno++;

        if(turno == jugadores.size() ) {

            turno = 0;

        }

    }

    private void enviarTurno() {

        System.out.println(conex);
        System.out.println(conex.getClass());

        if(turno == usuario.getNum() ) {

            if(conex.getClass().toString().equals("Class Client")) {

                conex.enviar(ultimaPalabra);

            }

        }

        if(conex instanceof Server ) {

            conex.enviar(ultimaPalabra);

        } else {

            ultimaPalabra = conex.recibir();

        }

    }

    private int calcularPuntuacionScrabble(String palabra ) {
        // Scrabble.calcular(str);
        return 4;

    }

    private boolean partidaGanada() {       // metodo extra por si se quieren añadir mas formas para ganar
        return jugadores.get(turno).getPuntos() >= PUNTUACION_GANAR;
    }

}