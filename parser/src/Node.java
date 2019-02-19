package parser.src;

import java.util.List;
import java.util.Optional;

public interface Node {

    // returns a list representation of the subtree rooted at the given node
    List<Token> toList();

    // returns a copy of this node children (or null in the case of leaves)
    List<Node> getChildren();

    //returns true if this node is an internal node with a least a child or if this node is a leaf
    boolean isFruitful();

    //eturns true if the nodeis a leaf corresponding to an operator, and false otherwise
    boolean isOperator();
    
    //returns true if the node’s first child is an operator, and false otherwise
    boolean isStartedByOperator();

    //returns the first child of this node, or empty if the node is either a leaf or unfruitful
    Optional<Node> firstChild();

    //returns true if this node’s only child is a leaf, and false otherwise.
    boolean isSingleLeafParent();
}