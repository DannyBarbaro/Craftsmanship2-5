package parser.src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import static parser.src.TerminalSymbol.*;

public enum NonTerminalSymbol implements Symbol {
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    private static final Map<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>> productions
            = new HashMap<NonTerminalSymbol, Map<TerminalSymbol, SymbolSequence>>() {
        {
            put(EXPRESSION, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(MINUS, SymbolSequence.build(TERM, EXPRESSION_TAIL));
                put(OPEN, SymbolSequence.build(TERM, EXPRESSION_TAIL));
                put(VARIABLE, SymbolSequence.build(TERM, EXPRESSION_TAIL));
            }});

            put(EXPRESSION_TAIL, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(PLUS, SymbolSequence.build(PLUS, TERM, EXPRESSION_TAIL));
                put(MINUS, SymbolSequence.build(MINUS, TERM, EXPRESSION_TAIL));
                put(CLOSE, SymbolSequence.EPSILON);
                put(null, SymbolSequence.EPSILON);
            }});

            put(TERM, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(MINUS, SymbolSequence.build(UNARY, TERM_TAIL));
                put(OPEN, SymbolSequence.build(UNARY, TERM_TAIL));
                put(VARIABLE, SymbolSequence.build(UNARY, TERM_TAIL));
            }});

            put(TERM_TAIL, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(TIMES, SymbolSequence.build(TIMES, UNARY, TERM_TAIL));
                put(DIVIDE, SymbolSequence.build(DIVIDE, UNARY, TERM_TAIL));
                put(PLUS, SymbolSequence.EPSILON);
                put(MINUS, SymbolSequence.EPSILON);
                put(CLOSE, SymbolSequence.EPSILON);
                put(null, SymbolSequence.EPSILON);
            }});

            put(UNARY, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(MINUS, SymbolSequence.build(MINUS, FACTOR));
                put(OPEN, SymbolSequence.build(FACTOR));
                put(VARIABLE, SymbolSequence.build(FACTOR));
            }});

            put(FACTOR, new HashMap<TerminalSymbol, SymbolSequence>() {{
                put(OPEN, SymbolSequence.build(OPEN, EXPRESSION, CLOSE));
                put(VARIABLE, SymbolSequence.build(VARIABLE));
            }});
        }
    };

    @Override
    public ParseState parse(List<Token> input) {
        Objects.requireNonNull(input, "Cannot match with a null input");
        TerminalSymbol tSymbol = input.isEmpty() ? null : input.get(0).getType();
        SymbolSequence sSequence = productions.get(this).get(tSymbol);
        return Objects.isNull(sSequence) ? ParseState.FAILURE : sSequence.match(input);
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
