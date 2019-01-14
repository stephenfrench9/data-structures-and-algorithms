public class tester {
    public static void main(String[] args) {
        System.out.println("tester tester tester tester tester is running");
        DoubleLinkedList<String> a = new DoubleLinkedList<String>();

//        a.add(0);
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        a.add(4);
//        a.add(5);
//        a.add(6);
//        a.add(7);
//        a.add(88);

        a.add("the");
        a.add("day");
        a.add("was");
        a.add("young");


        for(String i:a) {
            System.out.println(i);
        }
        System.out.println("the size of the list is: " + a.size());
        System.out.println("now lets do some OPerations");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        try {
            System.out.println("the index is: " + a.contains("thee"));

        } catch(Exception e) {
            System.out.println("Exception Thrown");
        }

        System.out.println("OPerations completed.");
        System.out.println("the size of the list is: " + a.size());


        for(String i:a) {
            System.out.println(i);
        }

    }
}
