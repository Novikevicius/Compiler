package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class VarAssignDeclaration extends VarDeclaration {
    private Type type;
    private Token name;
    private Expression expr;

    public VarAssignDeclaration(Token name, Type type, Expression expr) {
        this.name = name;
        this.type = type;
        this.expr = expr;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("type", type);
        printer.print("name", name);
        printer.print("expr", expr);
    }
}