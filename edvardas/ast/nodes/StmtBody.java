package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;

public class StmtBody extends Node {
    private ArrayList<Statement> stmts;
    public StmtBody(ArrayList<Statement> s) {
        stmts = s;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("statements", stmts);
    }
}