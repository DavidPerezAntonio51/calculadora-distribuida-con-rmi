package mx.tese.BusMensajesCore;

import mx.tese.RMI.ServidorReplica;

import java.util.Optional;

public interface ServerRegistryManager {
    Optional<Replica<ServidorReplica>> getServer();
    void regresarServer(Replica<ServidorReplica> server);
}
