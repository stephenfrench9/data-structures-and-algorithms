package datastructures.concrete;

import datastructures.interfaces.IDisjointSet;
import misc.exceptions.NotYetImplementedException;

/**
 * See IDisjointSet for more details.
 */
public class ArrayDisjointSet<T> implements IDisjointSet<T> {
    // Note: do NOT rename or delete this field. We will be inspecting it
    // directly within our private tests.
    private int[] pointers; //lets assume this is like 'tree structure'
    private T[] values;
    int size;

    // However, feel free to add more methods and private helper methods.
    // You will probably need to add one or two more fields in order to
    // successfully implement this class.

    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    public ArrayDisjointSet() {
        pointers = new int[20];
        values = makeArrayOfT(20);
        size = 0;
    }

    @Override
    public void makeSet(T item) {
        values[size] = item;
        pointers[size] = -1;
        size += 1;
    }

    private int findID(T item) {
        int itemId = -1;
        for(int i=0; i < values.length; i++) {
            if(values[i] == item) {
                itemId = i;
            }
        }
        if(itemId == -1) {
            throw new IllegalArgumentException();
        }
        return itemId;
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

        //we have two distinct ids. They are roots.
        //values[id] holds the value
        //pointers[id] gives the rank. ROOTS.

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
            System.out.println(String.format("%3d :%3d :%4s", k, pointers[k], values[k]));
        }
    }
}
