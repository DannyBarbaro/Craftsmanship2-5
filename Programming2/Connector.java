package Programming2;

import java.util.HashMap;
// Author: Daniel Barbaro
// Connectors the operators that are used on variables
public final class Connector extends AbstractToken {

    private final TerminalSymbol type;
    private static Cache<TerminalSymbol, Connector> cache = new Cache<>();

    private static final HashMap<TerminalSymbol, String> connectorMap = new HashMap<TerminalSymbol, String>() {{
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

    public static final Connector build(TerminalSymbol type) {
        if (connectorMap.keySet().contains(type)) {
            return cache.get(type, Connector::new);
        } else {
            throw new IllegalArgumentException("The given type is not accepted as a connector");
        }
    }
}