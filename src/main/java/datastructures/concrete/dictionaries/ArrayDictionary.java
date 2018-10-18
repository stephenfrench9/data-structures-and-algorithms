package datastructures.concrete.dictionaries;

import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;
    private int size;

    // You're encouraged to add extra fields (and helper methods) though!

    public ArrayDictionary() {
        pairs = makeArrayOfPairs(10);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);

    }

    private int find(K key) {
        for(int k = 0; k < size; k += 1) {
            if(key != null) {
                if (key.equals(pairs[k].key)) {
                    return k;
                }
            } else {
                if (key == pairs[k].key) {
                    return k;
                }
            }
        }
        return -1;
    }

    @Override
    public V get(K key) {
        int index = find(key);

        if(index == -1) {
            throw new NoSuchKeyException();
        }

        return pairs[index].value;
    }

    @Override
    public void put(K key, V value) {

        if(size == pairs.length) {
            Pair<K, V>[] newList = makeArrayOfPairs(pairs.length + 10);
            for(int k=0; k < size; k++) {
                newList[k] = pairs[k];
            }
            pairs = newList;
        }

        int index = find(key);
        Pair<K, V> p = new Pair<K, V>(key, value);

        if(index == -1) {
            pairs[size] = p;
            size += 1;
        } else {
            pairs[index] = p;
        }
    }

    @Override
    public V remove(K key) {
        int index = find(key);

        if(index==-1) {
            throw new NoSuchKeyException();
        } else {
            V r = pairs[index].value;
            pairs[index] = pairs[size-1];
            pairs[size] = null;
            size -= 1;
            return r;
        }
    }

    @Override
    public boolean containsKey(K key) {
           int index = find(key);
           if(index > -1) {
               return true;
           } else {
               return false;
           }
    }

    @Override
    public int size() {
        return size;
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}
