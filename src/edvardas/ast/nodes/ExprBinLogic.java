package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.State;
import edvardas.ast.nodes.Expression;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;

public class ExprBinLogic extends ExprBinary {
    
    public ExprBinLogic(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node tBool = new TypePrim(State.TYPE_BOOL);
        Node t1 = left.checkTypes();
        Node t2 = right.checkTypes();
        unifyTypes(t1, tBool, left.getLine());
        unifyTypes(t2, tBool, right.getLine());
        return tBool;
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
            case AND:
                writer.write(Instruction.AND, type);
                break;
            case OR:
                writer.write(Instruction.NEQ, type);
                break;
            default:
                break;
        }
    }
}