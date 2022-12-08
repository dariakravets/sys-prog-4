public class Token {
    public String lexeme;
    public Lexeme type;

    public Token(String lexeme, Lexeme type) {
        this.lexeme = lexeme;
        this.type = type;
    }

    @Override
    public String toString() {
        return lexeme + " : " + type;
    }
}
