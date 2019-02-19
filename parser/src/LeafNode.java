package parser.src;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class LeafNode implements Node {

    private final Token token;

    public Token getToken() {
        return token;
    }

    private LeafNode(Token token) {
        this.token = token;
    }

    public static LeafNode build(Token token) {
        Objects.requireNonNull(token, "Cannot build with null token");
        return new LeafNode(token);
    }

    @Override
    public List<Token> toList() {
        return Collections.singletonList(this.token);
    }

    @Override
    public String toString() {
        return this.token.toString();
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public boolean isFruitful() {
        return true;
    }

    @Override
    public boolean isOperator() {
        return this.getToken().isOperator();
    }

    @Override
    public boolean isStartedByOperator() {
        return false;
    }

    @Override
    public Optional<Node> firstChild() {
        return Optional.empty();
    }

    @Override
    public boolean isSingleLeafParent() {
        return false;
    }
}