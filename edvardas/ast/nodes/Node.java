package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public abstract class Node {
    public abstract void print(ASTPrinter printer) throws Exception;
}