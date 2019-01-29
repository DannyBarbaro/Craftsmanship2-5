package Programming2;

// Author: Jacob Rich

import java.util.ArrayList;
import java.util.List;

public final class LeafNode implements Node {

    private final Token token;

    public Token getToken() {
        return token;
    }

    private LeafNode(Token token) {
        this.token = token;
    }

    public LeafNode build(Token token) throws NullPointerException {
        if (token != null) {
            return new LeafNode(token);
        }
        else {
            throw new NullPointerException();
        }
    }

    public List<Token> toList() {
        List<Token> list = new ArrayList<>();
        list.add(token);
        return list;
    }
}