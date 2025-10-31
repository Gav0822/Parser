package Domain;

/**
 * Represents a lexical token produced during scanning.
 *
 * @see Token.Type
 */
public class Token {
    
    /**
     * Defines the types of tokens recognized by the scanner.
     *
     * @see Token
     */
    public enum Type {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("*"),
        DIVISION("/"),
        L_PAREN("("),
        R_PAREN(")"),
        INT_LITERAL(),
        EOS((String) null);

        public String lexeme;

        Type(String str){
            lexeme = str;
        }

        Type() { };
    }

    String lexeme;
    int row_number;
    int col_number;
    Type type;

    /**
     * Default constructor for Token.
     */
    public Token() {
        this.lexeme = null;
        this.type = Type.EOS;
        row_number = 0;
        col_number = 0;
    }

    /**
     * Constructs a Token from a given lexeme.
     *
     * @param lexeme the string representation of the token
     * @param col the character position
     * @see Type
     */
    public Token(String lexeme, int col){
        if(lexeme == null){
            this.lexeme = null;
            this.type = Type.EOS;
            return;
        }
        this.lexeme = lexeme;
        this.type = findType(lexeme);
        row_number = 0;
        col_number = col;
    }

    /**
     * Constructs a Token from a given lexeme.
     *
     * @param lexeme the string representation of the token
     * @param row the line position representation
     * @param col the character position
     * @see Type
     */
    public Token(String lexeme, int row, int col){
        if(findType(lexeme) == null){
            System.err.printf("Invaid string, cannot parse \"%s\" to single token\n", lexeme);
            return;
        }
        this.lexeme = lexeme;
        this.type = findType(lexeme);
        row_number = row+1;
        col_number = col+1;
    }

    public Type getType(){
        return this.type;
    }

    public String getLexeme(){
        return this.lexeme;
    }

    public int getRow(){
        return this.row_number;
    }

    public int getCol(){
        return this.col_number;
    }

    /**
     * Attempts to determine the token type for the provided lexeme.
     *
     * @param lexeme the string to classify
     * @return the corresponding {@link Type}, or {@code null} if invalid
     * @see Type
     */
    private Type findType(String lexeme){
        if(lexeme == null) return Type.EOS;
        
        for(Type tType : Type.values()){
            if(tType.lexeme != null && tType.lexeme.equals(lexeme)) return tType;
        }

        for(char c : lexeme.toCharArray()){
            if(!Character.isDigit(c)) return null;
        }

        return Type.INT_LITERAL;
    }

    /**
     * Returns a string representation of this token.
     *
     * @return a formatted string containing the token type and lexeme
     * @see Type
     */
    @Override
    public String toString(){
        return String.format("%s : %s || row=%d, col=%d", this.type.name(), this.lexeme, this.row_number, this.col_number);
    }

    public String name(){
        return this.lexeme;
    }

    public int row(){
        return this.row_number;
    }

    public int col(){
        return this.col_number;
    }

    public String tokenError(Token.Type t){
        return String.format("Syntax error on token %s, \"%s\" expected [Line %d, Col %d]", this.name(), t.toString(), this.row(), this.col());
    }

    public boolean match(Type type){
        return this.getType().equals(type);
    }

}