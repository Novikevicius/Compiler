package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class StatementContinue extends Statement {
    private Token keyword;
    public StatementContinue(Token kw) {
        keyword = kw;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("stmtContinue", keyword);
    }
}