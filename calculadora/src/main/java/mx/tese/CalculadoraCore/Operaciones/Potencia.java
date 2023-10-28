package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

public class Potencia implements Operacion {
    private final Operacion operacion1;
    private final Operacion operacio2;
    public Potencia(Operacion operacion1, Operacion operacion2) {
        this.operacion1 = operacion1;
        this.operacio2 = operacion2;
    }

    @Override
    public double ejecutar() {
        return Math.pow(operacion1.ejecutar(),operacio2.ejecutar());
    }
}
