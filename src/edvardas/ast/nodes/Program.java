package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.codeGeneration.Label;
import edvardas.parser.Scope;

public class Program extends Node {
    private ArrayList<Decl> decl;
    private DeclFn mainFn;
    private Label main_label;

    public Program(ArrayList<Decl> decls) {
        decl = decls;
        decls.forEach((d) -> addChildren(d));
        main_label = new Label();
    }
    public Label getMainLabel(){
        return main_label;
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
            int line = 1;
            if(decl.size() > 0)
                line = decl.get(0).getLine();
            semanticError(line, "main function is not found");
            return;
        }
        mainFn = (DeclFn) main;

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
    @Override
    public void genCode(CodeWriter writer)
    {
        Label exit = new Label();
        writer.write(Instruction.CALL_BEGIN, exit, State.TYPE_INT);
        ArrayList<Object> ops = new ArrayList<Object>(2);
        ops.add(main_label);
        ops.add(0);
        writer.write(Instruction.CALL, ops, State.TYPE_INT);
        writer.placeLabel(exit);
        writer.write(Instruction.EXIT, null, State.TYPE_VOID);
        writer.placeLabel(main_label);
        mainFn.genCode(writer);
        for(Decl d: decl)
        {
            if(d.getName().equals(mainFn.getName()))
                continue;
            d.genCode(writer);
        }
    }
    
}