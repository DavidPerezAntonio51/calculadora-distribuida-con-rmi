package mx.tese;

import mx.tese.RMI.ServidorReplica;
import mx.tese.RMI.ServerRegistry;
import mx.tese.RMI_Impl.ServidorReplicaImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ServidorCalculadora {
    public static void main(String[] args,Scanner scanner) throws InterruptedException {
        String direccionIP = obtenerDireccionIPValida(scanner);
        int default_server_port = obtenerPuertoRMI(scanner);
        int puerto = obtenerPuertoValido(scanner);
        try {
            System.out.println("INICIANDO REPLICA CON PUERTO " + puerto + "...");
            // Crea un registro RMI en el puerto especificado
            Registry registry = LocateRegistry.createRegistry(puerto);

            // Crea una instancia de la servidorReplica
            ServidorReplica servidorReplica = new ServidorReplicaImpl(puerto);

            // Registra la instancia en el registro RMI
            registry.rebind("ServidorReplica", servidorReplica);

            System.out.println("La servidorReplica está lista en el puerto " + puerto + ".");
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
        int intentosMaximos = 3; // Puedes ajustar el número de intentos según tus necesidades
        int intento = 1;

        while (intento <= intentosMaximos) {
            try {
                // Intenta llamar al método registrarServidor() en otro servidor RMI
                String servidorRemotoURL = "rmi://" + direccionIP + ":" + default_server_port + "/ServerRegistry";
                ServerRegistry serverRegistry = (ServerRegistry) Naming.lookup(servidorRemotoURL);

                // Llama al método para registrar el servidor
                boolean ok = serverRegistry.registrarServidor("localhost", puerto);
                if (ok) {
                    System.out.println("Servidor registrado en el servidor RMI remoto con éxito.");
                    break; // Sal del bucle si la llamada fue exitosa
                }else{
                    System.err.println("Error al registrar el servidor: problema del servidor.");
                    System.out.println("Intentando nuevamente en 5 segundos...");
                }
            } catch (Exception e) {
                System.err.println("Error al registrar el servidor en el intento " + intento + ": " + e.getMessage());
                System.out.println("Intentando nuevamente en 5 segundos...");
            }
            Thread.sleep(5000); // Espera 5 segundos antes de intentar nuevamente
            intento++;
        }
        if (intento > intentosMaximos) {
            System.out.println("Se agotaron los intentos. No se pudo registrar el servidor en el servidor RMI remoto.");
            System.exit(0);
        }
    }

    private static int obtenerPuertoValido(Scanner scanner) {
        int puerto = 0;
        boolean puertoValido = false;

        while (!puertoValido) {
            try {
                System.out.print("Ingrese el puerto donde se ejecutara esta Replica: ");
                puerto = Integer.parseInt(scanner.nextLine());
                if (puerto >= 0 && puerto <= 65535) {
                    puertoValido = true;
                } else {
                    System.out.println("El puerto debe estar en el rango de 0 a 65535.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido para el puerto.");
            }
        }

        return puerto;
    }

    public static int obtenerPuertoRMI(Scanner scanner) {

        System.out.print("Introduce el puerto del ServerRegisttry o presiona Enter para usar el puerto por defecto (1099): ");
        String puertoStr = scanner.nextLine().trim();

        if (puertoStr.isEmpty()) {
            return 1099; // Usar el puerto por defecto (1099)
        }

        try {
            int puerto = Integer.parseInt(puertoStr);
            if (puerto >= 0 && puerto <= 65535) {
                return puerto;
            } else {
                System.out.println("El puerto debe estar en el rango de 0 a 65535.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un número válido para el puerto.");
        }

        return obtenerPuertoRMI(scanner); // Llamada recursiva si la entrada no es válida
    }

    public static String obtenerDireccionIPValida(Scanner scanner) {

        while (true) {
            System.out.print("Introduce la dirección del ServiceRegistry o presiona Enter para usar la dirección por defecto (localhost): ");
            String direccionIP = scanner.nextLine().trim();

            if (direccionIP.isEmpty()) {
                return "localhost"; // Usar la dirección por defecto (localhost)
            }

            // Validar que la dirección IP sea válida
            if (esDireccionIPValida(direccionIP)) {
                return direccionIP;
            } else {
                System.out.println("Dirección IP no válida. Por favor, ingrese una dirección IP válida.");
            }
        }
    }

    public static boolean esDireccionIPValida(String direccionIP) {
        // Utilizar una expresión regular para validar la dirección IP
        String patronIP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

        return Pattern.matches(patronIP, direccionIP);
    }
}