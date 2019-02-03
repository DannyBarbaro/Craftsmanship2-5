package parser;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LeafNodeTest {

    @Test
    public void testToString() {
        try {
            Variable v = Variable.build("x");
            LeafNode l = LeafNode.build(v);
            assertEquals(l.toString(), v.toString());
        }
        catch (Exception ex) {}
    }

}