package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;

public class ExprPrefix extends Expression {
    private Expression expr;
    private State operator;

    public ExprPrefix(State op, Expression expr) {
        this.expr = expr;
        this.operator = op;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("operator", operator);
        printer.print("expression", expr);
    }
}