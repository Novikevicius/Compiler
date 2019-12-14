package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class VarTarget extends AssignmentTarget {
    private ExprVar var;

    public VarTarget(ExprVar var)
    {
        this.var = var;
        addChildren(var);
    }
    public ExprVar getVar()
    {
        return var;
    }
    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("var", var);
    }

    @Override
    public void resolveNames(Scope scope) {
        var.resolveNames(scope);
    }
    
    @Override
    public Node checkTypes() throws Exception{
        if(var.getTarget() instanceof ArrayDeclaration){
            semanticError(getLine(), "Array cannot be assignment's target");
        }
        return var.checkTypes();
    }
    @Override
    public int getLine()
    {
        return var.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        writer.write(Instruction.SET_L, ( (VarDeclaration) (((ExprVar)var).getTarget().parent)).stack_slot, ((TypePrim)var.checkTypes()).getKind());
    }
}