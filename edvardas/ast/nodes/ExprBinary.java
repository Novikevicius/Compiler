package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.ast.ASTPrinter;
import edvardas.ast.nodes.Expression;
import edvardas.parser.Scope;

public class ExprBinary extends Expression {
    protected Expression left;
    protected BinaryOperator operator;
    protected Expression right;
    
    public ExprBinary(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        addChildren(left);
        addChildren(right);
    }

    @Override
    public void print(ASTPrinter printer) throws Exception {
       printer.print("operator", operator);
       printer.print("left", left);
       printer.print("right", right);
    }
    @Override
    public void resolveNames(Scope scope) throws Exception
    {
        left.resolveNames(scope);
        right.resolveNames(scope);
    }
}