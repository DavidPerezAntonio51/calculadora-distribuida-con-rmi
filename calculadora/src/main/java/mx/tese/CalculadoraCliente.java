package mx.tese;

import mx.tese.CalculadoraCore.Lexico.*;
import mx.tese.CalculadoraCore.Operacion;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CalculadoraCliente extends JFrame {
    private JTextField pantalla; // Pantalla de la calculadora
    private double resultado; // Almacena el resultado
    private String operador; // Almacena el operador actual
    private boolean nuevoNumero; // Indica si se debe iniciar un nuevo número

    // Constructor
    public CalculadoraCliente() {
        // Inicializa la ventana de la calculadora
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializa la pantalla
        pantalla = new JTextField();
        pantalla.setEditable(false);
        //This = Jframe
        this.add(pantalla, BorderLayout.NORTH);

        // Crea un panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 4));

        // Array de etiquetas para los botones
        String[] botones = {"7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "C", "0", "=", "/"};

        // Agrega los botones al panel
        for (String boton : botones) {
            JButton btn = new JButton(boton);
            panelBotones.add(btn);
        }

        this.add(panelBotones, BorderLayout.CENTER);

        // Inicializa variables
        resultado = 0;
        operador = "";
        nuevoNumero = true;
    }

    public static void main(String[] args, Scanner scanner) {
        String op = "(6+8+9)*(6-2+cos(2/3))+(2^2)";
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(new StringReader(op));
        java.util.List<Token> tokens = new ArrayList<>();
        Token token;
        try {
            while ((token = analizadorLexico.yylex()) != null && token.getType() != TokenType.EOF) {
                System.out.println(token);
                tokens.add(token);
            }
        } catch (IOException e) {
            System.err.println("Error al analizar la expresión: " + e.getMessage());
        }
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(tokens);
        try {
            Operacion operacion = analizadorSintactico.parseExpression();
            System.out.println(operacion.ejecutar());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //SwingUtilities.invokeLater(() -> {
        //  CalculadoraCliente calculadora = new CalculadoraCliente();
        //calculadora.setVisible(true);
        //});
    }
}
