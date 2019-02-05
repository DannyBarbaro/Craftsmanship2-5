package parser;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public final class SymbolSequence {

    private final List<Symbol> production;

    static final SymbolSequence EPSILON = build();

    private SymbolSequence(List<Symbol> production) {
        this.production = production;
    }

    public static final SymbolSequence build(List<Symbol> production) {
        if (!Objects.isNull(production)) {
            return new SymbolSequence(production);
        } else {
            throw new NullPointerException("Cannot create a symbol sequence with null production");
        }
    }

    public static final SymbolSequence build(Symbol... symbols) {
        if (!Objects.isNull(symbols)) {
            return new SymbolSequence(Arrays.asList(symbols));
        } else {
            throw new NullPointerException("Cannot create a symbol sequence with null production");
        }
    }

    @Override
    public String toString() {
        return production.toString();
    }

    public ParseState match(List<Token> input) {
        if (Objects.isNull(input)) {
            throw new NullPointerException("Cannot match with a null input");
        }
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