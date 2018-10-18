package misc;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import org.junit.Assert;

public class BaseTest {
    protected static final int SECOND = 1000;

    /**
     * Please call this method whenever you need to create a new DoubleLinkedList instance
     * instead of calling the constructor directly. Our private tests rely on this method.
     */
    protected <T> IList<T> makeInstance() {
        return new DoubleLinkedList<>();
    }

    /**
     * This method creates a simple list containing three elements to help minimize
     * redundancy later in our tests.
     *
     * Please do not modify this method: our private tests will also use this method.
     */
    protected IList<String> makeBasicList() {
        IList<String> list = this.makeInstance();

        list.add("a");
        list.add("b");
        list.add("c");

        return list;
    }

    /**
     * This test will check if a list contains exactly the same elements as
     * the "expected" array. See the tests you were provided for example
     * usage.
     *
     * Please do not modify this method: our private tests rely on this.
     */
    protected <T> void assertListMatches(T[] expected, IList<T> actual) {
        assertEquals(expected.length, actual.size());
        assertEquals(expected.length == 0, actual.isEmpty());

        for (int i = 0; i < expected.length; i++) {
            try {
                assertEquals("Item at index " + i + " does not match", expected[i], actual.get(i));
            } catch (Exception ex) {
                String errorMessage = String.format(
                        "Got %s when getting item at index %d (expected '%s')",
                        ex.getClass().getSimpleName(),
                        i,
                        expected[i]);
                throw new AssertionError(errorMessage, ex);
            }
        }
    }


    protected static <T> void assertEquals(T expected, T actual) {
        Assert.assertEquals(expected, actual);
    }

    protected static <T> void assertEquals(String message, T expected, T actual) {
        Assert.assertEquals(message, expected, actual);
    }

    /**
     * This wrapper class allows us to define a custom hashcode for arbitrary
     * objects.
     *
     * Whoever instantiates this object MUST make sure they provide a valid
     * hashcode and inner object. That is, when given two Wrapper objects,
     * if the inner objects are equal, then their hashcodes must also be
     * equal.
     */
    protected static class Wrapper<T> {
        private T inner;
        private int hashCode;

        public Wrapper(T inner, int hashCode) {
            this.inner = inner;
            this.hashCode = hashCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            Wrapper<?> wrapper = (Wrapper<?>) o;

            return inner != null ? inner.equals(wrapper.inner) : wrapper.inner == null;
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }
    }
}
