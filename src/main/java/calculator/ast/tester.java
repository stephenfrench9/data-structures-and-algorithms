package calculator.ast;

import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

public class tester {

    public static void main(String[] args) {
        System.out.println("tester is running");

        AstNode n = new AstNode(1.7);
        AstNode nn = new AstNode(2);
        AstNode nnn = new AstNode(2);

        //children
        IList<AstNode> children = new DoubleLinkedList<>();




        //variables
        IDictionary<String, AstNode> d = new ArrayDictionary<String, AstNode>();
        AstNode q = new AstNode("x");
        d.put(q.getName(), n);
        children.add(q);

        //expression
        AstNode e = new AstNode("sin", children);

        System.out.println(e.isVariable());
        System.out.println(q.isVariable());


        System.out.println("the value of the node e is: " + ExpressionManipulators.toDoubleHelper(d, e));
    }
}
