package calculator.ast;

import calculator.gui.ImageDrawer;
import calculator.interpreter.Environment;
import calculator.errors.EvaluationError;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import misc.exceptions.NotYetImplementedException;

import java.awt.*;

/**
 * All of the static methods in this class are given the exact same parameters for
 * consistency. You can often ignore some of these parameters when implementing your
 * methods.
 *
 * Some of these methods should be recursive. You may want to consider using public-private
 * pairs in some cases.
 */
public class ExpressionManipulators {
    /**
     * Takes the given AstNode node and attempts to convert it into a double.
     *
     * Returns a number AstNode containing the computed double.
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if any of the expressions uses an unknown operation.
     */
    public static AstNode toDouble(Environment env, AstNode node) {
        // To help you get started, we've implemented this method for you.
        // You should fill in the TODOs in the 'toDoubleHelper' method.
        return new AstNode(toDoubleHelper(env.getVariables(), node));
    }

    public static double toDoubleHelper(IDictionary<String, AstNode> variables, AstNode node) {
        // There are three types of nodes, so we have three cases.
        if (node.isNumber()) {
            return node.getNumericValue();
        } else if (node.isVariable()) {
            if (!variables.containsKey(node.getName())) {
                // If the expression contains an undefined variable, we give up.
                throw new EvaluationError("Undefined variable: " + node.getName());
            }
            AstNode n = variables.get(node.getName());
            return n.getNumericValue();
        } else {
            String name = node.getName();
            if (name.equals("+")) {
                Double sum = 0.0;
                for(AstNode i : node.getChildren()) {
                    Double d = toDoubleHelper(variables, i);
                    sum += d;
                }
                return sum;
            } else if (name.equals("-")) {
                if(node.getChildren().size() != 2) {
                    throw new EvaluationError("Subtraction requires two arguments");
                } else {
                    IList<AstNode> c = node.getChildren();
                    Double first = toDoubleHelper(variables, c.get(0));
                    Double second = toDoubleHelper(variables, c.get(1));
                    Double result = first - second;
                    return result;
                }
            } else if (name.equals("*")) {
                Double product = 1.0;
                for(AstNode i : node.getChildren()) {
                    Double d = toDoubleHelper(variables, i);
                    product *= d;
                }
                return product;
            } else if (name.equals("/")) {
                if(node.getChildren().size() != 2) {
                    throw new EvaluationError("Division requires two arguments");
                } else {
                    IList<AstNode> c = node.getChildren();
                    Double first = toDoubleHelper(variables, c.get(0));
                    Double second = toDoubleHelper(variables, c.get(1));
                    Double result = first/second;
                    return result;
                }
            } else if (name.equals("^")) {
                if(node.getChildren().size() != 2) {
                    throw new EvaluationError("power requires two arguments");
                } else {
                    IList<AstNode> c = node.getChildren();
                    Double first = toDoubleHelper(variables, c.get(0));
                    Double second = toDoubleHelper(variables, c.get(1));
                    Double result = Math.pow(first, second);
                    return result;
                }
            } else if (name.equals("negate")) {
                if(node.getChildren().size() != 1) {
                    throw new EvaluationError("negation requires one arguments");
                } else {
                    IList<AstNode> c = node.getChildren();
                    Double result = -1 *toDoubleHelper(variables, c.get(0));
                    return result;
                }
            } else if (name.equals("sin")) {
                if(node.getChildren().size() != 1) {
                    throw new EvaluationError("sin requires one arguments");
                } else {
                    IList<AstNode> c = node.getChildren();
                    Double result = Math.sin(toDoubleHelper(variables, c.get(0)));
                    return result;
                }
            } else if (name.equals("cos")) {
                if(node.getChildren().size() != 1) {
                    throw new EvaluationError("sin requires one arguments");
                } else {
                    IList<AstNode> c = node.getChildren();
                    Double result = Math.cos(toDoubleHelper(variables, c.get(0)));
                    return result;
                }
            } else {
                throw new EvaluationError("Unknown operation: " + name);
            }
        }
    }

