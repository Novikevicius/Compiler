package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class ArrayElement extends Expression {
    private Node type;
    private Token name;
    private Expression index;
    public ArrayElement(Token name, Expression index) {
        this.name = name;
        this.index = index;
        addChildren(index);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("index", index);
    }
    @Override
    public void resolveNames(Scope scope) throws Exception
    {
        type = scope.resolveName(name);
        index.resolveNames(scope);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        index.checkTypes();
        return type.checkTypes();
    }
}