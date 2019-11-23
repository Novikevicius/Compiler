package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementBreak extends Statement {
    private Token keyword;
    public StatementBreak(Token kw) {
        keyword = kw;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("stmtBreak", keyword);
    }
    @Override
    public void resolveNames(Scope s) {}
}