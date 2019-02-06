package parser;

import java.util.*;

public final class SymbolSequence {

    private final List<Symbol> production;

    static final SymbolSequence EPSILON = build();

    private SymbolSequence(List<Symbol> production) {
        this.production = Collections.unmodifiableList(production);
    }

    public static final SymbolSequence build(List<Symbol> production) {
        Objects.requireNonNull(production, "Cannot create a symbol sequence with null production");
        return new SymbolSequence(production);
    }

    public static final SymbolSequence build(Symbol... symbols) {
        Objects.requireNonNull(symbols, "Cannot create a symbol sequence with null symbols");
        return new SymbolSequence(Arrays.asList(symbols));
    }

    @Override
    public String toString() {
        return production.toString();
    }

    public ParseState match(List<Token> input) {
        Objects.requireNonNull(input, "Cannot match with a null input");
        List<Token> remainder = input;
        List<Node> children = new LinkedList<>();
        for(Symbol symbol : production) {
            ParseState pState = symbol.parse(input);
            if (!pState.getSuccess()) {
                return ParseState.FAILURE;
            } else {
                children.add(pState.getNode());
                remainder = pState.getRemainder();
            }
        }
        return ParseState.build(true, InternalNode.build(children), remainder);
    }

}