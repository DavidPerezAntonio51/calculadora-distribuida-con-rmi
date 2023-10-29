package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

public class ExpresionNegativa implements Operacion {
    private final Operacion result;
    public ExpresionNegativa(Operacion result) {
        this.result = result;
    }

    @Override
    public double ejecutar() {
        return result.ejecutar()* -1;
    }
}
