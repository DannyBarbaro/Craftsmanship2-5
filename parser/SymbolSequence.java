package parser;

import java.util.*;

public final class SymbolSequence {

    private final List<Symbol> production;

    static final SymbolSequence EPSILON = build();

    private SymbolSequence(List<Symbol> production) {
        this.production = Collections.unmodifiableList(new ArrayList<>(production));
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
        InternalNode.Builder builder = new InternalNode.Builder();
        for(Symbol symbol : production) {
            ParseState pState = symbol.parse(remainder);
            if (!pState.getSuccess()) {
                return ParseState.FAILURE;
            } else {
                builder.addChild(pState.getNode());
                remainder = pState.getRemainder();
            }
        }
        return ParseState.build(builder.simplify().build(), remainder);
    }
}