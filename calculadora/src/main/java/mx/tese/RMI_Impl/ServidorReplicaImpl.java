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
        Reader stringReader = new StringReader(operacion);
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(stringReader);
        List<Token> tokens = new ArrayList<>();
        System.out.println("Analizando operacion:" + operacion + " ...");
        Token token = null;
        try {
            while ((token = analizadorLexico.yylex()) != null && token.getType() != TokenType.EOF) {
                tokens.add(token);
            }
            tokens.add(new Token(TokenType.EOF, null));
        } catch (IOException e) {
            return new Respuesta(e.getMessage());
        }
        if (tokens.size() <= 1) {
            return new Respuesta("No hay operacion");
        }
        System.out.println("Operacion analizada correctamente. Revisando gramatica...");
        AnalizadorSintactico analizador = new AnalizadorSintactico(tokens);
        Operacion operacionEvaluada = null;
        try {
            operacionEvaluada = analizador.parseExpression();
        } catch (ParseException e) {
            System.err.println("Error gramatical:" + e.getMessage());
            return new Respuesta(e.getMessage());
        }
        try {
            System.out.println("Resolviendo operacion...");
            double ejecutar = operacionEvaluada.ejecutar();
            System.out.println("Operacion resuelta correctamente. Resultado: " + ejecutar);
            return new Respuesta(Optional.of(ejecutar));
        } catch (ArithmeticException e) {
            return new Respuesta(e.getMessage());
        }
    }

    @Override
    public boolean healthCheck() throws RemoteException {
        return true;
    }
}
