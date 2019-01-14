package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDisjointSet;
import misc.exceptions.NotYetImplementedException;

/**
 * See IDisjointSet for more details.
 */
public class ArrayDisjointSet<T> implements IDisjointSet<T> {
    // Note: do NOT rename or delete this field. We will be inspecting it
    // directly within our private tests.
    private int[] pointers; //lets assume this is like 'tree structure'
    private ChainedHashDictionary<T, Integer> values;
    int size;

    // However, feel free to add more methods and private helper methods.
    // You will probably need to add one or two more fields in order to
    // successfully implement this class.

    public ArrayDisjointSet() {
        pointers = new int[20];
        values = new ChainedHashDictionary<>();
        size = 0;
    }

    @Override
    public void makeSet(T item) {
        if(values.containsKey(item)) {
            throw new IllegalArgumentException();
        }

        if(size == pointers.length) {
            int[] new_pointers = new int[2*pointers.length];
            for(int i = 0; i < pointers.length; i++) {
                new_pointers[i] = pointers[i];
            }
            pointers = new_pointers;
        }
        values.put(item, size);
        pointers[size] = -1;
        size += 1;
    }

    private int findID(T item) {
        if(!values.containsKey(item)) {
            throw new IllegalArgumentException();
        } else {
            return values.get(item);
        }
    }

    private int goUp(int index) {
        if(pointers[index]<0) {
            return index;
        } else {
            return goUp(pointers[index]);
        }
    }

    @Override
    public int findSet(T item)
    {
        int itemId = findID(item);
        int rootId = goUp(itemId);
        return rootId;
    }

    @Override
    public void union(T item1, T item2) {
        int id1 = findSet(item1);
        int id2 = findSet(item2);
        if(id1 == id2) {
            throw new IllegalArgumentException();
        }
        int rank1 = pointers[id1];
        int rank2 = pointers[id2];

        int higherRank = id2;
        int lowerRank = id1;
        if(rank1 < rank2) {
            higherRank = id1;
            lowerRank = id2;
        }

        pointers[lowerRank] = higherRank;
        if(rank1 == rank2) {
            pointers[higherRank] -= 1;
        }
    }

    public void print_pointers() {
        for(int k=0; k<size; k++) {
            System.out.println(String.format("%3d :%3d", k, pointers[k]));
        }
    }
}
