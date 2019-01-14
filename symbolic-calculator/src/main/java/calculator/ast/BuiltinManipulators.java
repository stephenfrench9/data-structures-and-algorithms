package calculator.ast;

import calculator.interpreter.Environment;
import calculator.errors.EvaluationError;
import calculator.interpreter.Interpreter;
import calculator.errors.QuitError;
import datastructures.interfaces.IList;

public class BuiltinManipulators {
    public static AstNode handleQuit(Environment env, AstNode wrapper) {
        throw new QuitError();
    }

    public static AstNode handleBlock(Environment env, AstNode wrapper) {
        assertSignatureOk("block", wrapper);
        AstNode out = new AstNode(1);
        for (AstNode child : wrapper.getChildren()) {
            out = env.getInterpreter().evaluate(env, child);
        }
        // Return value is same as last item in block
        return out;
    }

    public static AstNode handleAssign(Environment env, AstNode wrapper) {
        assertSignatureOk("assign", 2, wrapper);
        Interpreter interp = env.getInterpreter();
        IList<AstNode> children = wrapper.getChildren();

        // Parse children
        AstNode var = children.get(0).getChildren().get(0);  // Unnest 'simplify'
        AstNode expr = interp.evaluate(env, children.get(1));

        // Some sanity checking
        if (!var.isVariable()) {
            throw new EvaluationError(String.format(
                    "LHS of assignment must be a variable. Encountered %s instead.",
                    var.isNumber() ? var.getNumericValue() : var.getName()));
        }

        // Record and return result
        env.getVariables().put(var.getName(), expr);
        return expr;
    }

    private static void assertSignatureOk(String name, int numChildren, AstNode node) {
        boolean ok = node.isOperation()
                && node.getName().equals(name)
                && node.getChildren().size() == numChildren;
        if (!ok) {
            String msg = String.format(
                    "Input ('%s' w/ %d) does not match expected ('%s' w/ %d)",
                    node.getName(),
                    node.getChildren().size(),
                    name,
                    numChildren);

            throw new EvaluationError(msg);
        }
    }

    private static void assertSignatureOk(String name, AstNode node) {
        boolean ok = node.isOperation() && node.getName().equals(name);
        if (!ok) {
            String msg = String.format(
                    "Input ('%s') does not match expected ('%s')",
                    node.getName(),
                    name);

            throw new EvaluationError(msg);
        }
    }
}

