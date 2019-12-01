package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.ast.nodes.Expression;

public class ExprBinArithm extends ExprBinary {
    
    public ExprBinArithm(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node t1 = left.checkTypes();
        Node t2 = right.checkTypes();
        unifyTypes(t1, t2);
        return null;
    }
}