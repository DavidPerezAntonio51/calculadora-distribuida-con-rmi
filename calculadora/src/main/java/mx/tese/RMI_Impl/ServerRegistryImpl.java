package mx.tese.RMI_Impl;

import mx.tese.BusMensajesCore.Replica;
import mx.tese.BusMensajesCore.ServerRegistryManager;
import mx.tese.RMI.ServidorReplica;
import mx.tese.RMI.ServerRegistry;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerRegistryImpl extends UnicastRemoteObject implements ServerRegistry, ServerRegistryManager {

    private Queue<Replica<ServidorReplica>> replicas;

    public ServerRegistryImpl() throws RemoteException {
        this.replicas = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean registrarServidor(String ip, Integer puerto) {
        System.out.println("Registrando servidor: " + ip + ":" + puerto);
        String servidorRemotoURL = "rmi://" + ip + ":" + puerto + "/ServidorReplica";
        try {
            ServidorReplica nuevaReplica = (ServidorReplica) Naming.lookup(servidorRemotoURL);
            Replica<ServidorReplica> replica = new Replica<>(nuevaReplica, puerto, ip);
            replicas.offer(replica);
            System.out.println("Servidor registrado correctamente, en linea: " + nuevaReplica.healthCheck());
            return true;
        } catch (NotBoundException | RemoteException | MalformedURLException e) {
            System.err.println("Error al registrar el servidor con puerto: " + puerto);
            System.err.println("Message: " + e.getMessage() + " Cause: " + e.getCause());
        }
        return false;
    }

    @Override
    public synchronized Optional<Replica<ServidorReplica>> getServer() {
        if (replicas.isEmpty()) {
            // Si la cola está vacía, no hay elementos disponibles
            return Optional.empty();
        }
        while (true) {
            // Obtiene el elemento de la cola
            Replica<ServidorReplica> elemento = replicas.poll();
            if (elemento != null) {
                try {
                    System.out.println("Verificando la salud del servidor con puerto:"+elemento.getPort()+"...");
                    // Verifica si el servidor está activo llamando a checkHealt
                    if (elemento.getReplica().healthCheck()) {
                        // Si el servidor está activo, regresa el elemento
                        System.out.println("Servidor con puerto:"+elemento.getPort()+" En linea:" + elemento.getReplica().healthCheck());
                        return Optional.of(elemento);
                    } else {
                        System.out.println("El servidor ya no está activo. Descartando elemento.");
                    }
                } catch (RemoteException e) {
                    // Si se produce una excepción, el servidor no está activo, por lo que quitamos el elemento
                    System.err.println("Error al verificar la salud del servidor. Descartando elemento.");
                }
            } else {
                // La cola está vacía, no hay elementos válidos disponibles
                return Optional.empty();
            }
        }
    }

    @Override
    public void regresarServer(Replica<ServidorReplica> server) {
        replicas.offer(server);
    }
}
