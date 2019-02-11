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
            ParseState pState = symbol.parse(remainder);
            if (!pState.getSuccess()) {
                return ParseState.FAILURE;
            } else {
                children.add(pState.getNode());
                remainder = pState.getRemainder();
            }
        }
        return ParseState.build(InternalNode.build(children), remainder);
    }

    public static void main(String[] args) {
        List<Token> input = new LinkedList<>();
        input.add(Variable.build("a"));
        input.add(Connector.build(TerminalSymbol.PLUS));
        input.add(Variable.build("b"));
        input.add(Connector.build(TerminalSymbol.DIVIDE));
        input.add(Variable.build("c"));

        Optional<Node> r = NonTerminalSymbol.parseInput(input);
        System.out.print(r.get());
    }
}