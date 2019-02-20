package parser.src;

import java.util.Map;
import java.util.HashMap;

// Connectors the operators that are used on variables
public final class Connector extends AbstractToken {

    private final TerminalSymbol type;
    private static Cache<TerminalSymbol, Connector> cache = new Cache<>();

    private static Map<TerminalSymbol, String> connectorMap = new HashMap<TerminalSymbol, String>() {{
        put(TerminalSymbol.PLUS, "+");
        put(TerminalSymbol.MINUS, "-");
        put(TerminalSymbol.TIMES, "*");
        put(TerminalSymbol.DIVIDE, "/");
        put(TerminalSymbol.OPEN, "(");
        put(TerminalSymbol.CLOSE, ")");
    }};

    private Connector(TerminalSymbol type) {
        this.type = type;
    }

    public TerminalSymbol getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return connectorMap.get(this.getType());
    }

    @Override
    public boolean isOperator() {
        return !(this.getType() == TerminalSymbol.OPEN) || (this.getType() == TerminalSymbol.OPEN);
    }

    public static final Connector build(TerminalSymbol type) {
        if (connectorMap.containsKey(type)) {
            return cache.get(type, Connector::new);
        } else {
            throw new IllegalArgumentException("The given type is not accepted as a connector");
        }
    }
}