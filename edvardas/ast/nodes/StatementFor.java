package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public class StatementFor extends Statement {
    private VarDeclaration initialization;
    private Expression condition;
    private StatementAssignment afterthought;
    private StmtBody body;
    
    public StatementFor(VarDeclaration initialization, Expression cond, StatementAssignment afterthought, StmtBody body) {
        condition = cond;
        this.initialization = initialization;
        this.afterthought = afterthought;
        this.body = body;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("initialization", initialization);
        printer.print("condition", condition);
        printer.print("afterthought", afterthought);
        printer.print("body", body);
    }
}