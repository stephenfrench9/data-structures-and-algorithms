package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import misc.exceptions.NotYetImplementedException;

import java.util.Arrays;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int size;

    // Feel free to add more fields and constants.

    public ArrayHeap() {
        heap = makeArrayOfT(100);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    @Override
    public T removeMin() {


        return null;
    }

    @Override
    public T peekMin() {
        throw new NotYetImplementedException();
    }

    @Override
    public void insert(T item) {
        heap[size] = item;
        int newestItem = size;
        size += 1;
        up(newestItem);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printTree() {
        int level = 0;
        int current = 0;
        while(current < size) {

            int num = (int) Math.pow(NUM_CHILDREN, level);
            for(int j = 0; j < num; j++) {
                System.out.print(heap[current] + " ");
                current += 1;
            }
            System.out.println("");
            level += 1;

        }
    }

    @Override
    public T[] array() {
        return heap;
    }

    private int findChild(int parent) {
        return 4*parent + 1;
    }

    private int findParent(int child) {
        return (child - 1)/4;
    }

    private void up(int index) { //index is the element that you might push up
        if(index == 0) {
            return;
        }

        T floater = heap[index];
        T sinker = heap[findParent(index)];

        if(floater.compareTo(sinker) < 0) {
            heap[index] = sinker;
            heap[findParent(index)] = floater;
            up(findParent(index));
        }

    }

}
