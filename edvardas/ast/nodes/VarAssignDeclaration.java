package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class VarAssignDeclaration extends VarDeclaration {
    private Expression expr;

    public VarAssignDeclaration(Token name, Type type, Expression expr) {
        this.name = name;
        this.type = type;
        this.expr = expr;
        addChildren(type);
        addChildren(expr);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("type", type);
        printer.print("name", name);
        printer.print("expr", expr);
    }
    @Override
    public void resolveNames(Scope parentScope)
    {
        parentScope.add(name, this);
        expr.resolveNames(parentScope);
    }
}