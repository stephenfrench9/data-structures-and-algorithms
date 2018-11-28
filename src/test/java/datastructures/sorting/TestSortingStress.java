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
    public void testPlaceholder() {
        IList<Integer> list = new DoubleLinkedList<Integer>();
        IList<Integer> list1 = new DoubleLinkedList<Integer>();

        int big = (int) Math.pow(10, 6);

        for(int i = 0; i < big; i++)
            list.add(i);

        for(int i = 10; i < big; i++)
            list1.add(i);

        IList<Integer> top = Searcher.topKSort(big - 10, list);

//        for(int ele : top)
//            System.out.println(ele);
//        System.out.println("................");
//        for(int ele : list1)
//            System.out.println(ele);

        assertIListsMatch(top, list1);

        assertTrue(true);
    }
}
