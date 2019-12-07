package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public abstract class Decl extends Node {
    protected Token name;
    protected Type type;
    protected static int stack_slot_index;
    public int stack_slot;

    public abstract void print(ASTPrinter printer) throws Exception;
    public Token getName()
    {
        return name;
    }
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