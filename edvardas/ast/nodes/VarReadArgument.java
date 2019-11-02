package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class VarReadArgument extends ReadArgument {
    private Token name;

    public VarReadArgument(Token name) {
        this.name = name;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
    }
}