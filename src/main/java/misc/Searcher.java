package misc;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;
import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;

public class Searcher {
    /**
     * This method takes the input list and returns the top k elements
     * in sorted order.
     *
     * So, the first element in the output list should be the "smallest"
     * element; the last element should be the "biggest".
     *
     * If the input list contains fewer then 'k' elements, return
     * a list containing all input.length elements in sorted order.
     *
     * This method must not modify the input list.
     *
     * @throws IllegalArgumentException  if k < 0
     */
    public static <T extends Comparable<T>> IList<T> topKSort(int k, IList<T> input) {

        if(k<1) {
            throw new IllegalArgumentException();
        }


        ArrayHeap<T> heap = new ArrayHeap<T>();
        for(T i: input) {
            if(heap.size() < k) {
                System.out.println("initial insert " + i);
                heap.insert(i);
            } else {
                if(heap.peekMin().compareTo(i) < 0) {
                    T min = heap.removeMin();
                    System.out.println("Removed: " + min);
                    heap.insert(i);

                    System.out.println("Inserted: " + i);
                    System.out.println("Size is: " + heap.size());
                    System.out.println();
                }
            }
        }


        DoubleLinkedList<T> theAns = new DoubleLinkedList<T>();

        System.out.println("LOOK AT THE HEAP");
        T[] heapE = heap.array();
        for(int index = 0; index < heapE.length; index ++) {
            System.out.println(heapE[index]);
        }


        int elementsInHeap = heap.size();
        for(int j = 0; j < elementsInHeap; j++) {

            T min = heap.removeMin();
            System.out.println("Size is: " + heap.size());
            System.out.println("Heap element: " + min);
            theAns.add(min);
        }
        // Implementation notes:
        //
        // - This static method is a _generic method_. A generic method is similar to
        //   the generic methods we covered in class, except that the generic parameter
        //   is used only within this method.
        //
        //   You can implement a generic method in basically the same way you implement
        //   generic classes: just use the 'T' generic type as if it were a regular type.
        //
        // - You should implement this method by using your ArrayHeap for the sake of
        //   efficiency.

        for(int h = theAns.size() - 1; h != -1; h--) {
            T mover = theAns.get(h);
            theAns.delete(h);
            theAns.add(mover);
        }

        return theAns;
    }
}
