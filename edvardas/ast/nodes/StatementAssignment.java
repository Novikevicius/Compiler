package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;
import edvardas.ast.nodes.AssignmentTarget;

public class StatementAssignment extends Statement {
    private AssignmentTarget target;
    private State operator;
    private Expression value;
    
    public StatementAssignment(AssignmentTarget target, State operator, Expression value) {
        this.target = target;
        this.operator = operator;
        this.value = value;
        addChildren(target);
        addChildren(value);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("target", (Node)target);
        printer.print("operator", operator);
        printer.print("value", value);
    }
    @Override
    public void resolveNames(Scope scope) throws Exception
    {
        target.resolveNames(scope);
        value.resolveNames(scope);
    }
}