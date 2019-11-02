package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class StatementBreak extends Statement {
    private Token keyword;
    public StatementBreak(Token kw) {
        keyword = kw;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("stmtBreak", keyword);
    }
}