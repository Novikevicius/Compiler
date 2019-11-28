package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class VarTarget extends AssignmentTarget {
    private ExprVar var;

    public VarTarget(ExprVar var)
    {
        this.var = var;
    }
    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("var", var);
    }

    @Override
    public void resolveNames(Scope scope) {
        var.resolveNames(scope);
    }
    
}