package mx.tese;

import mx.tese.BusMensajesCore.RequestWorker;
import mx.tese.BusMensajesCore.ServerRegistryManager;
import mx.tese.RMI.ServerRegistry;
import mx.tese.RMI_Impl.ServerRegistryImpl;

import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BusMensajes {
    public static void main(String[] args,Scanner scanner) throws InterruptedException {
        int puertoRMI = obtenerPuertoRMIValido(scanner); // Solicita al usuario el puerto RMI
        int puertoServerSocket = obtenerPuertoServerSocketValido(scanner); // Solicita al usuario el puerto del servidor de sockets
        int maxThreads = 10; // Establece el límite máximo de threads

        // Solicita al usuario la cantidad de threads
        int numThreads = solicitarNumeroThreads(maxThreads,scanner);

        // Crea el ExecutorService con la cantidad de threads especificada
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        try {
            System.out.println("INICIANDO BUS DE MENSAJES...");
            // Crea un registro RMI en el puerto por defecto
            Registry registry = LocateRegistry.createRegistry(puertoRMI);

            // Crea una instancia de la calculadora
            ServerRegistry serverRegistry = new ServerRegistryImpl();
            // Registra la instancia en el registro RMI
            registry.rebind("ServerRegistry", serverRegistry);
            System.out.println("El ServerRegisty escucha en el puerto " + puertoRMI + ".");
            ServerRegistryManager serverRegistryManager = (ServerRegistryManager) serverRegistry;
            System.out.println("Iniciando socket listener en el puerto " + puertoServerSocket + "...");
            ServerSocket serverSocket = new ServerSocket(puertoServerSocket);
            System.out.println("El socket listener está listo.");
            System.out.println("EL BUS SE INICIO CORRECTAMENTE");
            while (true) {
                // Espera a que se conecte un cliente
                System.out.println("Esperando Conexiones...");
                Socket clienteRequest = serverSocket.accept();
                System.out.println("Cliente conectado desde:" + clienteRequest.getRemoteSocketAddress());
                executor.execute(new RequestWorker(clienteRequest, serverRegistryManager));
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }finally {
            executor.shutdown();
        }
    }
    private static int solicitarNumeroThreads(int maxThreads, Scanner scanner) {
        int numThreads = 0;
        boolean inputValid = false;

        while (!inputValid) {
            try {
                System.out.print("Ingrese la cantidad de threads (1-" + maxThreads + "): ");
                numThreads = Integer.parseInt(scanner.nextLine());
                if (numThreads >= 1 && numThreads <= maxThreads) {
                    inputValid = true;
                } else {
                    System.out.println("El número de threads debe estar en el rango de 1 a " + maxThreads + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }

        return numThreads;
    }
    public static int obtenerPuertoRMIValido(Scanner scanner) {
        int puerto = 0;
        boolean puertoValido = false;

        while (!puertoValido) {
            try {
                System.out.print("Ingrese el puerto del servidor RMI (presione Enter para usar el puerto por defecto 1099): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    puerto = 1099; // Puerto por defecto
                    puertoValido = true;
                } else {
                    puerto = Integer.parseInt(input);
                    if (puerto >= 0 && puerto <= 65535) {
                        puertoValido = true;
                    } else {
                        System.out.println("El puerto debe estar en el rango de 0 a 65535.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido para el puerto.");
            }
        }

        return puerto;
    }

    public static int obtenerPuertoServerSocketValido(Scanner scanner) {
        int puerto = 0;
        boolean puertoValido = false;

        while (!puertoValido) {
            try {
                System.out.print("Ingrese el puerto del servidor de sockets (presione Enter para usar el puerto por defecto 8080): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    puerto = 8080; // Puerto por defecto
                    puertoValido = true;
                } else {
                    puerto = Integer.parseInt(input);
                    if (puerto >= 0 && puerto <= 65535) {
                        puertoValido = true;
                    } else {
                        System.out.println("El puerto debe estar en el rango de 0 a 65535.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido para el puerto.");
            }
        }

        return puerto;
    }
}
