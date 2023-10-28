package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

import java.io.Serializable;

public class Numero implements Operacion, Serializable {
    private final double valor;

    public Numero(double valor) {
        this.valor = valor;
    }

    public Numero(int valor) {
        this.valor = valor;
    }

    public Numero(float valor) {
        this.valor = valor;
    }

    public Numero(String valor) {
        this.valor = Double.parseDouble(valor);
    }

    @Override
    public double ejecutar() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Numero numero = (Numero) o;

        return Double.compare(valor, numero.valor) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(valor);
        return (int) (temp ^ (temp >>> 32));
    }
}
