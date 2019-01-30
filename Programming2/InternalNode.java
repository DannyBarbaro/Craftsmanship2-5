package Programming2;

import java.util.ArrayList;
import java.util.List;

public final class InternalNode implements Node {

    private final List<Node> children;

    public List<Node> getChildren() {
        return new ArrayList<>(this.children);
    }

    private InternalNode(List<Node> children) {
        this.children = new ArrayList<>(children);
    }

    public InternalNode build(List<Node> children) throws NullPointerException {
        if (children != null) {
            return new InternalNode(children);
        }
        else {
            throw new NullPointerException();
        }
    }

    @Override
    public List<Token> toList() {
        return null;
    }
}
