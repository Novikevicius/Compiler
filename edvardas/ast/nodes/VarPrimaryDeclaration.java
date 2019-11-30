package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class VarPrimaryDeclaration extends VarDeclaration {
    public VarPrimaryDeclaration(Token name, Type type) {
        this.name = name;
        this.type = type;
        addChildren(type);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("type", type);
    }
    @Override
    public void resolveNames(Scope parentScope)
    {
        parentScope.add(name, this);
    }
    @Override
    public Node checkTypes()
    {
        // do nothing
        return type;
    }
}