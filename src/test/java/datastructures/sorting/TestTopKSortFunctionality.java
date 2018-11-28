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

    private IList<String> makeStrings2() {
        IList<String> red = new DoubleLinkedList<>();
        red.add("abe"); red.add("ben"); red.add("carey"); red.add("dan"); red.add("elle");
        red.add("frank"); red.add("gus"); red.add("hank"); red.add("ishmail"); red.add("janelle");
        red.add("zzza"); red.add("zzzb"); red.add("zzzc");
        red.add("lskdfjs"); red.add("kjhjh");
        red.add("iouweu"); red.add("mnsdf"); red.add("aksldf lkfjsd"); red.add("ioeryt"); red.add("touching");
        red.add("sugar"); red.add("thistle"); red.add("grass"); red.add("user"); red.add("poser");
        red.add("made in detroit"); red.add("made in detroit");
        red.add("zzzd"); red.add("zzze"); red.add("zzzf");
        return red;
    }

    private void assertIListsMatch(IList<Integer> actual, IList<Integer> expected) {
        assertEquals(actual.size(), expected.size());
        for(int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i), expected.get(i));
        }
    }

    private void assertStringIListsMatch(IList<String> actual, IList<String> expected) {
        assertEquals(actual.size(), expected.size());
        for(int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i), expected.get(i));
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
    public void testSimpleUsage2() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 200; i++) {
            list.add(i);
        }
        for (int i = 200; i > 0; i--) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(4, list);
        IList<Integer> expected = new DoubleLinkedList<>();
        expected.add(198);
        expected.add(199);
        expected.add(199);
        expected.add(200);
        assertEquals(4, top.size());
        assertIListsMatch(top, expected);
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
    public void testStrings2() {
        IList<String> list = makeStrings2();
        IList<String> actual = Searcher.topKSort(6, list);
        IList<String> expected = new DoubleLinkedList<String>();
        expected.add("zzza"); expected.add("zzzb"); expected.add("zzzc");
        expected.add("zzzd"); expected.add("zzze"); expected.add("zzzf");
        assertStringIListsMatch(actual, expected);
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
