package parser.src;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public Optional<Node> firstChild() {
        return this.isFruitful() ? Optional.of(this.children.get(0)) : Optional.empty();
    }

    @Override
    public boolean isSingleLeafParent() {
        return this.children.size() == 1 && (this.children.get(0) instanceof LeafNode);
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
            Builder b = new Builder();
            List<Node> filteredChildren = this.children.stream()
                .filter(c -> c.isFruitful())    // get the fruitful nodes
                .collect(Collectors.toList());  // generate a list
            if(filteredChildren.size() == 1 && filteredChildren.get(0) instanceof InternalNode) {
                filteredChildren = filteredChildren.get(0).getChildren();
            }
            filteredChildren.forEach(c -> childMapper(c, b));
            return b;
            }

        //helper method to eliminate the lone child list
        private void childMapper(Node child, Builder b) {
            if(Objects.nonNull(child.getChildren()) && child.getChildren().size() == 1) {
                b.children.addAll(child.getChildren());
            } else {
                b.addChild(child);
            }
        }

        public InternalNode build() {
            return InternalNode.build(this.children);
        }
    }
}