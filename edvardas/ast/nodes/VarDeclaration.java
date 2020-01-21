package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public abstract class VarDeclaration extends Decl {
    protected Type type;
    public abstract void print(ASTPrinter printer) throws Exception;
    public Type getType()
    {
        return type;
    }
    @Override
    public int getLine()
    {
        return name.getLine();
    }
}