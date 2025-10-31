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
        EOS("EOS");

        public String lexeme;

        Type(String str){
            lexeme = str;
        }

        Type() { };
    }

    private String lexeme;
    private int row_number;
    private int col_number;
    private Type type;

   /***********************************
    * Correction made: Fields are now private
    */

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
     * @param row the line position representation
     * @param col the character position
     * @see Type
     */
    public Token(String lexeme, Type type, int row, int col){
        if(row < 0){
            System.err.printf("Row number must be greater than 0.");    
            return;
        } 
        if(col < 0){
            System.err.printf("Column number must be greater than 0.");
            return;
        } 
        this.lexeme = lexeme;
        this.type = type;
        row_number = row+1;
        col_number = col+1;

       /**********************************
        * Fix: Error handling implemented. This implementation allows for the program to continue running 
        *       after a token parse failure.
        */
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

    

   /*******************************
    *Fixed: Moved findType() to lexical analyzer class
    */

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

   /*****************************
    * Fixed: match() function moved to Parser
    */

}