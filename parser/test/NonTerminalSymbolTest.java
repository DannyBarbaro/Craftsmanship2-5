package parser.test;

import org.junit.Test;
import parser.src.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

        LeafNode bNode = LeafNode.build(Variable.build("b"));
        LeafNode divNode = LeafNode.build(Connector.build(TerminalSymbol.DIVIDE));
        LeafNode cNode = LeafNode.build(Variable.build("c"));
        tempChildren = new LinkedList<>();
        tempChildren.add(bNode);
        tempChildren.add(divNode);
        tempChildren.add(cNode);
        InternalNode level1 = InternalNode.build(tempChildren);

        LeafNode aNode = LeafNode.build(Variable.build("a"));
        LeafNode plusNode = LeafNode.build(Connector.build(TerminalSymbol.PLUS));
        tempChildren = new LinkedList<>();
        tempChildren.add(aNode);
        tempChildren.add(plusNode);
        tempChildren.add(level1);
        InternalNode root = InternalNode.build(tempChildren);

        Optional<Node> r = NonTerminalSymbol.parseInput(input);
        System.out.println(r.get().toString());
        assertEquals(root.toString(), r.get().toString());
    }
}