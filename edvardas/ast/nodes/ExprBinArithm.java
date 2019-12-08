package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.State;
import edvardas.ast.nodes.Expression;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;

public class ExprBinArithm extends ExprBinary {

    public ExprBinArithm(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }

    @Override
    public Node checkTypes() throws Exception {
        Node t1 = left.checkTypes();
        Node t2 = right.checkTypes();
        if (t1 instanceof TypePrim && t2 instanceof TypePrim) {
            TypePrim t = (TypePrim) t1;
            switch (t.getKind()) {
            case TYPE_INT:
            case TYPE_FLOAT:
            case TYPE_STRING:
                break;
            default:
                semanticError(this.getLine(), "Binary operation is not allowed for " + t.getKind().getName());
                return null;
            }
        }
        unifyTypes(t1, t2, this.getLine());
        return t1;
    }

    @Override
    public void genCode(CodeWriter writer) {
        left.genCode(writer);
        right.genCode(writer);
        State type = null;
        try {
            type = ((TypePrim) left.checkTypes()).getKind();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (operator) {
            case ADD:
                writer.write(Instruction.ADD, type);
                break;
            case MULTIPLICATION:
                writer.write(Instruction.MUL, type);
                break;
            case DIVISION:
                writer.write(Instruction.DIV, type);
                break;
            case MINUS:
                writer.write(Instruction.SUB, type);
                break;
            case EXPONENTIAL:
                writer.write(Instruction.EXP, type);
                break;
            default:
                break;
        }
    }
}