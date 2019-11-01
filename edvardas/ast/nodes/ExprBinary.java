package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.ast.nodes.Expression;

public class ExprBinary extends Expression {
    private Expression left;
    private State operator;
    private Expression right;
    public ExprBinary(State operator, Expression left, Expression right) {
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