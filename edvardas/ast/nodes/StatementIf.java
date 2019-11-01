package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public class StatementIf extends Statement {
    private Expression condition;
    private StmtBody body;
    
    public StatementIf(Expression cond, StmtBody body) {
        condition = cond;
        this.body = body;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("condition", condition);
        printer.print("body", body);
    }
}