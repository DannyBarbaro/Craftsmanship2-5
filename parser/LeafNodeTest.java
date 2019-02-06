package parser;

import org.junit.Test;
import static org.junit.Assert.*;

public class LeafNodeTest {

    @Test
    public void testToString() {
        Variable v = Variable.build("x");
        LeafNode l = LeafNode.build(v);
        assertEquals(l.toString(), v.toString());
    }

}