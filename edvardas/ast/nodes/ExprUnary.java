package edvardas.ast.nodes;

import edvardas.State;
import edvardas.UnaryOperator;
import edvardas.ast.ASTPrinter;
import edvardas.ast.nodes.Expression;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class ExprUnary extends Expression {
    private Expression expr;
    private UnaryOperator operator;
    private boolean isPostfix;

    public ExprUnary(UnaryOperator operator, Expression expr) {
        this(operator, expr, false);
        addChildren(expr);
    }

    public ExprUnary(UnaryOperator operator, Expression expr, boolean isPostfix) {
        this.operator = operator;
        this.expr = expr;
        this.isPostfix = isPostfix;
    }

    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("operator", operator);
        printer.print("expr", expr);
        if (operator == UnaryOperator.INCREMENT || operator == UnaryOperator.DECREMENT) {
            printer.print("isPostfix", isPostfix);
        }
    }

    @Override
    public void resolveNames(Scope scope) throws Exception {
        expr.resolveNames(scope);
    }

    @Override
    public Node checkTypes() throws Exception {
        if (operator == UnaryOperator.NEGATION && expr instanceof ExprLiteral) {
            TypePrim t = (TypePrim) (expr.checkTypes());
            switch (t.getKind()) {
            case TYPE_INT:
            case TYPE_FLOAT:
                return t;
            default:
                break;
            }
        }
        if (expr instanceof ExprVar || expr instanceof ArrayElement) {
            return expr.checkTypes();
        }
        semanticError(this.getLine(), "Unary operation is not allowed for type: " + expr.getClass().getSimpleName());
        return null;
    }

    @Override
    public int getLine() {
        return expr.getLine();
    }

    @Override
    public void genCode(CodeWriter writer) {
        State type = null;
        try {
            type = ((TypePrim) checkTypes()).getKind();
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }
        switch (operator) {
            case NEGATION:
                try {
                    expr.genCode(writer);
                    writer.write(Instruction.NEG, null, type);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                break;
            case INCREMENT:
                expr.genCode(writer);
                if(isPostfix)
                    expr.genCode(writer);
                writer.write(Instruction.INC, null, type);
                if(isPostfix)
                {
                    if(expr instanceof ExprVar)
                        writer.write(Instruction.SET_L, ( (VarDeclaration) ((ExprVar)expr).getTarget().parent).stack_slot, type);
                }
                else
                {
                    if(expr instanceof ExprVar)
                        writer.write(Instruction.SET_L, ( (VarDeclaration) ((ExprVar)expr).getTarget().parent).stack_slot, type);
                    expr.genCode(writer);  
                }
                break;
            case DECREMENT:
                expr.genCode(writer);
                if(isPostfix)
                    expr.genCode(writer);
                writer.write(Instruction.DEC, null, type);
                if(isPostfix)
                {
                    if(expr instanceof ExprVar)
                    writer.write(Instruction.SET_L, ( (VarDeclaration) ((ExprVar)expr).getTarget().parent).stack_slot, type);
                }
                else
                {
                    if(expr instanceof ExprVar)
                    writer.write(Instruction.SET_L, ( (VarDeclaration) ((ExprVar)expr).getTarget().parent).stack_slot, type);
                    expr.genCode(writer);  
                }
                break;
            default:
                break;
        }
    }
}