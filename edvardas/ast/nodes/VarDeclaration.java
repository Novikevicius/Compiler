package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public abstract class VarDeclaration extends Decl {
    public abstract void print(ASTPrinter printer) throws Exception;
}