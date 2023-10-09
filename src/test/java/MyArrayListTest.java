import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyArrayListTest {

    private MyArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyArrayList<>();
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(1);
        list.add(5);
    }

    @Test
    void testAddAndGet() {
        assertEquals(3, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(4, list.get(2));
        assertEquals(1, list.get(3));
        assertEquals(5, list.get(4));
    }

    @Test
    void testSize() {
        assertEquals(5, list.size());
    }

    @Test
    void testSet() {
        list.set(1, 2);
        assertEquals(2, list.get(1));
    }
}