package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Lexico.TokenType;
import mx.tese.CalculadoraCore.Operacion;

public class FuncionNegativa extends Funcion{
    public FuncionNegativa(TokenType type, Operacion argument) {
        super(type, argument);
    }

    @Override
    public double ejecutar() {
        return super.ejecutar()*-1;
    }
}
