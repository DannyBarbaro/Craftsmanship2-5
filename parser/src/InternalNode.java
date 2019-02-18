package parser.src;

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

    @Override
    public List<Node> getChildren() {
        return new ArrayList<>(this.children);
    }

    @Override
    public boolean isFruitful() {
        return !this.children.isEmpty();
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public boolean isStartedByOperator() {
       return !this.children.isEmpty() && this.children.get(0).isOperator();
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
                .map(this::childMapper)
                .collect(Collectors.toList());
            return this;
        }

        //helper method to eliminate the lone child list
        private Node childMapper(Node child) {
            if(Objects.nonNull(child.getChildren()) && child.getChildren().size() == 1) {
                return child.getChildren().get(0);
            } else {
                return child;
            }
        }

        public InternalNode build() {
            return InternalNode.build(this.children);
        }
    }
}