package mx.tese.BusMensajesCore;

import mx.tese.CalculadoraCore.Respuesta;
import mx.tese.RMI.ServidorReplica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Optional;

public class RequestWorker implements Runnable {
    private final ServerRegistryManager serverRegistry;
    private final Socket clienteRequest;

    public RequestWorker(Socket clienteRequest, ServerRegistryManager serverRegistry) {
        this.serverRegistry = serverRegistry;
        this.clienteRequest = clienteRequest;
    }

    @Override
    public void run() {
        Optional<Replica<ServidorReplica>> server = serverRegistry.getServer();
        try {
            ObjectInputStream input = new ObjectInputStream(clienteRequest.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(clienteRequest.getOutputStream());
            if (!server.isPresent()) {
                output.writeObject(new Respuesta("No hay servidores disponibles para procesar la solicitud."));
                System.out.println("No hay servidores disponibles para procesar la solicitud.");
            }
            Replica<ServidorReplica> replica = server.get();
            System.out.println("Enviando solicitud al servidor con puerto: " + replica.getPort());
            ServidorReplica servidorReplica = replica.getReplica();
            String operacion = input.readUTF();
            Respuesta calcular = servidorReplica.calcular(operacion);
            output.writeObject(calcular);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (server.isPresent()&&server.get().getReplica().healthCheck()) {
                    serverRegistry.regresarServer(server.get());
                }
            } catch (RemoteException e) {
                System.out.println("Servidor no disponible, ya no se encola de nuevo.");
            }
        }
    }
}
