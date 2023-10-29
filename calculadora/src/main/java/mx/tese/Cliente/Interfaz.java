package mx.tese.Cliente;

import javax.swing.*;
import java.awt.*;

public class Interfaz  extends JFrame{
    private JTextField pantalla; // Pantalla de la calculadora
    private double resultado; // Almacena el resultado
    // Constructor
    public Interfaz(String host, int port) {
        // Inicializa la ventana de la calculadora
        setTitle("Calculadora");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Inicializa la pantalla
        pantalla = new JTextField();
        pantalla.setFont(new Font("Arial", Font.BOLD, 25));
        pantalla.setEditable(false);
        pantalla.setPreferredSize(new Dimension(0, 60)); // Ajusta la altura de la pantalla
        panel.add(pantalla, BorderLayout.NORTH);

        // Crea un panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 5, 10, 10));

        // Array de etiquetas para los botones
        String[] botones = {"7", "8", "9", "+", "sen",
                "4", "5", "6", "-", "cos",
                "1", "2", "3", "*", "tan",
                "<=", "0", ".", "/", "sqrt",
                "(", ")", "^", "CLR", "="};

        // Agrega los botones al panel en el orden correcto
        ClientSocketWorker  clientSocketWorker = new ClientSocketWorker(host, port);
        ResponseNotifyController responseNotifyController = new ResponseNotifyController(this);
        DisplayWriterController displayWriterController = new DisplayWriterController(pantalla, clientSocketWorker, responseNotifyController);
        for (String boton : botones) {
            JButton btn = new JButton(boton);
            btn.addActionListener(displayWriterController);
            panelBotones.add(btn);
        }

        panel.add(panelBotones, BorderLayout.CENTER);
        this.add(panel);
        // Inicializa variables
        resultado = 0;
    }
}
