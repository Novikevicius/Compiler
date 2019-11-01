package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class ArrayDeclaration extends VarDeclaration {
    private Type type;
    private Token name;
    private Token size;

    public ArrayDeclaration(Type type, Token name, Token size) {
        this.type = type;
        this.name = name;
        this.size = size;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("type", type);
        printer.print("name", name);
        printer.print("size", size);
    }
}