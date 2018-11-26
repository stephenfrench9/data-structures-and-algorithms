package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {

    private IList<String> makeStrings() {
        IList<String> red = new DoubleLinkedList<>();
        red.add("abe"); red.add("ben"); red.add("carey"); red.add("dan"); red.add("elle");
        red.add("frank"); red.add("gus"); red.add("hank"); red.add("ishmail"); red.add("janelle");

        return red;
    }

    private void assertIListsMatch(IList<Integer> one, IList<Integer> two) {
        for(int i = 0; i < one.size(); i++) {
            assertEquals(one.get(i), two.get(i));
        }
    }

    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }

    @Test(timeout=SECOND)
    public void testStrings() {
        IList<String> list = makeStrings();

        IList<String> top = Searcher.topKSort(5, list);

        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(list.get(i+5), top.get(i));
        }
    }

    @Test(timeout=SECOND)
    public void smallList1() {
        IList<Integer> list = new DoubleLinkedList<Integer>();
        list.add(0); list.add(1); list.add(2);

        IList<Integer> top = Searcher.topKSort(5, list);

        assertIListsMatch(list, top);
    }

    @Test(timeout=SECOND)
    public void smallList2() {
        IList<Integer> list = new DoubleLinkedList<Integer>();
        list.add(2); list.add(0); list.add(1);

        IList<Integer> answer = new DoubleLinkedList<Integer>();
        answer.add(0); answer.add(1); answer.add(2);

        IList<Integer> top = Searcher.topKSort(5, list);

        assertIListsMatch(answer, top);
    }

}
