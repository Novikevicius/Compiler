package edvardas.ast.nodes;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class TypePrim extends Type {
    private State kind;
    private Token token;
    public TypePrim(State kind) {
        this(kind, null);
    }
    public TypePrim(State kind, Token t) {
        this.kind = kind;
        this.token = t;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("kind", kind);
    }
    @Override
    public void resolveNames(Scope s) 
    {
        
    }
    public State getKind() { return kind;}
}