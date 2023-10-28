package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

public class Tangente implements Operacion {
    private final Operacion operacion;

    public Tangente(Operacion operacion) {
        this.operacion = operacion;
    }

    @Override
    public double ejecutar() {
        return Math.tan(operacion.ejecutar());
    }
}
