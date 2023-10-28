package mx.tese.BusMensajesCore;

public class Replica <T>{
    private final T replica;
    private final Integer port;
    private final String ip;

    public Replica(T replica, Integer port, String ip) {
        this.replica = replica;
        this.port = port;
        this.ip = ip;
    }

    public T getReplica() {
        return replica;
    }

    public Integer getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }
}
