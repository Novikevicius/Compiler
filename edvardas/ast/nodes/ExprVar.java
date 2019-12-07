package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class ExprVar extends Expression {
    private Token name;
    private Node target;

    public ExprVar(Token name) {
        this.name = name;
    }
    public Node getTarget()
    {
        return target;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
    }
    @Override
    public void resolveNames(Scope scope)
    {
        target = scope.resolveName(name);
    }
    @Override
    public Node checkTypes()
    {
        return target;
    }
    @Override
    public int getLine()
    {
        return name.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        writer.write(Instruction.GET_L, ((Decl)target).stack_slot, ((TypePrim)checkTypes()).getKind());
    }
}