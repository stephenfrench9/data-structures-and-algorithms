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
        try {
            a.insert(9, 100);
            a.insert(9, 150);
            a.insert(5, 111);
            a.insert(0, 100);
            a.insert(0, 100);
        } catch(Exception e) {
            System.out.println("Exception Thrown");
        }
        System.out.println("the size of the list is: " + a.size());


//        try {
//            a.set(-1, 100);
//        } catch(Exception e) {
//            System.out.println("Exception Thrown");
//        }
//        try {
//            a.set(8, 100);
//        } catch(Exception e) {
//            System.out.println("Exception Thrown");
//        }
//        try {
//            a.set(10, 100);
//        } catch(Exception e) {
//            System.out.println("Exception Thrown");
//        }


        for(int i:a) {
            System.out.println(i);
        }

    }
}
