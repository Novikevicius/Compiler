package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public class StatementExpression extends Statement {
    private Expression expr;
    public StatementExpression(Expression expr) {
        this.expr = expr;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("expr", expr);
    }
}