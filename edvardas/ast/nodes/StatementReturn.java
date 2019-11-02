package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class StatementReturn extends Statement {
    private Token keyword;
    private Expression value;
    
    public StatementReturn(Token keyword, Expression value) {
        this.keyword = keyword;
        this.value = value;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("keyword", keyword);
        printer.print("value", value);
    }
}