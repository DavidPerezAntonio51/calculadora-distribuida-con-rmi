package mx.tese.CalculadoraCore;

import java.io.Serializable;
import java.util.Optional;

public class Respuesta implements Serializable {
    private final String error;
    private final Double resultado; // Almacena un Double en lugar de Optional<Double>

    public Respuesta(Optional<Double> resultado) {
        this.resultado = resultado.orElse(null); // Usa null para representar la ausencia de un resultado
        error = "";
    }

    public Respuesta(String error) {
        this.error = error;
        resultado = null; // Usa null para representar la ausencia de un resultado
    }

    public String getError() {
        return error;
    }

    public Double getResultado() {
        if(resultado != null){ // Comprueba si resultado es distinto de null en lugar de usar isPresent()
            return resultado;
        }
        throw new RuntimeException("No hay resultado");
    }

    public boolean success(){
        return resultado != null; // Comprueba si resultado es distinto de null en lugar de usar isPresent()
    }
}
