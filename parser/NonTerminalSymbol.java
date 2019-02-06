package parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static parser.TerminalSymbol.*;

public enum NonTerminalSymbol implements Symbol {
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    private HashMap<NonTerminalSymbol, List<SymbolSequence>> nonTermsTable = new HashMap<NonTerminalSymbol, List<SymbolSequence>>() {
        {
            nonTermsTable.put(EXPRESSION, Arrays.asList(SymbolSequence.build(TERM, EXPRESSION_TAIL)));
            nonTermsTable.put(EXPRESSION_TAIL, Arrays.asList(SymbolSequence.build(PLUS, TERM, EXPRESSION_TAIL),
                    SymbolSequence.build(MINUS, TERM, EXPRESSION_TAIL), SymbolSequence.EPSILON));
            nonTermsTable.put(TERM, Arrays.asList(SymbolSequence.build(UNARY, TERM_TAIL)));
            nonTermsTable.put(TERM_TAIL, Arrays.asList(SymbolSequence.build(TIMES, UNARY, TERM_TAIL),
                    SymbolSequence.build(DIVIDE, UNARY, TERM_TAIL), SymbolSequence.EPSILON));
            nonTermsTable.put(UNARY, Arrays.asList(SymbolSequence.build(MINUS, FACTOR), SymbolSequence.build(FACTOR)));
            nonTermsTable.put(FACTOR,
                    Arrays.asList(SymbolSequence.build(OPEN, EXPRESSION, CLOSE), SymbolSequence.build(VARIABLE)));
        }
    };

    @Override
    public ParseState parse(List<Token> input) {
        Objects.requireNonNull(input, "Cannot match with a null input");
        for (NonTerminalSymbol nonTerminal : nonTermsTable.keySet()) {
            Optional<ParseState> pState = nonTermsTable.get(nonTerminal).stream()
                .map(ss -> ss.match(input))
                .filter(state -> state != ParseState.FAILURE)
                .findFirst();
            if (pState.isPresent()) {
                return pState.get();
            }
        }
        return ParseState.FAILURE;
    }

    public static final Optional<Node> parseInput(List<Token> input) {
        Objects.requireNonNull(input, "Cannot match with a null input");
        Optional<Node> output = Optional.empty();
        ParseState pState = EXPRESSION.parse(input);
        if (pState.getSuccess() && pState.hasNoRemainder()) {
            output = Optional.of(pState.getNode());
        }
        return output;
    }

    public static void main(String[] args) {
        List<Token> input = new LinkedList<>();
        input.add(Variable.build("a"));
        input.add(Connector.build(TerminalSymbol.PLUS));
        input.add(Variable.build("b"));
        input.add(Connector.build(TerminalSymbol.DIVIDE));
        input.add(Variable.build("c"));
        System.out.println(parseInput(input));
        
    }
}