    public static AstNode simplify(Environment env, AstNode node) {
        // Try writing this one on your own!
        // Hint 1: Your code will likely be structured roughly similarly
        //         to your "toDouble" method
        // Hint 2: When you're implementing constant folding, you may want
        //         to call your "toDouble" method in some way
        IDictionary<String, AstNode> variables = env.getVariables();
        if (node.isNumber()) {
            return node;
        } else if (node.isVariable()) {
            return node;
        }
        if(node.isOperation()) {
            String name = node.getName();
            if(name.equals("simplify")) {
                return simplify(env, node.getChildren().get(0));
            }
            try {
                double result = toDoubleHelper(env.getVariables(), node);
                return new AstNode(result);
            } catch (EvaluationError e) {
                return nodeWithProperChildren(node, env);
            }
        }
        System.out.println("simplify is completed");
        return node;
    }

    private static AstNode nodeWithProperChildren(AstNode node, Environment env) {
        IList<AstNode> l = node.getChildren();
        IList<AstNode> ln = new DoubleLinkedList<AstNode>();
        String new_name = node.getName();
        for (AstNode i : l) {
            ln.add(simplify(env,i));
        }
        return new AstNode(new_name, ln);
    }

    /**
     * Expected signature of plot:
     *
     * >>> plot(exprToPlot, var, varMin, varMax, step)
     *
     * Example 1:
     *
     * >>> plot(3 * x, x, 2, 5, 0.5)
     *
     * This command will plot the equation "3 * x", varying "x" from 2 to 5 in 0.5
     * increments. In this case, this means you'll be plotting the following points:
     *
     * [(2, 6), (2.5, 7.5), (3, 9), (3.5, 10.5), (4, 12), (4.5, 13.5), (5, 15)]
     *
     * ---
     *
     * Another example: now, we're plotting the quadratic equation "a^2 + 4a + 4"
     * from -10 to 10 in 0.01 increments. In this case, "a" is our "x" variable.
     *
     * >>> c := 4
     * 4
     * >>> step := 0.01
     * 0.01
     * >>> plot(a^2 + c*a + a, a, -10, 10, step)
     *
     * ---
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable. //they will contain an undefined variable
     * @throws EvaluationError  if varMin > varMax
     * @throws EvaluationError  if 'var' was already defined
     * @throws EvaluationError  if 'step' is zero or negative
     */
    public static AstNode plot(Environment env, AstNode node) {


//        AstNode operation = ; // an expression with a variable in it
//        AstNode var = ; // the variable
//        AstNode varMin = ;
//        AstNode varMax = ;
//        AstNode step = ; // something that toDoubleHelper evaluates to a number // how is this not a

        //wow I am really confused about all of this. Why is it this way? I imported the class. Now I should
        //be able to say class.methodIwant.
        IList<AstNode> children = node.getChildren();

        try {
            AstNode expression = children.get(0);
            AstNode variable = children.get(1);
            AstNode minN = children.get(2);
            AstNode maxN = children.get(3);
            AstNode stepsN = children.get(4);

            Double min = minN.getNumericValue();
            Double max = maxN.getNumericValue();
            Double steps = stepsN.getNumericValue();
            Double delta = (max-min)/(steps-1);

            IList<Double> x = new DoubleLinkedList<Double>();
            IList<Double> y = new DoubleLinkedList<Double>();

            for(int j =0; j < steps; j++) {
                Double x_coordinate = min+j*delta;
                x.add(x_coordinate);
                env.getVariables().put(variable.getName(), new AstNode(x_coordinate));
                y.add(toDoubleHelper(env.getVariables(), expression));
            }
            ImageDrawer i = env.getImageDrawer();
            i.drawScatterPlot("Y vs X", "x", "y",x,y);
        } catch(IndexOutOfBoundsException e) {
            return new AstNode(0);
        }


        // Note: every single function we add MUST return an
        // AST node that your "simplify" function is capable of handling.
        // However, your "simplify" function doesn't really know what to do
        // with "plot" functions (and what is the "plot" function supposed to
        // evaluate to anyways?) so we'll settle for just returning an
        // arbitrary number.
        //
        // When working on this method, you should uncomment the following line:
        //
         return new AstNode(1);
    }
}
