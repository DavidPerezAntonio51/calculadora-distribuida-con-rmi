package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

public class NumeroNegativo extends Numero{
    public NumeroNegativo(Double value) {
        super(value);
    }

    @Override
    public double ejecutar() {
        return super.ejecutar()*-1;
    }
}
