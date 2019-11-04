package edvardas.ast.nodes;

import edvardas.UnaryOperator;
import edvardas.ast.ASTPrinter;
import edvardas.ast.nodes.Expression;

public class ExprUnary extends Expression {
    private Expression left;
    private UnaryOperator operator;
    private Expression right;
    public ExprUnary(UnaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(ASTPrinter printer) throws Exception {
       printer.print("operator", operator);
       printer.print("left", left);
       printer.print("right", right);
    }
    
}