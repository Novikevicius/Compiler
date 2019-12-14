package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.State;
import edvardas.ast.nodes.Expression;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;

public class ExprBinEquality extends ExprBinary {
    
    public ExprBinEquality(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node t1 = left.checkTypes();
        Node t2 = right.checkTypes();
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
            default:
                break;
        }
    }
}