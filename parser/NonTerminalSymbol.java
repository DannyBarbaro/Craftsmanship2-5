package parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static parser.TerminalSymbol.*;

public enum NonTerminalSymbol implements Symbol {
    EXPRESSION, EXPRESSION_TAIL, TERM, TERM_TAIL, UNARY, FACTOR;

    private HashMap<NonTerminalSymbol, List<SymbolSequence>> nonTermsTable =
            new HashMap<NonTerminalSymbol, List<SymbolSequence>>(){{
            nonTermsTable.put(EXPRESSION, Arrays.asList(
                    SymbolSequence.build(TERM, EXPRESSION_TAIL)));
            nonTermsTable.put(EXPRESSION_TAIL, Arrays.asList(
                    SymbolSequence.build(PLUS, TERM, EXPRESSION_TAIL),
                    SymbolSequence.build(MINUS, TERM, EXPRESSION_TAIL),
                    SymbolSequence.EPSILON));
            nonTermsTable.put(TERM, Arrays.asList(
                    SymbolSequence.build(UNARY, TERM_TAIL)));
            nonTermsTable.put(TERM_TAIL, Arrays.asList(
                    SymbolSequence.build(TIMES, UNARY, TERM_TAIL),
                    SymbolSequence.build(DIVIDE, UNARY, TERM_TAIL),
                    SymbolSequence.EPSILON));
            nonTermsTable.put(UNARY, Arrays.asList(
                    SymbolSequence.build(MINUS, FACTOR),
                    SymbolSequence.build(FACTOR)));
            nonTermsTable.put(FACTOR, Arrays.asList(
                    SymbolSequence.build(OPEN, EXPRESSION, CLOSE),
                    SymbolSequence.build(VARIABLE)));
    }};

    @Override
    public ParseState parse(List<Token> input) {
        if (input.isEmpty()) {
            return ParseState.FAILURE;
        }
        for (Map.Entry<NonTerminalSymbol, List<SymbolSequence>> entry : nonTermsTable.entrySet()) {
            for (SymbolSequence sequence : entry.getValue()) {
                if (!sequence.match(input).equals(ParseState.FAILURE)) {

                }
            }
        }
    }
}
