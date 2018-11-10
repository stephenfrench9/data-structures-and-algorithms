package datastructures.dictionaries;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestChainedHashDictionary extends TestDictionary {
    protected <K, V> IDictionary<K, V> newDictionary() {
        return new ChainedHashDictionary<>();
    }

    @Test
    public void sizeMethodAfterResizing() {
        IDictionary<Integer, Integer> d = newDictionary(); assertEquals(0,d.size());
        for(int i = 0; i < 1000; i++) {
            d.put(i, i);
            assertEquals(i+1, d.size());
        }
    }

    @Test
    public void overwrite() {
        IDictionary<Integer, Integer> d = newDictionary(); assertEquals(0,d.size());

        for(int k=0; k < 1000; k++) {
            d.put(k,k);
            assertEquals(k+1, d.size());
        }

        for(int k=0; k < 1000; k++) {
            d.put(k,k);
            assertEquals(1000, d.size());
        }
    }

    @Test
    public void basicOperations() {
        IDictionary<String, String> d = makeBasicDictionary();
        assertDictMatches(new String[]{"keyA","keyB","keyC"}, new String[]{"valA","valB","valC"},d);
    }

    @Test
    public void testRemove() {
        IDictionary<String, String> d = makeBasicDictionary();
        assertDictMatches(new String[]{"keyA","keyB","keyC"}, new String[]{"valA","valB","valC"},d);

        String removed = d.remove("keyA");
        assertDictMatches(new String[]{"keyB","keyC"}, new String[]{"valB","valC"},d);
        assertEquals(removed, "valA");
    }

    @Test
    public void testFailedRemove() {
        IDictionary<String, String> d = makeBasicDictionary();
        assertDictMatches(new String[]{"keyA","keyB","keyC"}, new String[]{"valA","valB","valC"},d);

        String removed = d.remove("keyA");
        assertDictMatches(new String[]{"keyB","keyC"}, new String[]{"valB","valC"},d);
        assertEquals(removed, "valA");

        try {
            String removedAgain = d.remove("keyA");
            assertEquals(removed, "valA");
        } catch (NoSuchKeyException nk) {
            System.out.println("We are catching the error that we meant to catch, a nosuch key error");
        }
        assertDictMatches(new String[]{"keyB","keyC"}, new String[]{"valB","valC"},d);
    }

    @Test
    public void testContainsKey() {
        IDictionary<String, String> d = makeBasicDictionary();
        assertEquals(d.containsKey("keyA"), true); assertEquals(d.containsKey("keyB"), true);
        assertEquals(d.containsKey("keyC"), true); assertEquals(d.containsKey("valA"), false);
        assertEquals(d.containsKey("keyD"), false); assertEquals(d.containsKey("keyAA"), false);
    }

    @Test
    public void testIteratorInitializationHasNext() {
        IDictionary<String, String> d = makeBasicDictionary();
        assertEquals(true, d.iterator().hasNext());
        d.remove("keyA"); assertEquals(true, d.iterator().hasNext());
        d.remove("keyB"); assertEquals(true, d.iterator().hasNext());
        d.remove("keyC"); assertEquals(false, d.iterator().hasNext());
    }

    @Test
    public void testIteratorNext() {
        IDictionary<String, String> d = makeBasicDictionary();
        d.remove("keyA"); assertEquals(true, d.iterator().hasNext());
        d.remove("keyB"); assertEquals(true, d.iterator().hasNext());
        d.remove("keyC"); assertEquals(false, d.iterator().hasNext());


        try {
            d.iterator().next();
            fail("Expected NoSuchElementException");
        } catch(NoSuchElementException nk) {
            //great
        }

        d = makeBasicDictionary();
        Iterator<KVPair<String, String>> it = d.iterator();
        assertEquals(true, it.hasNext());
        assertEquals("keyA", it.next().getKey());
        assertEquals(true, it.hasNext());
        assertEquals("keyB", it.next().getKey());
        assertEquals(true, it.hasNext());
        assertEquals("keyC", it.next().getKey());
        assertEquals(false, it.hasNext());
        try {
            it.next();
            fail("Expected NoSuchElementException");
        } catch(NoSuchElementException nk) {
            //great
        }
    }

    @Test(timeout=SECOND)
    public void testManyObjectsWithSameHashCode() {
        IDictionary<Wrapper<String>, Integer> map = this.newDictionary();
        for (int i = 0; i < 1000; i++) {
            map.put(new Wrapper<>("" + i, 0), i);
        }

        for (int i = 999; i >= 0; i--) {
            String key = "" + i;
            assertEquals(i, map.get(new Wrapper<>(key, 0)));

            assertFalse(map.containsKey(new Wrapper<>(key + "a", 0)));
        }

        Wrapper<String> key1 = new Wrapper<>("abc", 0);
        Wrapper<String> key2 = new Wrapper<>("cde", 0);

        map.put(key1, -1);
        map.put(key2, -2);

        assertEquals(1002, map.size());
        assertEquals(-1, map.get(key1));
        assertEquals(-2, map.get(key2));
    }

    @Test(timeout=SECOND)
    public void testNegativeHashCode() {
        IDictionary<Wrapper<String>, String> dict = this.newDictionary();

        Wrapper<String> key1 = new Wrapper<>("foo", -1);
        Wrapper<String> key2 = new Wrapper<>("bar", -100000);
        Wrapper<String> key3 = new Wrapper<>("baz", 1);
        Wrapper<String> key4 = new Wrapper<>("qux", -4);

        dict.put(key1, "val1");
        dict.put(key2, "val2");
        dict.put(key3, "val3");

        assertTrue(dict.containsKey(key1));
        assertTrue(dict.containsKey(key2));
        assertTrue(dict.containsKey(key3));
        assertFalse(dict.containsKey(key4));

        assertEquals("val1", dict.get(key1));
        assertEquals("val2", dict.get(key2));
        assertEquals("val3", dict.get(key3));

        dict.remove(key1);
        assertFalse(dict.containsKey(key1));
    }

    @Test(timeout=10* SECOND)
    public void stressTest() {
        int limit = 1000000;
        IDictionary<Integer, Integer> dict = this.newDictionary();

        for (int i = 0; i < limit; i++) {
            dict.put(i, i);
            assertEquals(i, dict.get(i));
        }

        for (int i = 0; i < limit; i++) {
            assertFalse(dict.containsKey(-1));
        }

        for (int i = 0; i < limit; i++) {
            dict.put(i, -i);
        }

        for (int i = 0; i < limit; i++) {
            assertEquals(-i, dict.get(i));
            dict.remove(i);
        }
    }
}
