package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class Param {
    private Token name;
    private Type type;
    public Param(Token name, Type type) {
        this.name = name;
        this.type = type;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("type", type);
    }
}