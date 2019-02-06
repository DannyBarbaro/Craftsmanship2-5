package parser;

// Author: Jacob Rich

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
}