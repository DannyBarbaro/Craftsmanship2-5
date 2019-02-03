package parser;

// Author: Daniel Barbaro

public abstract class AbstractToken implements Token {

    public final boolean matches(TerminalSymbol type) {
        return type == this.getType();
    }
}