package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;

public class StatementVarDecl extends Statement {
    private VarDeclaration declaration;
    
    public StatementVarDecl(VarDeclaration declaration) {
        this.declaration = declaration;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("decl", declaration);
    }
}