package Domain;
import Domain.Token;
import java.util.ArrayList;
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
     * Retrieves the next token from the source string, advancing the internal index.
     *
     * @return the next {@link Token} found, or an EOS token if at the end of input
     * @see Token
     */
    public Token getNextToken(){
        

        /*if(this.source[this.current_row] == null)
            return new Token(null, this.current_row, this.current_col);*/

        char[] c = this.source[this.current_row].toCharArray();

        if(this.current_row >= source.length-1 && this.current_col >= c.length)
            return new Token(null, this.current_row, this.current_col);

        if(current_col >= c.length){
            this.current_row++;
            this.current_col = 0;
            return this.getNextToken();
        }
        else if(Character.isWhitespace(c[current_col])){
            current_col++;
            return this.getNextToken();
        }
        else if(Character.isDigit(c[this.current_col])){
            StringBuilder INT_LITERAL_LEX = new StringBuilder();
            while(current_col < c.length && Character.isDigit(c[this.current_col])){
                INT_LITERAL_LEX.append(c[this.current_col]);
                this.current_col++;
            }
            return new Token(INT_LITERAL_LEX.toString(), this.current_row, this.current_col);
            /*String lex = String.valueOf(c[this.current_col]);
            Token t = new Token(lex, this.current_row, this.current_col);
            this.current_col++;
            return t;*/
        }
        else {
            Token t = new Token(String.valueOf(c[current_col]), this.current_row, this.current_col);
            this.current_col++;
            return t;
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

    /**
     * Generates a list of tokens from an array of string arguments.
     *
     * @param args array of input strings
     * @return a list of {@link Token} objects generated from the arguments
     * @see Token#generateTokenList(String)
     */
    public Token[] generateTokenList(String[] args){
        ArrayList<Token> tokens = new ArrayList<>();
        
        for(int i = 0; i < args.length; i++){
            tokens.addAll(generateTokenList(args[i], i));
        }

        return tokens.toArray(new Token[]{});
    }

    /**
     * Generates an array of tokens from a single input sentence.
     *
     * @param sentence the input string to tokenize
     * @return an array of {@link Token} objects representing the tokens found
     * @see Type
     */
    public Token[] generateTokenList(String sentence){
        ArrayList<Token> tokens = new ArrayList<>();
        char[] c = sentence.toCharArray();
        for(int j = 0; j < c.length; j++) {
            
            if(Character.isWhitespace(c[j])) continue;

            Token t = new Token(String.valueOf(c[j]), 0, j);

            if(t.type.equals(Token.Type.EOS)) {
                tokens.add(t);
                return tokens.toArray(new Token[] {});
            } 
            else if(t.type.equals(Token.Type.INT_LITERAL)){

                StringBuilder INT_LITERAL_LEX = new StringBuilder();
                while(j < c.length && Character.isDigit(c[j])){
                    INT_LITERAL_LEX.append(c[j]);
                    j++;
                }
                j--;
                t = new Token(INT_LITERAL_LEX.toString(), 0, j);
                tokens.add(t);

            }
            else {
                tokens.add(t);
            }
        }
        return tokens.toArray(new Token[]{});
    }

    public ArrayList<Token> generateTokenList(String sentence, int row){
        ArrayList<Token> tokens = new ArrayList<>();
        char[] c = sentence.toCharArray();
        for(int j = 0; j < c.length; j++) {
            
            if(Character.isWhitespace(c[j])) continue;

            Token t = new Token(String.valueOf(c[j]), row, j);

            if(t.type.equals(Token.Type.EOS)) {
                tokens.add(t);
                return tokens;
            } 
            else if(t.type.equals(Token.Type.INT_LITERAL)){

                StringBuilder INT_LITERAL_LEX = new StringBuilder();
                while(j < c.length && Character.isDigit(c[j])){
                    INT_LITERAL_LEX.append(c[j]);
                    j++;
                }
                j--;
                t = new Token(INT_LITERAL_LEX.toString(), row, j);
                tokens.add(t);

            }
            else {
                tokens.add(t);
            }
        }
        return tokens;
    }
}