package mx.tese.RMI;

import mx.tese.CalculadoraCore.Respuesta;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorReplica extends Remote {
    public Respuesta calcular(String operacion) throws RemoteException;
    public boolean healthCheck() throws RemoteException;
}