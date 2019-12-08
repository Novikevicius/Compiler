package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.parser.Scope;

public class StatementVarDecl extends Statement {
    private VarDeclaration declaration;

    public StatementVarDecl(VarDeclaration declaration) {
        this.declaration = declaration;
        addChildren(declaration);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("decl", declaration);
    }
    @Override
    public void resolveNames(Scope scope) throws Exception
    {
        declaration.resolveNames(scope);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        return declaration.checkTypes();
    }
    @Override
    public int getLine()
    {
        return declaration.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        declaration.genCode(writer);
    }
}