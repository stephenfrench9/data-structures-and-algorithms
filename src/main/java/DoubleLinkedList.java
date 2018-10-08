import java.util.Iterator;

public class DoubleLinkedList<T> implements IList<T> {

    @Override
    public void add(T item) {

    }

    @Override
    public T remove() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public void set(int index, T item) {

    }

    @Override
    public void insert(int index, T item) {

    }

    @Override
    public T delete(int index) {
        return null;
    }

    @Override
    public int indexOf(T item) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(T other) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private class node<E> {
        node<E> next;
        node<E> previous;
        E data;

        public node(E data){
            this.next = null;
            this.previous = null;
            this.data = data;
        }

        public node(node<E> next, node<E> previous, E data) {
            this.next = next;
            this.previous = previous;
            this.data = data;
        }
    }

}



