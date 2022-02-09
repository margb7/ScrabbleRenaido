
public class ScrabbleRenaido {

    private static final int SCRAB_PORT = 7557;
    private static Server srv;
    private static Client cli;
    private static Partida partida; 

    public static void main(String[] args) {

        if(args.length != 0 ) {

            if(args[0].equals("--h") || args[0].equals("help") ) {

                System.out.println("Ayuda: ");
                System.out.println("\n\tSin argumentos: ");
                System.out.println("\t\tmenú interactivo");
                System.out.println("\n\tArgumentos para crear una sala:");
                System.out.println("\t\t-s   numero_de_jugadores    nombre_para_jugar");
                System.out.println("\n\tArgumentos para unirse a una sala:");
                System.out.println("\t\t-c   ip_sala    nombre_para_jugar");
                System.out.println("\n\t\t  { ip_sala: ip separada por puntos (ej:  129.7.65.6 ) }");
                System.out.println("");

            } else if(args[0].equals("-s")) {

                int nJug = Integer.parseInt(args[1]);
                String nombre = args[2];
                srv = new Server("", SCRAB_PORT,nombre,0);  
                partida = srv.crearSala(nJug);
                jugar();

            } else if(args[0].equals("-c")) {

                String ip = args[1];
                String nombre = args[2];

                cli = new Client(ip,SCRAB_PORT,nombre);
                partida = cli.unirseSala(ip);
                jugar();
            }

        } else {

            menu();

        }

    }

    public static void menu() {
        char op;
        String nombreJugador;

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

            int numJugadores;

            System.out.println("Introduce el numero de jugadores: ");
            numJugadores = Entrada.getInt();


            srv = new Server("", SCRAB_PORT, nombreJugador,0);

            partida = srv.crearSala(numJugadores);
            jugar();

        } else {
            
            String ip;
            System.out.println("Ip del servidor de la sala: ");
            ip = Entrada.getString();

            cli = new Client(ip, SCRAB_PORT, nombreJugador);
            partida = cli.unirseSala(ip);
            jugar();

        }

    }

    public static void jugar() {

        partida.jugar();

    }

}
