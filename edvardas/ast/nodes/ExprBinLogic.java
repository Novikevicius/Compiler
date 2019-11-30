package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.ast.nodes.Expression;

public class ExprBinLogic extends ExprBinary {
    
    public ExprBinLogic(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
}