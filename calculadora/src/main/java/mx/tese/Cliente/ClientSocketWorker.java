package mx.tese.Cliente;

import mx.tese.CalculadoraCore.Respuesta;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class ClientSocketWorker {
    private String host = "localhost";
    private int port = 1099;

    public ClientSocketWorker(String host, int port) {
        this.host = host;
        this.port = port;
    }

    Respuesta enviarPeticion(String peticion) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeUTF(peticion);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Si la conexión es exitosa, retorna la respuesta
            Respuesta respuesta = (Respuesta) objectInputStream.readObject();
            socket.close();
            return respuesta;
        } catch (IOException e) {
            return new Respuesta("Error de conexión, intente en unos momentos...");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
