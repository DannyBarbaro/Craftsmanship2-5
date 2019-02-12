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

    public boolean isFruitful(){
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
        if (Objects.nonNull(subTreeString)) {
            return subTreeString;
        }
        else {
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

        public Builder() {
             this.children = new ArrayList<>();;
        }
        public boolean addChild(Node node) {
            return this.children.add(node);
        }

        // TODO: fix simplify
        public Builder simplify() {
            children = children.stream().filter(child -> child.isFruitful()).collect(Collectors.toList());
            if(children.size() == 1) {
                //System.out.println(children.get(0));
                //children = children.get(0).getChildren();
            }
            return this;
        }

        public InternalNode build() {
            return InternalNode.build(this.children);
        }
    }
}
