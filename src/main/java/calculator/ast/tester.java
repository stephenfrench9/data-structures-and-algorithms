package calculator.ast;

import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

public class tester {

    public static void main(String[] args) {
        System.out.println("this is a picture");

        AstNode n = new AstNode(4.4);
        AstNode p = new AstNode(3.0);
        AstNode nn = new AstNode(3.0);
        AstNode pp = new AstNode(3.0);

        IList<AstNode> children = new DoubleLinkedList<>();
        children.add(n);
        children.add(p);
        AstNode e = new AstNode("-", children);
        System.out.println("e has " + e.getChildren().size() + " children");

        IDictionary<String, AstNode> d = new ArrayDictionary<String, AstNode>();

        System.out.println("the value of the node e is: " + ExpressionManipulators.toDoubleHelper(d, e));
    }
}
