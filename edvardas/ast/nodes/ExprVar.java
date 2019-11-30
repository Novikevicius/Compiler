package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class ExprVar extends Expression {
    private Token name;
    private Node target;

    public ExprVar(Token name) {
        this.name = name;
    }
    public Node getTarget()
    {
        return target;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
    }
    @Override
    public void resolveNames(Scope scope)
    {
        target = scope.resolveName(name);
    }
    @Override
    public Node checkTypes()
    {
        return target;
    }
}