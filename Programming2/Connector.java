package Programming2;

// Author: Daniel Barbaro
// Connectors the operators that are used on variables
public final class Connector extends AbstractToken {

    private final TerminalSymbol type;
    private static Cache<TerminalSymbol, Connector> cache = new Cache<>();

    private Connector(TerminalSymbol type) {
        this.type = type;
    }

    public TerminalSymbol getType() {
        return this.type;
    }

    @Override
    public String toString() {
        switch (this.type) {
        case PLUS:
            return "+";
        case MINUS:
            return "-";
        case TIMES:
            return "*";
        case DIVIDE:
            return "/";
        case OPEN:
            return "(";
        case CLOSE:
            return ")";
        default:
            return "unknown type";
        }
    }

    public static final Connector build(TerminalSymbol type) throws Exception {
        if (type == TerminalSymbol.PLUS || type == TerminalSymbol.MINUS || type == TerminalSymbol.TIMES
                || type == TerminalSymbol.DIVIDE || type == TerminalSymbol.OPEN || type == TerminalSymbol.CLOSE) {
            return cache.get(type, (t) -> new Connector(t));
        } else {
            throw new IllegalArgumentException("The given type is not accepted as a connector");
        }
    }
}