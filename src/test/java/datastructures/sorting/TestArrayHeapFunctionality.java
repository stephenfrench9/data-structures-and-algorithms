package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {

    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    protected <T> void assertListMatches(Comparable<T>[] expected, Comparable<T>[] actual) {
        for (int i = 0; i < actual.length; i++) {
            try {
                assertEquals("Item at index " + i + " does not match", expected[i], actual[i]);
            } catch (Exception ex) {
                String errorMessage = String.format(
                        "Got %s when getting item at index %d (expected '%s')",
                        ex.getClass().getSimpleName(),
                        i,
                        expected[i]);
                throw new AssertionError(errorMessage, ex);
            }
        }
    }


    @Test(timeout=SECOND)
    public void basicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
    }

    @Test(timeout=SECOND)
    public void basicInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for(int i = 0; i < 5; i++) {
            heap.insert(i);
        }

        assertListMatches(heap.array(), new Integer[] {0,1,2,3,4});

    }

    @Test(timeout=SECOND)
    public void percolateUp() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for(int i = 0; i < 5; i++) {
            heap.insert(i);
        }
        heap.insert(-1);
        assertListMatches(heap.array(), new Integer[] {-1,0,2,3,4,1});
    }

    @Test(timeout=SECOND)
    public void peakMin() {
        IPriorityQueue<String> heap = makeInstance();
        heap.insert("frank");
        heap.insert("adam");
        heap.insert("bob");
        heap.insert("lucie");
        heap.insert("zach");
        heap.insert("abbey");
        assertListMatches(heap.array(), new String[] {"abbey", "adam", "bob", "lucie", "zach", "frank"});
        assertEquals("abbey", heap.peekMin());
    }

    @Test(timeout=SECOND)
    public void removeMin() {
        IPriorityQueue<String> heap = makeInstance();
        heap.insert("frank");
        heap.insert("adam");
        heap.insert("bob");
        heap.insert("lucie");
        heap.insert("zach");
        heap.insert("abbey");
        assertListMatches(heap.array(), new String[] {"abbey", "adam", "bob", "lucie", "zach", "frank"});

        String min = heap.removeMin();
        assertListMatches(heap.array(), new String[] {"adam", "frank", "bob", "lucie", "zach"});
        min = heap.removeMin();
        assertListMatches(heap.array(), new String[] {"bob", "frank", "zach", "lucie"});
        min = heap.removeMin();
        assertListMatches(heap.array(), new String[] {"frank", "lucie", "zach"});
        min = heap.removeMin();
        assertListMatches(heap.array(), new String[] {"lucie", "zach"});
        min = heap.removeMin();
        assertListMatches(heap.array(), new String[] {"zach"});
        min = heap.removeMin();
        assertListMatches(heap.array(), new String[] {});
        try {
            min = heap.removeMin();
            fail("an exception should have been thrown");
        } catch( EmptyContainerException e ) {
            //good, we tried to remove min from an empty list, and we got an exception
        }


    }
}
