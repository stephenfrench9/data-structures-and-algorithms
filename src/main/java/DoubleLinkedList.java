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
            size = 1;
        } else {
            back.next = new node<T>(null, back, item);
            back = back.next;
            size += 1;
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

        if(index >= 0 && index <= size - 1) {
            node<T> p = front;

            for(int k = 1; k <= index; k++) {
                p = p.next;
            }

            return p.data;

        } else {
            return null;
        }

    }

    @Override
    public void set(int index, T item) {
        if(valid(index)) {
            node<T> p = pointAt(index);
            p.data = item;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void insert(int index, T item) {
        if(index >= 0 && index <= size) {
            if(size == 0) {
                front = new node<T>(item);
                back = front;
                size += 1;
                return;
            }

            if(size == index) {
                back.next = new node<T>(item);
                back.next.previous = back;
                back = back.next;
                size += 1;
                return;
            } else {
                node<T> p = pointAt(index);
                node<T> inserted = new node<T>(item);
                inserted.next = p;
                if(p==front) {
                    p.previous = inserted;
                    front = inserted;
                    size += 1;
                } else {
                    node<T> predecessor;
                    predecessor = p.previous;
                    predecessor.next = inserted;
                    p.previous = inserted;
                    inserted.previous = predecessor;
                    size += 1;
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T delete(int index) {
        T r = null;//Is this ok? can a String be null?

        if(valid(index)) {//There is an item in the list and we are pointing at it.
            node<T> p = pointAt(index);
            r = p.data;

            node<T> previous = p.previous;
            node<T> next = p.next;

            if(front == back) {//one thing in the list
                front = null;
                back = null;
                return r;
            }

            if(previous == null) {//front of a meaty list
                front = next;
                front.previous = null;
                return r;
            }

            if(next == null) { //end of a meaty list
                back = previous;
                back.next = null;
                return r;
            }


            previous.next = next;
            next.previous = previous;
            p.next = null;
            p.previous = null;


        } else {
            throw new IndexOutOfBoundsException();
        }

        return r;
    }

    @Override
    public int indexOf(T item) {
        return 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T other) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedListIterator<T>(this.front);
    }

    private boolean valid(int index) {
        return index >= 0 && index <= size - 1;
    }

    private node<T> pointAt(int index) {
        node<T> p = front;
        for(int k = 1; k <= index; k++) {
            p = p.next;
        }
        return p;
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



