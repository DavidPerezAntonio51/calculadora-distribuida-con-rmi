package mx.tese;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int opcion = obtenerOpcionValida(scanner);
        switch (opcion) {
            case 1:
                // Programa Bus de Mensajes
                BusMensajes.main(args,scanner);
                break;
            case 2:
                // Programa Calculadora Cliente
                CalculadoraCliente.main(args,scanner);
                break;
            case 3:
                // Programa Servidor de Calculadora
                ServidorCalculadora.main(args,scanner);
                break;
        }
    }

    public static int obtenerOpcionValida(Scanner scanner) {
        int opcion = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.println("Seleccione el programa a ejecutar:");
            System.out.println("1. Bus de Mensajes");
            System.out.println("2. Calculadora Cliente");
            System.out.println("3. Servidor de Calculadora");
            System.out.print("Ingrese el número de programa (1/2/3): ");
            String opcionString = scanner.nextLine().trim();

            if (!opcionString.isEmpty()) {
                try {
                    opcion = Integer.parseInt(opcionString);
                    if (opcion >= 1 && opcion <= 3) {
                        entradaValida = true;
                    } else {
                        System.out.println("Opción no válida. Debe ser 1, 2 o 3.");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Entrada no válida. Debe ingresar un número.");
                }
            } else {
                System.err.println("Entrada no válida. Debe ingresar un número.");
            }
        }

        return opcion;
    }
}
