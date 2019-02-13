package parser.src;

// Tokens are either variables or operators in an expression
public interface Token {

    TerminalSymbol getType();

    // return whether the argument is equal to getType()
    boolean matches(TerminalSymbol type);
}