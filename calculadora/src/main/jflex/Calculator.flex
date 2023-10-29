package mx.tese.CalculadoraCore.Lexico;

%%
%public
%class AnalizadorLexico
digit = [0-9]
suma = [+]
resta = [-]
multiplicacion = [*]
division = [/]
cos = cos
sen = sen
tan = tan
raiz = sqrt
potencia = [\^]
parentesis_izquierdo = [(]
parentesis_derecho = [)]
%type Token

%eofval{
    return new Token(TokenType.EOF,null);
%eofval}

%%
{digit}+('.'{digit}+)? {return new Token(TokenType.NUMERO,yytext());}
{suma} { return new Token(TokenType.SUMA,yytext()); }
{resta}     { return new Token(TokenType.RESTA,yytext()); }
{multiplicacion}     { return new Token(TokenType.MULTIPLICACION,yytext()); }
{division}     { return new Token(TokenType.DIVISION,yytext()); }
{cos}   { return new Token(TokenType.COS,yytext()); }
{sen}   { return new Token(TokenType.SEN,yytext()); }
{tan}   { return new Token(TokenType.TAN,yytext()); }
{raiz}   { return new Token(TokenType.RAIZ,yytext()); }
{potencia}   { return new Token(TokenType.POTENCIA,yytext()); }
{parentesis_izquierdo}   { return new Token(TokenType.PARENTESIS_IZQUIERDO,yytext()); }
{parentesis_derecho}   { return new Token(TokenType.PARENTESIS_DERECHO,yytext()); }
[ \t\n]    { /* ignore whitespace */ }
[^]          { throw new Error("Illegal character <"+yytext()+">"); }