package datastructures.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import org.junit.Test;

import java.util.Iterator;

public class TestArrayDictionary extends TestDictionary {

    protected <K, V> IDictionary<K, V> newDictionary() {
        return new ArrayDictionary<>();
    }

    protected IDictionary<String, Integer> makeBasicList() {
        IDictionary<String, Integer> d = this.newDictionary();

        d.put("one", 1);
        d.put("two", 2);
        d.put("three", 3);

        assertDictMatches(new String[]{"one","two","three"}, new Integer[]{1, 2, 3}, d);

        return d;
    }

    @Test
    public void testIterator() {
        System.out.println("runnin from testDictionary class");
        IDictionary<String, Integer> d = makeBasicList();
        assertEquals(3, d.get("three"));

        Iterator it = d.iterator();

        while(it.hasNext()) {
            System.out.println(it.next().equals(new KVPair<String, Integer>("one", 1)));
        }

        for(KVPair<String, Integer> pari: d) {
            System.out.println(pari);
        }

        System.out.println(d.get("three"));
    }

}
