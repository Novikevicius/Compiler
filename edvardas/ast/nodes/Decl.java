package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public abstract class Decl extends Node {
    protected Token name;
    protected Type type;

    public abstract void print(ASTPrinter printer) throws Exception;
    public Token getName()
    {
        return name;
    }
    public Type getType()
    {
        return type;
    }
}