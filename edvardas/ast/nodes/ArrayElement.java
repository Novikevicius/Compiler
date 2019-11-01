package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class ArrayElement extends Expression {
    private Token name;
    private Expression index;
    public ArrayElement(Token name, Expression index) {
        this.name = name;
        this.index = index;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("index", index);
    }
}