package Programming2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InternalNodeTest {

    @Test
    public void testToList() {
        try {
            Variable v = Variable.build("x");
            LeafNode l = LeafNode.build(v);
            List<Node> list = new ArrayList<>();
            list.add(l);
            InternalNode i = InternalNode.build(list);
            assertEquals(l.toList(), i.toList());
        }
        catch (Exception ex) {}
    }

    @Test
    public void testToString() {
        try {
            Variable v = Variable.build("x");
            Connector c = Connector.build(TerminalSymbol.CLOSE);
            LeafNode lv = LeafNode.build(v);
            LeafNode lc = LeafNode.build(c);
            List<Node> list = new ArrayList<>();
            list.add(lv);
            list.add(lc);
            InternalNode i = InternalNode.build(list);
            assertEquals("[x,),]", i.toString());
        }
        catch (Exception ex) {}
    }

}