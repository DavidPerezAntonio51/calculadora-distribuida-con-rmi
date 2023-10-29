package mx.tese.Cliente;

import mx.tese.CalculadoraCore.Respuesta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class DisplayWriterController implements ActionListener {
    private final JTextField display;
    private final Stack<String> stackText;
    private final ClientSocketWorker clientSocketWorker;
    private final ResponseNotifyController responseNotifyController;

    public DisplayWriterController(JTextField display, ClientSocketWorker clientSocketWorker, ResponseNotifyController responseNotifyController) {
        this.display = display;
        this.clientSocketWorker = clientSocketWorker;
        this.responseNotifyController = responseNotifyController;
        stackText = new Stack<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "<=":
                if (!stackText.empty()) {
                    stackText.pop();
                }
                break;
            case "CLR":
                stackText.clear();
                break;
            case "=":
                Respuesta respuesta = clientSocketWorker.enviarPeticion(display.getText());
                responseNotifyController.notify(respuesta);
                break;
            case "sen":
            case "cos":
            case "tan":
            case "sqrt":
                stackText.push(button.getText() + "(");
                break;
            case "^":
            case "(":
            case ")":
            case "+":
            case "-":
            case "*":
            case "/":
            case ".":
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                stackText.push(button.getText());
                break;
            default:
                break;
        }
        StringBuilder textModified = new StringBuilder();
        for (String s : stackText) {
            textModified.append(s);
        }
        display.setText(textModified.toString());
    }
}
