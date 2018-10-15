import org.junit.Test;

public class TestDeleteFunctionality extends BaseTest {

    @Test(timeout = 1000)
    public void testDeleteString() {
        IList<String> list = makeInstance();
        list.add("a");
        list.add("b");
        String deleted = list.delete(0);

        assertListMatches(new String[]{"b"}, list);
        assertEquals("a", deleted);
    }

    @Test(timeout = 1000)
    public void testDeleteInt() {
        IList<Integer> list = makeInstance();
        list.add(1);
        list.add(2);
        int deleted = list.delete(0);

        assertListMatches(new Integer[]{2}, list);
        assertEquals(1, deleted);
    }

    @Test(timeout = 1000)
    public void testDeleteFinal() {
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

        String deleted = list.remove();

        assertEquals(null, deleted);
        assertListMatches(new String[]{}, list);
    }


}
