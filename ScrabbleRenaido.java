public class ScrabbleRenaido {

    private static Server srv;
    private static Client cli;

    public static void main(String[] args ) {

        char op;
        
        System.out.println("####");
        System.out.println("\tScrabble");
        System.out.println("####");

        do {

            System.out.println("A) Crear sala\nB) Unirse a sala");
            op = Character.toLowerCase(Entrada.getChar());

        }while(op != 'a' && op != 'b');

        if(op == 'a' ) {
      
            crearSala();

        } else {

            unirseSala();
            
        }

    }

    public static void crearSala() {

        int num;

        System.out.println("ip de ordenador del otro jugador: ");

        srv = new Server(Entrada.getString(), 7555);

        while(true) {

            srv.enviar(Entrada.getString().getBytes());
            System.out.println(new String(srv.escuchar()));

        }

    }

    public static void unirseSala() {

        System.out.println("ip de ordenador del otro jugador: ");

        cli = new Client(Entrada.getString(), 7555);
        
        while(true) {

            System.out.println(new String(cli.recibir()));
            cli.enviar(Entrada.getString().getBytes());

        }

    }

}
