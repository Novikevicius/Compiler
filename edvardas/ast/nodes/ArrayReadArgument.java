package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class ArrayReadArgument extends ReadArgument {
    private Token name;
    private Expression index;

    public ArrayReadArgument(Token name, Expression index) {
        this.name = name;
        this.index = index;
        addChildren(index);
    }

    public void print(ASTPrinter printer) throws Exception {
        printer.print("name", name);
        printer.print("index", index);
    }

    @Override
    public void resolveNames(Scope scope) throws Exception {
        scope.resolveName(name);
        index.resolveNames(scope);
    }
    @Override
    public Node checkTypes() throws Exception {
        index.checkTypes();
        return null;
    }
    @Override
    public int getLine()
    {
        return name.getLine();
    }
}