package mx.tese.CalculadoraCore;

import java.io.Serializable;
import java.util.Optional;

public class Respuesta implements Serializable {
    private final String error;
    private final Optional<Double> resultado;

    public Respuesta(Optional<Double> resultado) {
        this.resultado = resultado;
        error = "";
    }

    public Respuesta(String error) {
        this.error = error;
        resultado = Optional.empty();
    }

    public String getError() {
        return error;
    }

    public Double getResultado() {
        if(resultado.isPresent()){
            return resultado.get();
        }
        throw new RuntimeException("No hay resultado");
    }

    public boolean success(){
        return resultado.isPresent();
    }
}
