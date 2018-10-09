import java.util.Iterator;

public class DoubleLinkedList<T> implements IList<T> {
    node<T> front;
    node<T> back;
    int size;

    public DoubleLinkedList() {//I think you should not include the generic type in the constructor, because the constructor is what the client uses,
//        and the client shouldn't decide or have to say what type of variable the generic class will be using.
        this.front = null;
        this.back = null;
        size = 0;
    }

    @Override
    public void add(T item) {
        if(back==null) {
            back = new node<T>(item);
            front = back;
        } else {
            back.next = new node<T>(null, back, item);
            back = back.next;
        }
    }

    @Override
    public T remove() {
        if(back == null) {
            return null;
        } else if (back == front) {
            T r = back.data;
            back = null;
            front = null;
            return r;
        }

        T r = back.data;
        back = back.previous;
        back.next.previous = null;
        back.next = null;

        return r;
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
        return new DoubleLinkedListIterator<T>(this.front);
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        node<T> current;

        public DoubleLinkedListIterator(node<T> front){
            this.current =  front;
        }

        @Override
        public boolean hasNext() {
            if(current == null) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public T next() {
            if(current == null) {
                return null;
                //is this ok? can you return null? what if T is an integer.
            } else {
                T value = current.data;
                current = current.next;
                return value;
            }
        }
    }

    private static class node<E> {
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



