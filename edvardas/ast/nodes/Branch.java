package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class Branch extends Node {
    private Expression condition;
    private StmtBody body;
    
    public Branch(Expression cond, StmtBody body) {
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
    public void resolveNames(Scope parentScope)
    {
        condition.resolveNames(parentScope);
        body.resolveNames(parentScope);
    }
}