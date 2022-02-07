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

        srv = new Server("", 7555);
        String str;

        while(true) {
            
            str = srv.recibir();    
            System.out.println(str);
            System.out.println("Envia:");
            srv.enviar(Entrada.getString());
            
        }

    }

    public static void unirseSala() {

        System.out.println("ip de ordenador del otro jugador: ");

        cli = new Client(Entrada.getString(), 7555);
        
        while(true) {

            System.out.println("Envia:");
            cli.enviar(Entrada.getString());
            System.out.println(new String(cli.respuesta()));

        }

    }

}
