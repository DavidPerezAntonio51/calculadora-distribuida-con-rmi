package mx.tese.CalculadoraCore.Lexico;

import mx.tese.CalculadoraCore.Operacion;
import mx.tese.CalculadoraCore.Operaciones.*;

import java.util.List;

public class AnalizadorSintactico {
    private List<Token> tokens;
    private int currentTokenIndex;

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = tokens;
        currentTokenIndex = 0;
    }

    public Operacion parseExpression() throws ParseException {
        Operacion result = parseTerm();
        while (checkToken(TokenType.SUMA) || checkToken(TokenType.RESTA)) {
            switch (currentToken().getType()) {
                case SUMA:
                    matchToken(TokenType.SUMA);
                    result = new Suma(result, parseTerm());
                    break;
                case RESTA:
                    matchToken(TokenType.RESTA);
                    result = new Resta(result, parseTerm());
                    break;
                default:
                    throw new ParseException("Se esperaba + o - pero se encontró: " + currentToken().getLexema());
            }
        }
        return result;
    }

    private Operacion parseTerm() throws ParseException {
        Operacion result = parseFactor();
        while (checkToken(TokenType.MULTIPLICACION) || checkToken(TokenType.DIVISION)) {
            switch (currentToken().getType()) {
                case MULTIPLICACION:
                    matchToken(TokenType.MULTIPLICACION);
                    result = new Multiplicacion(result, parseFactor());
                    break;
                case DIVISION:
                    matchToken(TokenType.DIVISION);
                    result = new Division(result, parseFactor());
                    break;
                default:
                    throw new ParseException("Se esperaba * o / pero se encontró: " + currentToken().getLexema());
            }
        }
        return result;
    }

    private Operacion parseFactor() throws ParseException {
        Operacion result = parsePower();
        while (checkToken(TokenType.POTENCIA)) {
            switch (currentToken().getType()) {
                case POTENCIA:
                    matchToken(TokenType.POTENCIA);
                    result = new Potencia(result, parsePower());
                    break;
                default:
                    throw new ParseException("Se esperaba ^ pero se encontró: " + currentToken().getLexema());
            }
        }
        return result;
    }

    private Operacion parsePower() throws ParseException {
        if (checkToken(TokenType.PARENTESIS_IZQUIERDO)) {
            matchToken(TokenType.PARENTESIS_IZQUIERDO);
            Operacion result = parseExpression();
            matchToken(TokenType.PARENTESIS_DERECHO);
            return result;
        } else if (checkToken(TokenType.NUMERO)) {
            return new Numero(Double.parseDouble(nextToken().getLexema()));
        } else if (
                checkToken(TokenType.SEN)
                        || checkToken(TokenType.COS)
                        || checkToken(TokenType.TAN)
                        || checkToken(TokenType.RAIZ)
        ) {
            Token function = nextToken();
            matchToken(TokenType.PARENTESIS_IZQUIERDO);
            Operacion argument = parseExpression();
            matchToken(TokenType.PARENTESIS_DERECHO);
            switch (function.getType()) {
                case SEN:
                    return new Seno(argument);
                case COS:
                    return new Coseno(argument);
                case TAN:
                    return new Tangente(argument);
                case RAIZ:
                    return new RaizCuadrada(argument);
                // Agrega más funciones aquí
                default:
                    throw new ParseException(
                            "Se esperaba alguno de los siguientes [(,NUMERO,Funcion] pero se encontro: " +
                                    function.getLexema());
            }
        } else {
            throw new ParseException(
                    "Se esperaba alguno de los siguientes [(,NUMERO,Funcion] pero se encontro: " +
                            currentToken().getLexema());
        }
    }

    public Token nextToken() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex++);
        } else {
            throw new IllegalArgumentException("No hay más tokens");
        }
    }

    public Token currentToken() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        } else {
            throw new IllegalArgumentException("No hay más tokens");
        }
    }

    public boolean checkToken(TokenType type) {
        if (currentTokenIndex < tokens.size()) {
            Token token = tokens.get(currentTokenIndex);
            return token.getType() == type;
        } else {
            return false;
        }
    }

    public boolean matchToken(TokenType type) throws ParseException {
        if (checkToken(type)) {
            currentTokenIndex++;
            return true;
        } else {
            try {
                throw new ParseException("Se esperaba " + type + " pero se encontró " + currentToken().getLexema());
            } catch (IllegalArgumentException e) {
                throw new ParseException("Se esperaba " + type + " pero no se encontro");
            }
        }
    }
}
