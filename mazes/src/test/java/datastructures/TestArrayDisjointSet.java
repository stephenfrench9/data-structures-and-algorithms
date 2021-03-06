package datastructures;

import datastructures.concrete.ArrayDisjointSet;
import datastructures.interfaces.IDisjointSet;
import misc.BaseTest;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestArrayDisjointSet extends BaseTest {
    private <T> IDisjointSet<T> createForest(T[] items) {
        IDisjointSet<T> forest = new ArrayDisjointSet<>();
        for (T item : items) {
            forest.makeSet(item);
        }
        return forest;
    }

    private <T> void check(IDisjointSet<T> forest, T[] items, int[] expectedIds) {
        for (int i = 0; i < items.length; i++) {
            assertEquals(expectedIds[i], forest.findSet(items[i]));
        }
    }

    @Test(timeout=SECOND) //my test
    public void basicInsertAndFind() {
        IDisjointSet<String> forest = createForest(new String[]{"a", "b", "c"});
        int seta = forest.findSet("a");
        assertEquals(seta,0);
    }

    @Test(timeout=SECOND) //my test
    public void basicUnion() {
        String[] trees = new String[]{"a", "aa", "b", "bb", "c", "cc", "d", "dd", "e", "ee", "f", "ff"};
        IDisjointSet<String> forest =
                createForest(trees);
        forest.union("a", "aa");
        forest.union("b", "bb");
        forest.union("c", "cc");
        forest.union("d", "dd");
        forest.union("e", "ee");
        forest.union("f", "ff");

        for(int i=0; i < trees.length/2; i++) {
            try {
                forest.union(trees[2*i], trees[2*i+1]);
                fail("exception should be thrown");
            } catch (IllegalArgumentException e) {
                //glad to be here
            }
        }
        for(int i=0; i < trees.length/2; i++) {
            try {
                forest.union(trees[2*i+1], trees[2*i]);
                fail("exception should be thrown");
            } catch (IllegalArgumentException e) {
                //glad to be here
            }
        }
    }

    @Test(timeout=SECOND) //my test
    public void repeatedElement() {
        try {
            IDisjointSet<String> forest = createForest(new String[]{"a", "a", "c"});
            fail("You shouldn't be able to put the same element into disjoint sets twice");
        } catch(IllegalArgumentException e) {
            //good to be hear
        }
    }

    @Test(timeout=4*SECOND) //my test
    public void largeSetReaptedElement() {
        ArrayDisjointSet<Integer> forest = new ArrayDisjointSet<>();
        forest.makeSet(0);

        int numItems = 5000;
        for (int i = 1; i < numItems; i++) {
            forest.makeSet(i);
            forest.union(0, i);
        }

        try {
            forest.makeSet(0);
            fail("Adding the same set again");
        } catch(IllegalArgumentException ex) {
            //good to be here.
        }

    }

//    @Test(timeout=4*SECOND)
//    public void testLargeForest2() {
//        IDisjointSet<Integer> forest = new ArrayDisjointSet<>();
//        forest.makeSet(1);
//
//        int numSets = 10;
//        int numItems = 5000;
//        for(int k = 0; k < numSets; k++) {
//            forest.makeSet(k*numItems + 1);
//            for (int i = k*numItems+2; i < (k+1)*numItems; i++) {
//                forest.makeSet(i);
//                forest.union(k*numItems+1, i);
//            }
//        }
//
//        int cap = 6000;
//
//        int k = 0;
//        int id = forest.findSet(k);
//        for (int i = 0; i < cap; i++) {
//            for (int j = 0; j < numItems; j++) {
//                assertEquals(id, forest.findSet(j));
//            }
//        }
//    }

    @Test(timeout=SECOND)
    public void testMakeSetAndFindSetSimple() {
        String[] items = new String[] {"a", "b", "c", "d", "e"};
        IDisjointSet<String> forest = this.createForest(items);

        for (int i = 0; i < 5; i++) {
            check(forest, items, new int[] {0, 1, 2, 3, 4});
        }
    }

    @Test(timeout=SECOND)
    public void testUnionSimple() {
        String[] items = new String[] {"a", "b", "c", "d", "e"};
        IDisjointSet<String> forest = this.createForest(items);

        forest.union("a", "b");
        int id1 = forest.findSet("a");
        assertTrue(id1 == 0 || id1 == 1);
        assertEquals(id1, forest.findSet("b"));

        forest.union("c", "d");
        int id2 = forest.findSet("c");
        assertTrue(id2 == 2 || id2 == 3);
        assertEquals(id2, forest.findSet("d"));

        assertEquals(4, forest.findSet("e"));
    }

    @Test(timeout=SECOND)
    public void testUnionUnequalTrees() {
        String[] items = new String[] {"a", "b", "c", "d", "e"};
        IDisjointSet<String> forest = this.createForest(items);

        forest.union("a", "b");
        int id = forest.findSet("a");

        forest.union("a", "c");

        for (int i = 0; i < 5; i++) {
            check(forest, items, new int[] {id, id, id, 3, 4});
        }
    }

    @Test(timeout=SECOND)
    public void testIllegalFindSet() {
        String[] items = new String[] {"a", "b", "c", "d", "e"};
        IDisjointSet<String> forest = this.createForest(items);

        try {
            forest.findSet("f");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // All ok -- expected result
        }
    }

    @Test(timeout=SECOND)
    public void testIllegalUnion() {
        String[] items = new String[] {"a", "b", "c", "d", "e"};
        IDisjointSet<String> forest = this.createForest(items);

        try {
            forest.union("a", "f");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // All ok -- expected result
        }

        forest.union("a", "b");

        try {
            forest.union("a", "b");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // All ok -- expected result
        }
    }

    @Test(timeout=4 * SECOND)
    public void testLargeForest() {
        IDisjointSet<Integer> forest = new ArrayDisjointSet<>();
        forest.makeSet(0);

        int numItems = 5000;
        for (int i = 1; i < numItems; i++) {
            forest.makeSet(i);
            forest.union(0, i);
        }

        int cap = 6000;
        int id = forest.findSet(0);
        for (int i = 0; i < cap; i++) {
            for (int j = 0; j < numItems; j++) {
                assertEquals(id, forest.findSet(j));
            }
        }
    }
}
