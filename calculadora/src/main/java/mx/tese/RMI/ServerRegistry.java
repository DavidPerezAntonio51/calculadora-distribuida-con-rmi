package mx.tese.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRegistry extends Remote {
    boolean registrarServidor(String ip, Integer puerto) throws RemoteException;
}
