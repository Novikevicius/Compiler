package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.State;
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
            int line = 0;
            if(decl.size() > 0)
                line = decl.get(0).getLine();
            semanticError(line, "main function is not found");
            return;
        }
        DeclFn mainFn = (DeclFn) main;

        if(mainFn.getParams().size() > 0)
        {
            semanticError(main.getLine(), "main should not have any parameters");
            return;
        }
        if(mainFn.getType().getKind() != State.TYPE_INT)
        {
            semanticError(main.getLine(), "main function's return type should be int");
            return;
        }
    }
    
}