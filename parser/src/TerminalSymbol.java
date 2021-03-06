package parser.src;

import java.util.List;

// An enumeration of the terminal symbol types
public enum TerminalSymbol implements Symbol {
    VARIABLE, PLUS,  MINUS,  TIMES,  DIVIDE, OPEN, CLOSE;

    public ParseState parse(List<Token> input) {
        if(!input.isEmpty() && input.get(0).matches(this)) {
            return ParseState.build(LeafNode.build(input.get(0)), input.subList(1, input.size()));
        }
        return ParseState.FAILURE;
    }
}