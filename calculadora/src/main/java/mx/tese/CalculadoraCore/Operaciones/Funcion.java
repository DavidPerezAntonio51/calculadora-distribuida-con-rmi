package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Lexico.TokenType;
import mx.tese.CalculadoraCore.Operacion;

public class Funcion implements Operacion {
    private final TokenType type;
    private final Operacion argument;
    public Funcion(TokenType type, Operacion argument) {
        this.type = type;
        this.argument = argument;
    }

    @Override
    public double ejecutar() {
        switch (type) {
            case SEN:
                return Math.sin(argument.ejecutar());
            case COS:
                return Math.cos(argument.ejecutar());
            case TAN:
                return Math.tan(argument.ejecutar());
            case RAIZ:
                return Math.sqrt(argument.ejecutar());
            default:
                throw new RuntimeException("No se puede ejecutar la funcion: " + type);
        }
    }
}
