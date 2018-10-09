public class tester {
    public static void main(String[] args) {
        System.out.println("tester tester tester tester tester is running");
        DoubleLinkedList<Integer> a = new DoubleLinkedList<Integer>();

        a.add(3);
        a.add(2);
        a.add(1);
        a.add(4);
        a.add(5);
        a.add(6);
        a.add(7);

        for(int i:a) {
            System.out.println(i);
        }
    }
}
