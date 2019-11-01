package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public class StatementElseIf extends Statement {
    private Expression condition;
    private StmtBody body;
    
    public StatementElseIf(Expression cond, StmtBody body) {
        condition = cond;
        this.body = body;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("condition", condition);
        printer.print("body", body);
    }
}