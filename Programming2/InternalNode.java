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

    public static InternalNode build(List<Node> children) {
        if (children != null) {
            return new InternalNode(children);
        }
        else {
            throw new NullPointerException("Cannot build with null children");
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
            subTreeString = "[";
            for (Node child : children) {
                subTreeString += child.toString();
                subTreeString += ",";
            }
            subTreeString += "]";
            return subTreeString;
        }
    }
}
