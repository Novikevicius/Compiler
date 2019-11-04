package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public class Branch extends Node {
    private Expression condition;
    private StmtBody body;
    
    public Branch(Expression cond, StmtBody body) {
        condition = cond;
        this.body = body;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("condition", condition);
        printer.print("body", body);
    }
}