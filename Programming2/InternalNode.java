package Programming2;

import java.util.ArrayList;
import java.util.List;

public final class InternalNode implements Node {

    private final List<Node> children;

    private List<Token> subTreeList;

    private String subTreeString;

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
        if (subTreeList != null) {
            return new ArrayList<>(subTreeList);
        }
        else {
            subTreeList = new ArrayList<>();
            for (Node child : children) {
                subTreeList.addAll(child.toList());
            }
            return new ArrayList<>(subTreeList);
        }
    }

    @Override
    public String toString() {
        if (subTreeString != null) {
            return subTreeString;
        }
        else {
            for (Node child : children) {
                subTreeString += child.toString();
            }
            return subTreeString;
        }
    }
}
