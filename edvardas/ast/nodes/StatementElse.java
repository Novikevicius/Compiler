package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementElse extends Statement {
    private StmtBody body;
    
    public StatementElse(StmtBody body) {
        this.body = body;
        addChildren(body);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("body", body);
    }
    @Override
    public void resolveNames(Scope s) 
    {
        body.resolveNames(s);
    }
    @Override
    public Node checkTypes() throws Exception 
    {
        body.checkTypes();
        return null;
    }
    @Override
    public int getLine()
    {
        return body.getLine();
    }
}