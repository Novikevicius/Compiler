package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.State;
import edvardas.ast.nodes.Expression;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.IComparable;

public class ExprBinComparison extends ExprBinary {
    
    public ExprBinComparison(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node t1 = left.checkTypes();
        Node t2 = right.checkTypes();
        if( !(t1 instanceof IComparable) )
        {
            semanticError(this.getLine(), t1.getClass().getSimpleName() + " is not comparable");
            return null;
        }
        IComparable comparable = (IComparable) t1;
        if( !comparable.isComparable() )
        {
            semanticError(this.getLine(), comparable.getKind().getName() + " is not comparable");
            return null;
        }
        unifyTypes(t1, t2, this.getLine());
        return new TypePrim(State.TYPE_BOOL);
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        left.genCode(writer);
        right.genCode(writer);
        State type = null;
        try {
            type = ((TypePrim) left.checkTypes()).getKind();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (operator) {
            case EQUAL:
                writer.write(Instruction.EQ, type);
                break;
            case NOT_EQUAL:
                writer.write(Instruction.NEQ, type);
                break;
            case LESS:
                writer.write(Instruction.LESS, type);
                break;
            case MORE:
                writer.write(Instruction.MORE, type);
                break;
            case LESS_EQUAL:
                writer.write(Instruction.LEQ, type);
                break;
            case MORE_EQUAL:
                writer.write(Instruction.MEQ, type);
                break;
            default:
                break;
        }
    }
}