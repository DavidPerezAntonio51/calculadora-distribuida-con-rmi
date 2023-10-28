package mx.tese.CalculadoraCore.Lexico;

public class Token {
    private TokenType type;
    private String lexema;

    public Token(TokenType type, String lexema) {
        this.type = type;
        this.lexema = lexema;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexema='" + lexema + '\'' +
                '}';
    }
}
