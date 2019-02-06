package parser;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static parser.TerminalSymbol.*;

public enum NonTerminalSymbol implements Symbol {
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    private static Map<NonTerminalSymbol, List<SymbolSequence>> nonTermsTable = new LinkedHashMap<NonTerminalSymbol, List<SymbolSequence>>() {
        {
            put(EXPRESSION, Arrays.asList(SymbolSequence.build(TERM, EXPRESSION_TAIL)));
            put(EXPRESSION_TAIL, Arrays.asList(SymbolSequence.build(PLUS, TERM, EXPRESSION_TAIL),
                    SymbolSequence.build(MINUS, TERM, EXPRESSION_TAIL),
                    SymbolSequence.EPSILON));
            put(TERM, Arrays.asList(SymbolSequence.build(UNARY, TERM_TAIL)));
            put(TERM_TAIL, Arrays.asList(SymbolSequence.build(TIMES, UNARY, TERM_TAIL),
                    SymbolSequence.build(DIVIDE, UNARY, TERM_TAIL),
                    SymbolSequence.EPSILON));
            put(UNARY, Arrays.asList(
                    SymbolSequence.build(MINUS, FACTOR),
                    SymbolSequence.build(FACTOR)));
            put(FACTOR, Arrays.asList(SymbolSequence.build(OPEN, EXPRESSION, CLOSE),
                    SymbolSequence.build(VARIABLE)));
        }
    };

    @Override
    public ParseState parse(List<Token> input) {
        Objects.requireNonNull(input, "Cannot match with a null input");
        System.out.println("Trying to parse: " + this.toString() + " with " + input.toString());
        for (NonTerminalSymbol nonTerminal : nonTermsTable.keySet()) {
            Optional<ParseState> pState = nonTermsTable.get(nonTerminal).stream()
                .map(ss -> ss.match(input))
                .filter(state -> !state.equals(ParseState.FAILURE))
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
}
