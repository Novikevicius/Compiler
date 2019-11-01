package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;

public class Program extends Node {
    private ArrayList<Decl> decl;

    public Program(ArrayList<Decl> decls) {
        decl = decls;
    }

    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("func", decl);
    }
    
}