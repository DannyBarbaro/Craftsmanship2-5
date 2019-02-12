package parser;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class InternalNode implements Node {

    private final List<Node> children;

    private List<Token> subTreeList;

    private String subTreeString;

    public List<Node> getChildren() {
        return new ArrayList<>(this.children);
    }

    public boolean isFruitful() {
        return !this.children.isEmpty();
    }

    private InternalNode(List<Node> children) {
        this.children = Collections.unmodifiableList(children);
    }

    public static InternalNode build(List<Node> children) {
        Objects.requireNonNull(children, "Cannot build with null children");
        return new InternalNode(children);
    }

    @Override
    public List<Token> toList() {
        if (Objects.nonNull(subTreeList)) {
            return new ArrayList<>(subTreeList);
        } else {
            subTreeList = new ArrayList<>();
            for (Node child : children) {
                subTreeList.addAll(child.toList());
            }
            return new ArrayList<>(subTreeList);
        }
    }

    @Override
    public String toString() {
        if (Objects.nonNull(subTreeString)) {
            return subTreeString;
        } else {
            StringJoiner joiner = new StringJoiner(",", "[", "]");
            for (Node child : children) {
                joiner.add(child.toString());
            }
            subTreeString = joiner.toString();
            return subTreeString;
        }
    }

    public static class Builder {

        private List<Node> children = new ArrayList<>();

        public boolean addChild(Node node) {
            return this.children.add(node);
        }

        public Builder simplify() {
            //stream and filter fruitful, then either get the lone child or the child with multiple children
            children = children.stream()
                .filter(c -> c.isFruitful())
                .map(c -> (Objects.nonNull(c.getChildren()) && c.getChildren().size() == 1) ? c.getChildren().get(0) : c)
                .collect(Collectors.toList());
            return this;
        }

        public InternalNode build() {
            return InternalNode.build(this.children);
        }
    }
}