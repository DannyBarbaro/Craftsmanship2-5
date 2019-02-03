package parser;

// Author: Daniel Barbaro
// Variables represent objects that operators are used on
public final class Variable extends AbstractToken {

    private final String representation;
    private static Cache<String, Variable> cache = new Cache<>();

    private Variable(String representation) {
        this.representation = representation;
    }

    public final String getRepresentation() {
        return representation;
    }

    public TerminalSymbol getType() {
        return TerminalSymbol.VARIABLE;
    }

    @Override
    public String toString() {
        return representation;
    }

    public static final Variable build(String representation) {
        return cache.get(representation, Variable::new);
    }
}