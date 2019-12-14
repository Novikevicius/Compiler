package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.IComparable;

public abstract class Type extends Node implements IComparable {
    public abstract void print(ASTPrinter printer) throws Exception;
}