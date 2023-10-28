package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

public class Coseno implements Operacion {
    private final Operacion operacion;

    public Coseno(Operacion operacion) {
        this.operacion = operacion;
    }

    @Override
    public double ejecutar() {
        return Math.cos(operacion.ejecutar());
    }
}
