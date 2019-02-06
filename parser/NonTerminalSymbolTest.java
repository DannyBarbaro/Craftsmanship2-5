package parser;

import org.junit.Test;

import javax.lang.model.element.VariableElement;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class NonTerminalSymbolTest {

    @Test
    public void parseInputTest() {
        List<Token> input = new LinkedList<>();
        input.add(Variable.build("a"));
        input.add(Connector.build(TerminalSymbol.PLUS));
        input.add(Variable.build("b"));
        input.add(Connector.build(TerminalSymbol.DIVIDE));
        input.add(Variable.build("c"));

        List<Node> tempChildren;

        LeafNode divNode = LeafNode.build(Connector.build(TerminalSymbol.DIVIDE));
        LeafNode cNode = LeafNode.build(Variable.build("c"));
        tempChildren = new LinkedList<>();
        tempChildren.add(divNode);
        tempChildren.add(cNode);
        InternalNode level3 = InternalNode.build(tempChildren);

        LeafNode bNode = LeafNode.build(Variable.build("b"));
        tempChildren = new LinkedList<>();
        tempChildren.add(bNode);
        tempChildren.add(level3);
        InternalNode level2 = InternalNode.build(tempChildren);

        LeafNode plusNode = LeafNode.build(Connector.build(TerminalSymbol.PLUS));
        tempChildren = new LinkedList<>();
        tempChildren.add(plusNode);
        tempChildren.add(level2);
        InternalNode level1b = InternalNode.build(tempChildren);

        LeafNode aNode = LeafNode.build(Variable.build("a"));
        tempChildren = new LinkedList<>();
        tempChildren.add(aNode);
        InternalNode level1a = InternalNode.build(tempChildren);

        tempChildren = new LinkedList<>();
        tempChildren.add(level1a);
        tempChildren.add(level1b);
        InternalNode root = InternalNode.build(tempChildren);

        assertEquals(root, NonTerminalSymbol.parseInput(input));
    }
}