package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class ArrayElement extends Expression {
    private Token name;
    private Expression index;
    public ArrayElement(Token name, Expression index) {
        this.name = name;
        this.index = index;
        ArrayList<Node> nodes = new ArrayList<Node>(1);
        nodes.add(this);
        addChildren(nodes);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("index", index);
    }
    @Override
    public void resolveNames(Scope scope)
    {
        scope.resolveName(name);
        index.resolveNames(scope);
    }
}