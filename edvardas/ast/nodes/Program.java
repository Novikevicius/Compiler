package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class Program extends Node {
    private ArrayList<Decl> decl;

    public Program(ArrayList<Decl> decls) {
        decl = decls;
        decls.forEach ((d) -> addChildren(d));
    }

    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("func", decl);
    }
    
    @Override
    public void resolveNames(Scope scope)
    {
        decl.forEach((decl) -> scope.add(((Decl)decl).getName(), decl));
        decl.forEach((decl) -> decl.resolveNames(scope));
    }
    
}