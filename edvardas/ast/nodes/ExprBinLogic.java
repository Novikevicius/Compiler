package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.State;
import edvardas.ast.nodes.Expression;

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
        unifyTypes(t1, tBool);
        unifyTypes(t2, tBool);
        return tBool;
    }
}