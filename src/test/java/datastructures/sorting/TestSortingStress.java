package datastructures.sorting;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.BaseTest;
import misc.Searcher;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    @Test(timeout=10*SECOND)
    public void tenThousandIntegers() {
        IList<Integer> raw = new DoubleLinkedList<Integer>();
        IList<Integer> expected = new DoubleLinkedList<Integer>();

        int big = (int) Math.pow(10, 4);
        for(int i = 0; i < big; i++)
            raw.add(i);
        for(int i = 9990; i < big; i++)
            expected.add(i);

        IList<Integer> filtered = Searcher.topKSort(big - 9990, raw);
        assertIListsMatch(filtered, expected);
    }

    @Test(timeout=10*SECOND)
    public void largeK() {
        IList<Integer> raw = new DoubleLinkedList<Integer>();
        IList<Integer> expected = new DoubleLinkedList<Integer>();

        int big = (int) Math.pow(10, 4);
        for(int i = 0; i < big; i++)
            raw.add(i);
        for(int i = 20; i < big; i++)
            expected.add(i);

        IList<Integer> filtered = Searcher.topKSort(big - 20, raw);
        assertIListsMatch(filtered, expected);
    }

    @Test(timeout=10*SECOND)
    public void middleOfList() {
        IList<Integer> raw = new DoubleLinkedList<Integer>();
        IList<Integer> expected = new DoubleLinkedList<Integer>();

        int big = (int) Math.pow(10, 4);
        for(int i = 0; i < big; i++)
            raw.add(i);
        for(int i = big - 1; i >= 0; i--)
            raw.add(i);
        for(int i = big-10; i < big; i++) {
            expected.add(i);
            expected.add(i);
        }

        IList<Integer> filtered = Searcher.topKSort(20, raw);
        assertIListsMatch(filtered, expected);
    }
}
