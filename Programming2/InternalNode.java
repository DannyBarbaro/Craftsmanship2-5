package Programming2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class InternalNode implements Node {

    private final List<Node> children;

    private final List<Token> childConcat;

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
        if(childConcat == null) {
            List<Token> list = new LinkedList<>();
            for (Node child : children) {
                list.addAll(child.toList());
            }
            childConcat = list;
        }
        return new ArrayList<>(childConcat);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
