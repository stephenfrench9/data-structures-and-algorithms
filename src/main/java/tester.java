public class tester {
    public static void main(String[] args) {
        System.out.println("tester tester tester tester tester is running");
        DoubleLinkedList<Integer> a = new DoubleLinkedList<Integer>();

        a.add(0);
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);
        a.add(6);
        a.add(7);
        a.add(8);

        for(int i:a) {
            System.out.println(i);
        }
        System.out.println("the size of the list is: " + a.size());
        System.out.println("now lets do some OPerations");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        try {
            a.delete(9);
        } catch(Exception e) {
            System.out.println("Exception Thrown");
        }

        System.out.println("OPerations completed.");
        System.out.println("the size of the list is: " + a.size());


        for(int i:a) {
            System.out.println(i);
        }

    }
}
