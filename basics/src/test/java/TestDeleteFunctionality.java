import org.junit.Test;

import java.util.ArrayList;

public class TestDeleteFunctionality extends BaseTest {

    @Test(timeout = 1000)
    public void testDeleteString() {
        IList<String> list = makeInstance();
        list.add("a");
        list.add("b");
        String deleted = list.delete(0);

        assertListMatches(new String[]{"b"}, list);
        assertEquals("a", deleted);
        assertEquals(list.size(), 1);
    }

    @Test(timeout = 1000)
    public void testDeleteInt() {
        IList<Integer> list = makeInstance();
        list.add(1);
        list.add(2);
        int deleted = list.delete(0);

        assertListMatches(new Integer[]{2}, list);
        assertEquals(1, deleted);
        assertEquals(list.size(), 1);
    }

    @Test(timeout = 1000)
    public void testDeleteFirst() {
        IList<Integer> list = makeInstance();
        list.add(1);
        list.add(2);
        int deleted = list.delete(0);

        assertListMatches(new Integer[]{2}, list);
        assertEquals(1, deleted);
        assertEquals(list.size(), 1);
    }

    @Test(timeout = 1000)
    public void testDeleteLast() {
        IList<Integer> list = makeInstance();
        list.add(1);
        list.add(2);
        list.add(3);
        int deleted = list.delete(list.size()-1);

        assertListMatches(new Integer[]{1,2}, list);
        assertEquals(3, deleted);
        assertEquals(list.size(), 2);
    }

    @Test(timeout = 1000)
    public void testDeleteOnly() {
        IList<String> list = makeInstance();

        list.add("that");
        String deleted = list.delete(0);

        assertListMatches(new String[]{}, list);
        assertEquals("that", deleted);
    }

    @Test(timeout = 1000)
    public void testDeleteAddWeaveEmptyList() {
        //on an empty list
        IList<String> list = makeInstance();

        for(int j = 0 ; j < 100; j ++) {
            list.add("that" + j);
            String deleted = list.delete(0);
            assertListMatches(new String[]{}, list);
            assertEquals("that" + j, deleted);
        }
    }

    @Test(timeout = 1000)
    public void testDeleteAddWeave() {

        IList<String> list = makeInstance();

        list.add("a");
        list.add("b");

        for(int j = 0 ; j < 100; j ++) {
            list.add("that" + j);
            String deleted = list.delete(2);
            assertListMatches(new String[]{"a", "b"}, list);
            assertEquals("that" + j, deleted);
        }
    }

    @Test(timeout = 1000)
    public void testDeleteAddManyTimes() {
        //on an empty list
        IList<String> list = makeInstance();

        for(int j = 0 ; j < 100; j ++) {
            list.add("that" + j);
        }
        for(int j = 0 ; j < 100; j ++) {
            list.remove();
        }

        assertListMatches(new String[]{}, list);
    }

    @Test(timeout = 1000)
    public void testDeleteNull() {
        //on an empty list
        IList<String> list = makeInstance();

        list.add(null);
        assertListMatches(new String[]{null}, list);

        String deleted = list.remove();

        assertEquals(null, deleted);
        assertListMatches(new String[]{}, list);
    }


//******************Null entries***********************



    @Test(timeout = 1000)
    public void testDeleteStringNull() {
        IList<String> list = makeInstance();
        list.add(null);
        list.add(null);
        String deleted = list.delete(0);

        assertListMatches(new String[]{null}, list);
        assertEquals(null, deleted);
        assertEquals(list.size(), 1);
    }

    @Test(timeout = 1000)
    public void testDeleteIntNull() {
        IList<Integer> list = makeInstance();
        list.add(null);
        list.add(null);

        Integer ff = null;
        Integer deleted = list.delete(0);
        Integer j = list.get(0);

        ArrayList<Integer> l = new ArrayList<Integer>();
        l.add(null);
        System.out.println(l);

        assertListMatches(new Integer[]{null}, list);
        assertEquals(null, deleted);
        assertEquals(list.size(), 1);
    }

    @Test(timeout = 1000)
    public void testDeleteFirstNull() {
        IList<Integer> list = makeInstance();
        list.add(1);
        list.add(2);
        int deleted = list.delete(0);

        assertListMatches(new Integer[]{2}, list);
        assertEquals(1, deleted);
        assertEquals(list.size(), 1);
    }

    @Test(timeout = 1000)
    public void testDeleteLastNull() {
        IList<Integer> list = makeInstance();
        list.add(1);
        list.add(2);
        list.add(3);
        int deleted = list.delete(list.size()-1);

        assertListMatches(new Integer[]{1,2}, list);
        assertEquals(3, deleted);
        assertEquals(list.size(), 2);
    }

    @Test(timeout = 1000)
    public void testDeleteOnlyNull() {
        IList<String> list = makeInstance();

        list.add("that");
        String deleted = list.delete(0);

        assertListMatches(new String[]{}, list);
        assertEquals("that", deleted);
    }

    @Test(timeout = 1000)
    public void testDeleteAddWeaveEmptyListNull() {
        //on an empty list
        IList<String> list = makeInstance();

        for(int j = 0 ; j < 100; j ++) {
            list.add("that" + j);
            String deleted = list.delete(0);
            assertListMatches(new String[]{}, list);
            assertEquals("that" + j, deleted);
        }
    }

    @Test(timeout = 1000)
    public void testDeleteAddWeaveNull() {

        IList<String> list = makeInstance();

        list.add("a");
        list.add("b");

        for(int j = 0 ; j < 100; j ++) {
            list.add("that" + j);
            String deleted = list.delete(2);
            assertListMatches(new String[]{"a", "b"}, list);
            assertEquals("that" + j, deleted);
        }
    }

    @Test(timeout = 1000)
    public void testDeleteAddManyTimesNull() {
        //on an empty list
        IList<String> list = makeInstance();

        for(int j = 0 ; j < 100; j ++) {
            list.add("that" + j);
        }
        for(int j = 0 ; j < 100; j ++) {
            list.remove();
        }

        assertListMatches(new String[]{}, list);
    }


}
