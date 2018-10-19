package calculator.ast;

import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

public class tester {

    public static void main(String[] args) {
        System.out.println("tester is running");

        AstNode n = new AstNode(4.4);

        IList<AstNode> children = new DoubleLinkedList<>();

        IDictionary<String, AstNode> d = new ArrayDictionary<String, AstNode>();
        AstNode q = new AstNode("x");
        d.put(q.getName(), n);

        System.out.println("the value of the node e is: " + ExpressionManipulators.toDoubleHelper(d, q));
    }
}
