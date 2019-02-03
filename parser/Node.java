package parser;

import java.util.List;

// Author: Jacob Rich
public interface Node {

    // returns a list representation of the subtree rooted at the given node
    List<Token> toList();

}