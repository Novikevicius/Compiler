package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public class ExprNegation extends Expression {
    private Expression expr;
    public ExprNegation(Expression expr) {
        this.expr = expr;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("negation", expr);
    }
}