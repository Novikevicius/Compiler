package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class ExprVar extends Expression {
    private Token name;
    public ExprVar(Token name) {
        this.name = name;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
    }
    @Override
    public void resolveNames(Scope scope)
    {
        scope.resolveName(name);
    }
}