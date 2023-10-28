package mx.tese.CalculadoraCore.Operaciones;

import mx.tese.CalculadoraCore.Operacion;

import java.io.Serializable;
import java.util.Objects;

public class Division implements Serializable, Operacion {
    private final Operacion dividendo;
    private final Operacion divisor;
    public Division(Operacion dividendo, Operacion divisor){
        this.dividendo = dividendo;
        this.divisor = divisor;
    }
    @Override
    public double ejecutar() {
        if(divisor.ejecutar() == 0){
            throw new ArithmeticException("No se puede dividir entre cero");
        }
        return dividendo.ejecutar() / divisor.ejecutar();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Division division = (Division) o;

        if (!Objects.equals(dividendo, division.dividendo)) return false;
        return Objects.equals(divisor, division.divisor);
    }

    @Override
    public int hashCode() {
        int result = dividendo != null ? dividendo.hashCode() : 0;
        result = 31 * result + (divisor != null ? divisor.hashCode() : 0);
        return result;
    }
}
