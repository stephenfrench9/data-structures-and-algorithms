package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;

/**
 * TODO: Replace this file with the one you wrote from project 1
 * TODO: Add the missing "iterator()" method
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    private Pair<K, V>[] pairs;

    public ArrayDictionary() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }

    @Override
    public V get(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public void put(K key, V value) {
        throw new NotYetImplementedException();
    }

    @Override
    public V remove(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean containsKey(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public int size() {
        throw new NotYetImplementedException();
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        throw new NotYetImplementedException();
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
