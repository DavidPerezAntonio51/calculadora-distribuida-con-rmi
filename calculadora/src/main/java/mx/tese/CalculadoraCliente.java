package mx.tese;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mx.tese.Cliente.Interfaz;

import javax.swing.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CalculadoraCliente{
    public static void main(String[] args, Scanner scanner) {
        String host = obtenerDireccionIPValida(scanner);
        int puerto = obtenerPuertoSocket(scanner);
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
            if (UIManager.getLookAndFeel() instanceof MaterialLookAndFeel) {
                MaterialLookAndFeel.changeTheme(new MaterialLiteTheme());
                SwingUtilities.invokeLater(() -> {
                    Interfaz calculadora = new Interfaz(host, puerto);
                    calculadora.setVisible(true);
                    calculadora.setLocationRelativeTo(null); // Centra la ventana en la pantalla
                    calculadora.requestFocus(); // Da el foco a la ventana
                });
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    public static int obtenerPuertoSocket(Scanner scanner) {

        System.out.print("Introduce el puerto del servidor o presiona Enter para usar el puerto por defecto (8080): ");
        String puertoStr = scanner.nextLine().trim();

        if (puertoStr.isEmpty()) {
            return 8080; // Usar el puerto por defecto (1099)
        }

        try {
            int puerto = Integer.parseInt(puertoStr);
            if (puerto >= 0 && puerto <= 65535) {
                return puerto;
            } else {
                System.out.println("El puerto debe estar en el rango de 1024 a 65535.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un número válido para el puerto.");
        }

        return obtenerPuertoSocket(scanner); // Llamada recursiva si la entrada no es válida
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
