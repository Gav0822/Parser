package Domain;
import Domain.LexicalAnalyzer;
import Domain.Token;

/** Parser uses a lexical analyzer and implements recursive descent parsing.
 * @see LexicalAnalyzer
 * @see Token
 */

public class Parser {

    /** The lexical analyzer providing tokens to the parser. */
    private LexicalAnalyzer la;

    /** The current token being examined. */
    private Token token;

    /** Flag indicating whether an error occurred during parsing. */
    private boolean error;

    /**
     * Constructs a new {@code Parser} for the given input file.
     *
     * @param filename the name of the file to be parsed
     */
    public Parser(String filename) {
        la = new LexicalAnalyzer(filename);
        token = la.getNextToken();
        error = false;
    }

    /**
     * Parses an expression according to the grammar:
     * <expr> -> <term> <expr_prime>
     * Prints entry and exit messages for tracing.
     */
    public void expr() {
        this.error = false;
        println("Enter <expr>");
        term();
        expr_prime();
        println("Exit <expr>");
    }

    /**
     * Parses the continuation of an expression according to the grammar:
     * <expr_prime> -> (+|-) <term> <expr_prime> null
     * Prints entry and exit messages for tracing.
     */
    public void expr_prime() {
        println("Enter <expr_prime>");
        if (token.match(Token.Type.ADDITION)) {
            nextToken();
            term();
            expr_prime();
        } else if (token.match(Token.Type.SUBTRACTION)) {
            nextToken();
            term();
            expr_prime();
        }
        println("Exit <expr_prime>");
    }

    /**
     * Parses a term according to the grammar:
     * <term> -> <factor> <term_prime>
     * Prints entry and exit messages for tracing.
     */
    public void term() {
        println("Enter <term>");
        factor();
        term_prime();
        println("Exit <term>");
    }

    /**
     * Parses the continuation of a term according to the grammar:
     *     <term_prime> → (*|/) <factor> <term_prime> null
     * Prints entry and exit messages for tracing.
     */
    public void term_prime() {
        println("Enter <term_prime>");
        if (token.match(Token.Type.MULTIPLICATION)) {
            nextToken();
            factor();
            term_prime();
        } else if (token.match(Token.Type.DIVISION)) {
            nextToken();
            factor();
            term_prime();
        }
        println("Exit <term_prime>");
    }

    /**
     * Parses a factor according to the grammar:
     *     <factor> -> ( <expr> ) | - <expr> | <number>
     * Handles parentheses and unary minus.
     * Prints entry and exit messages for tracing.
     */
    public void factor() {
        println("Enter <factor>");
        if (token.match(Token.Type.L_PAREN)) {
            printNextToken();
            nextToken();
            expr();
            if (!token.match(Token.Type.R_PAREN)) {
                System.err.println(token.tokenError(Token.Type.R_PAREN));
                this.error = true;
            }
            nextToken();
        } else if (token.match(Token.Type.SUBTRACTION)) {
            nextToken();
            expr();
        } else {
            number();
        }
        println("Exit <factor>");
    }

    /**
     * Parses a number according to the grammar:
     * <number> -> INT_LITERAL
     * Prints entry and exit messages for tracing.
     * Reports an error if the current token is not an integer literal.
     */
    public void number() {
        println("Enter <number>");
        if (!token.match(Token.Type.INT_LITERAL)) {
            System.err.println(token.tokenError(Token.Type.INT_LITERAL));
            this.error = true;
        }

        printNextToken();
        nextToken();
        printNextToken();

        println("Exit <number>");
    }

    /**
     * Begins parsing the input stream until the end-of-stream (EOS) token is reached.
     * This serves as the main entry point for the parser.
     */
    public void parse() {
        while (!token.match(Token.Type.EOS)) {
            this.expr();
        }
    }

    /**
     * Prints a labeled message to standard output, used for tracing parser entry and exit.
     *
     * @param expr the message to print
     */
    private void println(String expr) {
        System.out.println(expr);
    }

    /**
     * Retrieves the next token from the lexical analyzer and updates the current token.
     */
    private void nextToken() {
        this.token = this.la.getNextToken();
    }

    /**
     * Prints information about the next token if no parsing error has occurred.
     * Displays the token type and lexeme for debugging.
     */
    private void printNextToken() {
        if (!this.error) {
            if (token.getType().equals(Token.Type.INT_LITERAL))
                System.out.printf("Next token is: %s%n", token.getLexeme());
            else
                System.out.printf("Next lexeme is: %s%n", token.getLexeme());
        }
    }
}
