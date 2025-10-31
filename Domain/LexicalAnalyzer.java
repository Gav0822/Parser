package Domain;
import FileIO.FileIO;

/**
 * LexicalAnalyzer reads a source string (from a file) and produces tokens
 * one at a time for parsing.
 *
 * @see Token
 */
public class LexicalAnalyzer {

    private String[] source;
    private int current_row;
    private int current_col;

    /**
     * Default constructor for LexicalAnalyzer.
     * Initializes with no source.
     */
    public LexicalAnalyzer() {
        source = null;
        current_row = 0;
        current_col = 0;
    }

    /**
     * Constructs a LexicalAnalyzer using the contents of a file.
     *
     * @param fileName the name of the file to read
     * @see FileIO
     */
    public LexicalAnalyzer(String fileName) {
        try{
            FileIO file = new FileIO(fileName); 
            source = file.getSentences();
            current_row = 0;
            current_col = 0;
        } catch(Exception e){
            System.err.println("File not found: " + e);
        }
    }

    /**
     * Attempts to determine the token type for the provided lexeme.
     *
     * @param lexeme the string to classify
     * @return the corresponding {@link Type}, or {@code null} if invalid
     * @see Type
     */
    public Token.Type findType(String lexeme){
        if(lexeme == null) return Token.Type.EOS;
        
        for(Token.Type tType : Token.Type.values()){
            if(tType.lexeme != null && tType.lexeme.equals(lexeme)) return tType;
        }

        for(char c : lexeme.toCharArray()){
            if(!Character.isDigit(c)) return null;
        }

        return Token.Type.INT_LITERAL;
    }

    /**
     * Retrieves the next token from the source string, advancing the internal index.
     *
     * @return the next {@link Token} found, or an EOS token if at the end of input
     * @see Token
     * Cleaned this up a lot.
     */
    public Token getNextToken(){

        char[] c = this.source[this.current_row].toCharArray();

        if(this.current_row >= source.length-1 && this.current_col >= c.length)
            return new Token("EOS",Token.Type.EOS, this.current_row, this.current_col);

        if(current_col >= c.length){
            this.current_row++;
            this.current_col = 0;
            return this.getNextToken();
        }

        char currentChar = c[this.current_col];
        String strChar = String.valueOf(currentChar);

        
        if(Character.isWhitespace(currentChar)){
            current_col++;
            return this.getNextToken();
        }
        else if(Character.isDigit(currentChar)){
            StringBuilder INT_LITERAL_LEX = new StringBuilder();
            while(current_col < c.length && Character.isDigit(currentChar)){
                INT_LITERAL_LEX.append(currentChar);
                this.current_col++;
                currentChar = c[this.current_col];
            }
            return new Token(INT_LITERAL_LEX.toString(), Token.Type.INT_LITERAL, this.current_row, this.current_col);
        }
        else {
            this.current_col++;
            return new Token(strChar, findType(strChar), this.current_row, this.current_col);
        }
        
    }

    /**
     * Returns the next token from the source without advancing the internal index.
     * Useful for lookahead.
     *
     * @return the next {@link Token} without consuming it
     * @see Token
     */
    public Token peek(){
        int temprow = this.current_row;
        int tempcol = this.current_col;

        Token t = this.getNextToken();

        this.current_row = temprow;
        this.current_col = tempcol;
        return t;
    }

   /**********************************
    *  Correction made: Removed redundant/unused code
    */
}