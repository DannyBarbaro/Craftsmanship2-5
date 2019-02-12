package parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import static parser.TerminalSymbol.*;

public enum NonTerminalSymbol implements Symbol {
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    private static final Map<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>> productions = new HashMap<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>>() {
        {
            put(EXPRESSION, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(OPEN, SymbolSequence.build(TERM, EXPRESSION_TAIL));
            }});
            put(EXPRESSION_TAIL, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(PLUS, SymbolSequence.build(PLUS, TERM, EXPRESSION_TAIL));
                put(MINUS, SymbolSequence.build(MINUS, TERM, EXPRESSION_TAIL));
                put(null, SymbolSequence.EPSILON);
            }});
            put(TERM, new HashMap<TerminalSymbol, SymbolSequence>() {{
                //Arrays.asList(SymbolSequence.build(UNARY, TERM_TAIL))
            }});
            put(TERM_TAIL, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(TIMES, SymbolSequence.build(TIMES, UNARY, TERM_TAIL));
                put(DIVIDE, SymbolSequence.build(DIVIDE, UNARY, TERM_TAIL));
                put(null, SymbolSequence.EPSILON);
            }});
            put(UNARY, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(MINUS, SymbolSequence.build(MINUS, FACTOR));                
                //Arrays.asList(SymbolSequence.build(MINUS, FACTOR),
                //SymbolSequence.build(FACTOR)))
            }});
            put(FACTOR, new HashMap<TerminalSymbol, SymbolSequence>() {{
                //Arrays.asList(SymbolSequence.build(OPEN, EXPRESSION, CLOSE),
                //SymbolSequence.build(VARIABLE))
            }});
        }
    };

    private static Map<NonTerminalSymbol, List<SymbolSequence>> nonTermsTable = new HashMap<NonTerminalSymbol, List<SymbolSequence>>() {
        {
            put(EXPRESSION, Arrays.asList(SymbolSequence.build(TERM, EXPRESSION_TAIL)));
            put(EXPRESSION_TAIL, Arrays.asList(SymbolSequence.build(PLUS, TERM, EXPRESSION_TAIL),
                    SymbolSequence.build(MINUS, TERM, EXPRESSION_TAIL),
                    SymbolSequence.EPSILON));
            put(TERM, Arrays.asList(SymbolSequence.build(UNARY, TERM_TAIL)));
            put(TERM_TAIL, Arrays.asList(SymbolSequence.build(TIMES, UNARY, TERM_TAIL),
                    SymbolSequence.build(DIVIDE, UNARY, TERM_TAIL),
                    SymbolSequence.EPSILON));
            put(UNARY, Arrays.asList(SymbolSequence.build(MINUS, FACTOR),
                    SymbolSequence.build(FACTOR)));
            put(FACTOR, Arrays.asList(SymbolSequence.build(OPEN, EXPRESSION, CLOSE),
                    SymbolSequence.build(VARIABLE)));
        }
    };

    @Override
    public ParseState parse(List<Token> input) {
        Objects.requireNonNull(input, "Cannot match with a null input");
        Optional<ParseState> pState = nonTermsTable.get(this).stream()
            .map(ss -> ss.match(input))
            .filter(state -> !state.equals(ParseState.FAILURE))
            .findFirst();
        if (pState.isPresent()) {
            return pState.get();
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
