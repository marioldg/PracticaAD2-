package org.example;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import org.example.conexion.Conexion;
import org.example.Entidades.Entrenador;
import org.example.Entidades.Torneo;
import org.example.ELIMINAR.controlUsuarios.controlFicheros.EscrituraFicheros;
import org.example.ELIMINAR.controlUsuarios.controlFicheros.LecturaFicheros;

public class Main {
    public Scanner scanner = new Scanner(System.in);
    public LecturaFicheros lecturaFicheros = new LecturaFicheros();
    public static SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    public EscrituraFicheros escrituraFicheros = new EscrituraFicheros();
    //public final  String file = "src/main/Files/Credenciales.txt";
    public boolean acceso;
    private ArrayList<Torneo> torneos = new ArrayList<>();
    private ArrayList<Entrenador> entrenadores = new ArrayList<>();

    private Conexion conexion = new Conexion();


    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main (){
      /* leerTorneos();
        menuInvitado();
       */
        nuevoEntrenador();
    }
     /*
      En el metodo login, metemos credenciales, y nos asignamos rol
     */


    public void login() {
        while (true) {
            System.out.print("Ingrese su nombre de usuario: ");
            String username = scanner.nextLine();

            System.out.print("Ingrese su contraseña: ");
            String password = scanner.nextLine();

            acceso = lecturaFicheros.controlLogIn(username, password);

            if (!acceso) {
                System.out.println("Credenciales incorrectas. \n" +
                        "Si desea salir pulse 0.\n" +
                        "Si desea volver a intentarlo pulse 1. \n" +
                        "Si quieres ser invitado pulse 2.");

                int cod = controlarExceptionInt();

                switch (cod) {
                    case 0:
                        System.out.println("Saliendo del programa...");
                        return;
                    case 2:
                        menuInvitado();
                        break;
                    case 1:
                        System.out.println("Reintentando LogIn...");
                        break;
                    default:
                        System.out.println("Opción no válida. Reintentando LogIn...");
                        break;
                }
            } else {
                menu(lecturaFicheros.getRol(), username);
                break;
            }
        }
    }
     /*
      Menu generico, que nos permite movernos
      entre las diferenctes opciones de los distintos usuarios
     */

    public void menu(String rol, String nombre) {
        switch (rol) {
            case "Admin":
                menuAdmin();
                break;
            case "Entrenador":
                menuEntrenador(new Entrenador()); //Creamos el entrenador vacio a la espera de saber como recorrer los entrenadores
                break;
            case "Invi":
                login();
                break;
            default:
                System.out.println("Opcion no valida");
        }
    }

    /*
      Metodo para desplegar por consola el menu de admin ( no admin torneo)
     */

    public void menuAdmin() {
        System.out.println("Eres el Admin las opciones son esas :" +
                "\n 0-Salir"+
                "\n 1- Nuevo Torneo");
        int opcion = controlarExceptionInt();
        switch (opcion) {
            case 0:
                menuInvitado();
                break;

            case 1:
                nuevoTorneo();
                break;

            default:
                System.out.println("Error en el programa");
                break;
        }
    }

     /*
        Metodo para desplegar por consola el menu de entrenador
     */

    public void menuEntrenador(Entrenador e) {

        while (true) {
            System.out.println("Eres el Entrenador las opciones son esas :" +
                    "\n 0- Volver al login" +
                    "\n 1- Ver Carnet" +
                    "\n 2- Exportar Carnet");


            int opcion = controlarExceptionInt();


            if (opcion == 1) {
                System.out.println(e.toString());


            } else if (opcion == 2) {
                e.exportarXML("src/main/Files");


            } else if (opcion == 0) {
                menuInvitado();
                return;
            } else {
                System.out.println("Opcion no valida.Saliendo del programa...");
                break;
            }

        }
    }

    /*
        Metodo para desplegar por consola el menu de invitado
     */
    public void menuInvitado() {
        System.out.println("Eres el Invitado las opciones son esas :" +
                "\n 0-Salir"+
                "\n 1-Nuevo entrenador " +
                "\n 2-Logear" );
        int opcion = controlarExceptionInt();
        switch (opcion) {
            case 0:
                System.out.println("Saliendo del programa...");
                return;
            case 1:
                nuevoEntrenador();
                break;
            case 2:
                login();
                break;
            default:
                System.out.println("Saliendo");
                break;
        }
    }

