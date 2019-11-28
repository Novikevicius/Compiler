package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementExpression extends Statement {
    private Expression expr;
    public StatementExpression(Expression expr) {
        this.expr = expr;
        addChildren(expr);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("expr", expr);
    }
    @Override
    public void resolveNames(Scope scope)
    {
        expr.resolveNames(scope);
    }
}