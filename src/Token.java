package Grupo6;

public class Token {
	//I am adding this myself to accept Reserved words for functions declarations
	public static final int TK_RESERVEDW = 0;		
	public static final int TK_TYPE = 1;
	public static final int TK_ID = 2;
    public static final int TK_SEPARATOR = 3;

    private int type;
    private String lexeme;
    private int position;

    public Token(int type, String lexeme, int position) {
        this.type = type;
        this.lexeme = lexeme;
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexeme='" + lexeme + '\'' +
                ", position=" + position +
                '}';
    }
}
