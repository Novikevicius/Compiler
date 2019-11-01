package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class VarPrimaryDeclaration extends VarDeclaration {
    private Type type;
    private Token name;
    public VarPrimaryDeclaration(Token name, Type type) {
        this.name = name;
        this.type = type;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("type", type);
    }
}