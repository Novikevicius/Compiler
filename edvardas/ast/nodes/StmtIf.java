package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public abstract class StmtIf extends Node {
    public abstract void print(ASTPrinter printer) throws Exception;
}