package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public class StatementElse extends Statement {
    private StmtBody body;
    
    public StatementElse(StmtBody body) {
        this.body = body;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("body", body);
    }
}