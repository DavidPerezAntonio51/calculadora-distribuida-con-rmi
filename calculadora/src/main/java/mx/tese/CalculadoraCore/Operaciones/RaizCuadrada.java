package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

public class RaizCuadrada implements Operacion {
    private final Operacion operacion;

    public RaizCuadrada(Operacion operacion) {
        this.operacion = operacion;
    }

    @Override
    public double ejecutar() {
        return Math.sqrt(operacion.ejecutar());
    }
}
