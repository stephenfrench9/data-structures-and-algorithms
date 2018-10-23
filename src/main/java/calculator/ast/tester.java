package calculator.ast;

import calculator.interpreter.Environment;
import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

public class tester {

    public static void main(String[] args) {
        System.out.println("tester is running");

        //numbers
        AstNode n = new AstNode(1);
        AstNode nn = new AstNode(2);
        AstNode nnn = new AstNode(3);

        //variables
        IDictionary<String, AstNode> d = new ArrayDictionary<String, AstNode>();
        AstNode q = new AstNode("x");
//        d.put(q.getName(), n);

        //children
        IList<AstNode> children = new DoubleLinkedList<>();
        children.add(n);
        children.add(nn);

        //expression
        AstNode e = new AstNode("+", children);

        //parent expression
        IList<AstNode> top = new DoubleLinkedList<>();
        top.add(e);
        top.add(q);
        AstNode t = new AstNode("*", top);

        Environment env = new Environment(null, null, null, null, null);
        AstNode s = ExpressionManipulators.simplify(env, e);
        System.out.println("the value of the node e is: " + ExpressionManipulators.toDoubleHelper(d, s));
//        (1+2)*q
    }
}
