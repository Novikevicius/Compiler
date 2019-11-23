package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class TypePrim extends Type {
    private State kind;
    public TypePrim(State kind) {
        this.kind = kind;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("kind", kind);
    }
    @Override
    public void resolveNames(Scope s) 
    {
        
    }
}