    /*
      Metodo para crear nuevo entrenador
      Controlamos que el Entrenador no Exista
      Si no existe Comprobar que el code de nacioaldiad Existe
      Si ambos existen creamos, y metemos en credenciales.txt
     */
    public void nuevoEntrenador() {
        System.out.println("AAAAAAAAAAAAAA");

        System.out.println("Ingrese el nombre del entrenador");
        String nombre = scanner.nextLine();
        mostrarPais();
        System.out.println("Ingrese la nacionalidad del entrenador");
        String nacionalidad = scanner.nextLine();
        System.out.println("Introduce el id del torneo al que se quiera introducir");

        for (Torneo torneos : torneos) {
            System.out.println(torneos.getNombre() + "  " + torneos.getId());
        }
        int idTorneo = controlarExceptionInt();
        boolean torneoCorrecto = false;
        Torneo torneoElegido = null;
        for (Torneo torneos : torneos) {
            if (idTorneo == (torneos.getId())) {
                torneoCorrecto = true;
                torneoElegido = torneos;
            }
        }

            /*
            Se tienen que cumplior las dos, que el code de nacionalidad este dentro del xml de nacionalidad
            Y que el id del torneo exista
             */
            /*
            if (lecturaFicheros.leerPaises(nacionalidad) && torneoCorrecto) {

                Entrenador nuevoEntrenador = new Entrenador(generarIdEntrenador(), nombre, nacionalidad);



                nuevoEntrenador.getTorneosEntrenadores().add(torneoElegido);          //me añade al array de los torneos que participa el torneo que escoge el nuevo entrenador
                entrenadores.add(nuevoEntrenador);                                   //añade el nuevo entrenador al array de entrenadores del main
                torneos.get(idTorneo).getEntrenadores().add(nuevoEntrenador);       //añade el nuevo entrenador al array de de entrenadores del torneo escogido
                crearCredenciales(nombre, generarIdEntrenador(), "Entrenador");
                menuEntrenador(nuevoEntrenador);
            } else {
                System.out.println("Error:el code del pais, o el torneo, no esta\nNo se ha creado");
                menuInvitado();
            }
        }
        */

        try {
            System.out.println("SELECT nombre FROM appdatabase.paises WHERE id='"
                    + nacionalidad +"'" );
            ResultSet rs =conexion.getStatement().executeQuery(
                    "SELECT nombre FROM appdatabase.pais WHERE id='"
                            + nacionalidad +"'" );
            if(rs == null){
                System.out.println("Pais no reconocido");
            }else{
                System.out.println("Pais ok");
                rs =conexion.getStatement().executeQuery(
                        "SELECT nombre FROM appdatabase.torneo WHERE id='"
                                + idTorneo +"'" );
                if (!(rs==null)){
                    System.out.println("El torneo existe, es correcto.");

                    System.out.println("INSERT INTO appdatabase.entrenador " +
                            "(nombre, nacionalidad) VALUES ('"+ nombre +"','"+nacionalidad+"');");
                    conexion.getStatement().executeUpdate("INSERT INTO appdatabase.entrenador " +
                            "(nombre, nacionalidad) VALUES ('"+ nombre +"','"+nacionalidad+"');");
                }
                System.out.println("aaaaaa");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Comprobamos que no existen credenciales dentro de credencuales.txt


    public void crearCredenciales(String nombre, int id, String tipo) {
        if (lecturaFicheros.comprobarNuevo(nombre)) {
            System.out.println("Usuario repetido");
            nuevoEntrenador();

        } else {
            System.out.println("Inserte contraseña");
            String pass = scanner.nextLine();
            escrituraFicheros.insertarCredenciales(nombre, pass, tipo, id);
        }
    }


    // Metodo diseñado para crear nuevo torneo


    public void nuevoTorneo() {
        String pass = null;
        System.out.println("Introduce el nombre del Admin del Torneo");
        String nombre = scanner.nextLine();

        //comprobamos que el usuario no existe
        if (!lecturaFicheros.comprobarNuevo(nombre)) {
            System.out.println("Introduce una contraseña");
            pass = scanner.nextLine();

        } else {
            System.out.println("El id metido, ya existe.Intentandolo de nuevo..");
            nuevoTorneo();
        }

        System.out.println("Ingrese el nombre del torneo");
        String nombreTorneo = scanner.nextLine();
        mostrarPais();
        System.out.println("Ingrese la región del torneo (una letra)");
        char region = scanner.nextLine().charAt(0); // Leer el primer carácter como región

            /*
            Este metodo al que llamamos, nos da true si el torneo existe, y false si no
            Tenemos que negar el metodo para seguir dentro
             */
        boolean existeTorneo = false;
        for (Torneo t : torneos) {
            if (t.getNombre().equals(nombreTorneo) && region==t.getCodRegion()) {
                System.out.println("El torneo ya existe");
                existeTorneo = true;
            }

        }
        if (!existeTorneo) {
            Torneo torneo1 = new Torneo(torneos.size(), nombreTorneo, region, nombre, pass);
            torneos.add(torneo1);
            System.out.println("El torneo ha sido creado correctamente.");
            escrituraFicheros.insertarCredenciales(nombre, pass, "Admin Torneo", torneo1.getId());

            //Almacena los datos de torneo en el .dat

            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("src/main/Files/Torneos.dat", true))) {
                dos.writeInt(torneo1.getId());
                dos.writeUTF(torneo1.getNombre());
                dos.writeChar(torneo1.getCodRegion());
                dos.writeUTF(nombre);
                dos.writeInt(torneo1.getPuntos());

            } catch (IOException e) {
                System.out.println("Error al guardar el torneo en archivo.");
                e.printStackTrace();
            }


        }
        menuAdmin();


    }

    //Esta funcion te lee el .dat
    public void leerTorneoDB(){
        try {
            ResultSet rs =conexion.getStatement().executeQuery("SELECT * FROM appdatabase.torneo;");
            while (rs.next()) {
                System.out.println("Torneo: " + rs.getInt("id") +
                        " El nombre es " +rs.getString("nombre")+
                        " El code de region es " + rs.getString("codRegion") +
                        " Puntos " + rs.getString("puntosVictoria"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void leerTorneos() {
        File f = new File("src/main/Files/Torneos.dat");
        if (f.exists() && f.length() > 0) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
                while (dis.available() > 0) { // Mientras haya datos disponibles en el archivo
                    int id = dis.readInt();                     // Lee el ID del torneo
                    String nombre = dis.readUTF();              // Lee el nombre del torneo
                    char region = dis.readChar();               // Lee la región del torneo
                    String adminNombre = dis.readUTF();         // Lee el nombre del administrador
                    int puntos = dis.readInt();           // Lee la contraseña del administrador

                    // Crear un nuevo objeto Torneo con los datos leídos
                    Torneo torneo = new Torneo(id, nombre, region, adminNombre, puntos);
                    torneos.add(torneo); // Añadir el torneo a la lista
                }

            } catch (IOException e) {
                System.out.println("Error al leer el archivo de torneos.");
                e.printStackTrace();
            }
        }

    }

    /*Con este metodo controlamos que cuando pides un int por pantalla
     no rompa el programa por no ser int*/

    public static int controlarExceptionInt() {
        Scanner scanner = new Scanner(System.in);
        int numero = 0;
        boolean valido = false;

        // Repetir hasta que se ingrese un valor válido
        while (!valido) {
            System.out.print("Por favor, ingresa un número entero: ");
            try {
                // Intentamos parsear el valor ingresado como un entero
                numero = Integer.parseInt(scanner.nextLine());
                valido = true;  // Si no hay error, la entrada es válida
            } catch (NumberFormatException e) {
                System.out.println("¡Error! Debes ingresar un número entero.");
            }
        }
        return numero;
    }

    //Este metodo lee el txt y suma 1 al id de crear nuevoEntrenador

    public static int generarIdEntrenador(){
        int contadorId=0;
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/Files/Credenciales.txt"))) {
            String linea;
            while((linea=br.readLine())!=null){
                String[] parts = linea.split("  ");
                if(parts[2].equals("Entrenador")){
                    contadorId++;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
        }
        return contadorId;
    }
    /**
     * metodo para mostrar todos los paises de la bd
     */

    public void mostrarPais(){
        try {
            ResultSet paisesQuery = conexion.getStatement().executeQuery("SELECT * FROM appdatabase.pais");
            while (paisesQuery.next()) {
                System.out.println("Nombre: " + paisesQuery.getString("nombre") +
                        " El id es " +paisesQuery.getString("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


