import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuickSortTest {

    @Test
    void testQuickSort() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(1);
        list.add(4);
        list.add(1);
        list.add(5);

        QuickSort.quickSort(list, Comparator.naturalOrder());
        Integer[] expected = {1, 1, 3, 4, 5};
        Integer[] actual = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            actual[i] = list.get(i);
        }
        assertArrayEquals(expected, actual);
    }
}