package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;

public class StmtBody extends Node {
    private ArrayList<Stmt> stmts;
    public StmtBody(ArrayList<Stmt> s) {
        stmts = s;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("stms", stmts);
    }
}