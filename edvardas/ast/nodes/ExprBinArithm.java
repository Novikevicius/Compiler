package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.ast.nodes.Expression;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;

public class ExprBinArithm extends ExprBinary {
    
    public ExprBinArithm(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node t1 = left.checkTypes();
        Node t2 = right.checkTypes();
        if(t1 instanceof TypePrim && t2 instanceof TypePrim)
        {
            TypePrim t = (TypePrim)t1;
            switch(t.getKind())
            {
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
    public void genCode(CodeWriter writer)
    {
        left.genCode(writer);
        right.genCode(writer);
        switch (operator) {
            case ADD:
                writer.write(Instruction.ADD);
                break;
            case MULTIPLICATION:
                writer.write(Instruction.MUL);
                break;
            case DIVISION:
                writer.write(Instruction.DIV);
                break;
            case MINUS:
                writer.write(Instruction.SUB);
                break;
            case EQUAL:
                writer.write(Instruction.EQ);
                break;
            case NOT_EQUAL:
                writer.write(Instruction.NEQ);
                break;
            case LESS:
                writer.write(Instruction.LESS);
                break;
            case MORE:
                writer.write(Instruction.MORE);
                break;
            case LESS_EQUAL:
                writer.write(Instruction.LEQ);
                break;
            case MORE_EQUAL:
                writer.write(Instruction.MEQ);
                break;
            case EXPONENTIAL:
                writer.write(Instruction.EXP);
                break;
            default:
                break;
        }
    }
}