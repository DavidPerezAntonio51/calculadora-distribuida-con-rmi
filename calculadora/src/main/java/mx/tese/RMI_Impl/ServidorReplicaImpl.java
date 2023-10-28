package mx.tese.RMI_Impl;

import mx.tese.CalculadoraCore.Lexico.*;
import mx.tese.CalculadoraCore.Operacion;
import mx.tese.CalculadoraCore.Respuesta;
import mx.tese.RMI.ServidorReplica;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServidorReplicaImpl extends UnicastRemoteObject implements ServidorReplica {
    private final Integer port;

    public ServidorReplicaImpl(int port) throws RemoteException {
        this.port = port;
    }

    @Override
    public Respuesta calcular(String operacion) {
        System.out.println("Calculando en servidor:" + port + " ...");
        Reader stringReader = new StringReader(operacion);
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(stringReader);
        List<Token> tokens = new ArrayList<>();
        Token token = null;
        try {
            while ((token = analizadorLexico.yylex()) != null && token.getType() != TokenType.EOF) {
                tokens.add(token);
            }
            tokens.add(new Token(TokenType.EOF, null));
        }catch (IOException e) {
            return new Respuesta(e.getMessage());
        }
        if(tokens.size()<=1){
            return new Respuesta("No hay operacion");
        }
        AnalizadorSintactico analizador = new AnalizadorSintactico(tokens);
        Operacion operacionEvaluada = null;
        try {
            operacionEvaluada = analizador.parseExpression();
        } catch (ParseException e) {
            return new Respuesta(e.getMessage());
        }
        double ejecutar = operacionEvaluada.ejecutar();
        return new Respuesta(Optional.of(ejecutar));
    }

    @Override
    public boolean healthCheck() throws RemoteException {
        return true;
    }
}
