package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

import java.io.Serializable;

public class Resta implements Operacion, Serializable {
    private final Operacion operando1;
    private final Operacion operando2;

    public Resta(Operacion operando1, Operacion operando2) {
        this.operando1 = operando1;
        this.operando2 = operando2;
    }

    @Override
    public double ejecutar() {
        return operando1.ejecutar()-operando2.ejecutar();
    }
}
