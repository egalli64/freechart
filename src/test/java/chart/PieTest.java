package chart;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieTest {
    @Test
    public void testCreate() {
        assertTrue(Pie.createSimple());
    }
}
