package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public abstract class Decl extends Node {
    protected Token name;

    public abstract void print(ASTPrinter printer) throws Exception;
    public Token getName()
    {
        return name;
    }
}