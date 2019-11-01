package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class ExprLiteral extends Expression {
    private Token value;
    public ExprLiteral(Token value) {
        this.value = value;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("value", value);
    }
}