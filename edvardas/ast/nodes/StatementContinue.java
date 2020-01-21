package edvardas.ast.nodes;

import edvardas.Main;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementContinue extends Statement {
    private Token keyword;
    public StatementContinue(Token kw) {
        keyword = kw;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("stmtContinue", keyword);
    }
    @Override
    public void resolveNames(Scope s) throws Exception {
        Node ancestor = findAncestor(StatementWhile.class);
        if(ancestor != null) return;
        ancestor = findAncestor(StatementFor.class);
        if(ancestor != null) return;
        Main.error(Main.filename + ":" + keyword.getLine() + ": " + "continue is not inside a loop statement");
    }
    @Override
    public Node checkTypes()
    {
        // do nothing
        return null;
    }
    @Override
    public int getLine()
    {
        return keyword.getLine();
    }
}