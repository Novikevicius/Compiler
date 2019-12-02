package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class Program extends Node {
    private ArrayList<Decl> decl;

    public Program(ArrayList<Decl> decls) {
        decl = decls;
        decls.forEach((d) -> addChildren(d));
    }

    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("func", decl);
    }

    @Override
    public void resolveNames(Scope scope) {
        decl.forEach((decl) -> scope.add(((Decl) decl).getName(), decl));
        decl.forEach((decl) -> {
            try {
                decl.resolveNames(scope);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Node checkTypes() throws Exception {
        decl.forEach((decl) -> {
            try {
                decl.checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return null;
    }
    public void checkMain(Scope scope) throws Exception
    {
        Node main = scope.getMember("main");
        if(main == null)
        {
            semanticError(main.getLine(), "main function does not exist");
        }
        DeclFn mainFn = (DeclFn) main;

        if(mainFn.getParams().size() > 0)
        {
            semanticError(main.getLine(), "main should not have any parameters");
        }
    }
    
}