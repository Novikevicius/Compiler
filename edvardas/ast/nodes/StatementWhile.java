package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementWhile extends Statement {
    private Expression condition;
    private StmtBody body;
    
    public StatementWhile(Expression cond, StmtBody body) {
        condition = cond;
        this.body = body;
        addChildren(condition);
        addChildren(body);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("condition", condition);
        printer.print("body", body);
    }
    @Override
    public void resolveNames(Scope s) 
    {
        condition.resolveNames(s);
        body.resolveNames(s);
    }
}