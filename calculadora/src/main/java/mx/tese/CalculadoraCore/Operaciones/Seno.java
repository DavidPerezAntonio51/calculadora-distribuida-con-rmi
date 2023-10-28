package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

public class Seno implements Operacion {
    private final Operacion operacion;

    public Seno(Operacion operacion) {
        this.operacion = operacion;
    }

    @Override
    public double ejecutar() {
        return Math.sin(operacion.ejecutar());
    }
}
