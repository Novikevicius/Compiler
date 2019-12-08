package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;
import edvardas.ast.nodes.AssignmentTarget;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;

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

    public void print(ASTPrinter printer) throws Exception {
        printer.print("target", (Node) target);
        printer.print("operator", operator);
        printer.print("value", value);
    }

    @Override
    public void resolveNames(Scope scope) throws Exception {
        target.resolveNames(scope);
        value.resolveNames(scope);
    }

    @Override
    public Node checkTypes() throws Exception {
        Node t1 = target.checkTypes();
        Node t2 = value.checkTypes();
        unifyTypes(t1, t2, this.getLine());
        return null;
    }

    @Override
    public int getLine() {
        return target.getLine();
    }

    @Override
    public void genCode(CodeWriter writer) {
        value.genCode(writer);
        try {
            if(operator != State.ASSIGN_OP)
            {
                if (target instanceof VarTarget)
                    writer.write(Instruction.GET_L, ((VarDeclaration) (((ExprVar) ((VarTarget) target).getVar())
                            .getTarget().parent)).stack_slot, ((TypePrim) target.checkTypes()).getKind());
                switch(operator)
                {
                    case ASSIGN_OP_PLUS:
                        writer.write(Instruction.ADD, ((TypePrim) target.checkTypes()).getKind());
                        break;
                    case ASSIGN_OP_MINUS:
                        writer.write(Instruction.SUB, ((TypePrim) target.checkTypes()).getKind());
                        break;
                    case ASSIGN_OP_MULT:
                        writer.write(Instruction.MUL, ((TypePrim) target.checkTypes()).getKind());
                        break;
                    case ASSIGN_OP_DIV:
                        writer.write(Instruction.DIV, ((TypePrim) target.checkTypes()).getKind());
                        break;
                    default:
                        System.err.println("Unexpected assign operator: " + operator.toString());
                        break;
                }
            }              
        } catch (Exception e) {
            e.printStackTrace();
        }
        target.genCode(writer);
    }
}