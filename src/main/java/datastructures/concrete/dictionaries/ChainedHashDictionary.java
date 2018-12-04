package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See the spec and IDictionary for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;
    private int tablesize;
    private int size;
    // You're encouraged to add extra fields (and helper methods) though!

    public void resize() {
        Iterator<KVPair<K, V>> it = iterator();
        IDictionary<K, V>[] oldChains = chains;
        tablesize = 4*chains.length;
        chains = makeArrayOfChains(tablesize);
        size = 0;
        while(it.hasNext()) {
            KVPair<K, V> p = it.next();
            put(p.getKey(), p.getValue());
        }
    }


    public ChainedHashDictionary() {
        chains = makeArrayOfChains(100);
        tablesize = 10;
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }

    @Override
    public V get(K key) {
        int hashkey = 0;
        int index = 0;
        if(key != null) {
            hashkey = key.hashCode();
            index = Math.abs(hashkey%tablesize);
        }
        if(chains[index]==null) {
            throw new NoSuchKeyException();
        }
        return chains[index].get(key);
    }

    @Override
    public void put(K key, V value) {
        if (size > tablesize * 10) {
            resize();
        }
        try {
            get(key);
        } catch(NoSuchKeyException e) {
            size += 1;
        }


        int hashkey = 0;
        int index = 0;
        if(key!=null) {
            hashkey = key.hashCode();
            index = Math.abs(hashkey % tablesize);
        }

        if (chains[index] == null) {
            chains[index] = new ArrayDictionary<>();
        }

        chains[index].put(key, value);

    }

    @Override
    public V remove(K key) {
        int index = hashMod(key);
        if(chains[index]==null) {
            throw new NoSuchKeyException();
        }
        try {
            V removed = chains[index].remove(key);
            size -= 1;
            return removed;
        } catch (NoSuchKeyException nk) {
            throw new NoSuchKeyException();
        }
    }

    @Override
    public boolean containsKey(K key) {
        int index = hashMod(key);
        if(chains[index]==null) {
            return false;
        }

        return chains[index].containsKey(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains, this.size);
    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *
     * 2. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     * 3. Think about what exactly your *invariants* are. An *invariant*
     *    is something that must *always* be true once the constructor is
     *    done setting up the class AND must *always* be true both before and
     *    after you call any method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private int nextChain; //this is a chain, it has next (-1 when there is not a next)
        private Iterator<KVPair<K, V>> nextIterator; //this is a iterator, it has next. (null when there is not a next)
        private int size;

        public ChainedIterator(IDictionary<K, V>[] chains, int size) {
            this.chains = chains;
            this.size=size;
            nextChain=0; //this is the chain that has 'next' in it
            nextIterator = null; //this is the iterator that has 'next'
            //find the first next.
            boolean go = true;
            while(go) {
                while (nextChain < chains.length && chains[nextChain] == null) {
                    nextChain += 1;
                }
                go = false;
                if(nextChain < chains.length &&!chains[nextChain].iterator().hasNext()) {
                    nextChain += 1;
                    go = true;
                }
            }
            //now you have a chain or have determined there is no next.
            if(nextChain<chains.length) {
                nextIterator = chains[nextChain].iterator();
            } else {
                nextChain = -1;
                nextIterator = null;
            }
        }

        @Override
        public boolean hasNext() {
            if(nextChain==-1) {
                return false;
            }
            return nextIterator.hasNext();
         }

        @Override
        public KVPair<K, V> next() {

            if(nextChain == -1) {
                throw new NoSuchElementException();
            } else {
                KVPair<K, V> next = nextIterator.next();
                if(!nextIterator.hasNext()) {
                    updateNexts();
                }
                return next;
            }

        }

        private void updateNexts() {
            nextChain += 1;

            boolean search = true;
            while(search) {
                while (nextChain < chains.length && chains[nextChain] == null) {//if the chain is empty, make sure its null, no empty iterators.
                    nextChain += 1;
                }
                search = false;
                if(nextChain<chains.length&&!chains[nextChain].iterator().hasNext()){
                    nextChain += 1;
                    search = true;
                }
            }
            if(nextChain<chains.length) {//outof index error immenent
                nextIterator = chains[nextChain].iterator();
                //confirm that this iterator has a next, assign next, handle the empty chain
            } else {
                nextIterator = null;
                nextChain = -1;
            }
        }
    }

    public int hashMod(K key) {
        int hash = 0;
        int index = 0;
        if(key!=null) {
            hash = key.hashCode();
            index = Math.abs(hash % tablesize);
        }
        return index;
    }
}
