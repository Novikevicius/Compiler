package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.State;
import edvardas.ast.nodes.Expression;

public class ExprBinEquality extends ExprBinary {
    
    public ExprBinEquality(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node t1 = left.checkTypes();
        Node t2 = right.checkTypes();
        unifyTypes(t1, t2);
        return new TypePrim(State.TYPE_BOOL);
    }
}