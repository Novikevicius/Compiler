package edvardas.ast.nodes;

import edvardas.State;
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
        State type = null;
        if(target.parent instanceof Decl)
            writer.write(Instruction.GET_L, ((Decl)target.parent).stack_slot, ((TypePrim)checkTypes()).getKind());
        else if(target.parent instanceof StatementVarDecl)
        {
            Node n = checkTypes();
            if(n instanceof ArrayDeclaration){
                type = ((TypePrim)((ArrayDeclaration)n).getType()).getKind();
            }else{
                type = ((TypePrim)checkTypes()).getKind();
            }
            writer.write(Instruction.GET_L, ((StatementVarDecl)target.parent).getDeclaration().stack_slot, type);
        }
        else if(target.parent instanceof ArrayDeclaration)
            writer.write(Instruction.GET_L, ((ArrayDeclaration)target.parent).stack_slot, ((TypePrim)checkTypes()).getKind());

    }
}