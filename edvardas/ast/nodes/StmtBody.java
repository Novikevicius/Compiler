package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StmtBody extends Node {
    private ArrayList<Statement> stmts;

    public StmtBody(ArrayList<Statement> s) {
        stmts = s;
        for (Statement stmt : stmts) {
            addChildren(stmt);
        }
    }

    public void print(ASTPrinter printer) throws Exception {
        printer.print("statements", stmts);
    }

    @Override
    public void resolveNames(Scope parentScope) {
        Scope scope = new Scope(parentScope);
        stmts.forEach((stmt) -> ((Statement) stmt).resolveNames(scope));
    }

    @Override
    public Node checkTypes() throws Exception {
        stmts.forEach((stmt) -> {
            try {
                ((Statement) stmt).checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return null;
    }
}