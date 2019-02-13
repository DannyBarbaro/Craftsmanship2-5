package parser.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ParseState {

    public final static ParseState FAILURE = new ParseState(false, null, null);

    private final boolean success;
    private final Node node;
    private final List<Token> remainder;

    private ParseState(boolean success, Node node, List<Token> remainder) {
        this.success = success;
        this.node = node;
        this.remainder = remainder;
    }

    public static final ParseState build(Node node, List<Token> remainder) {
        Objects.requireNonNull(node, "Cannot create a parse state with null node");
        Objects.requireNonNull(remainder, "Cannot create a parse state with null remainder");
        return new ParseState(true, node, remainder);
    }

    public boolean getSuccess() {
        return success;
    }

    public Node getNode() {
        return node;
    }

    public List<Token> getRemainder() {
        return new ArrayList<Token>(remainder);
    }

    public final boolean hasNoRemainder() {
        return (Objects.isNull(remainder) || remainder.size() == 0);
    }
}