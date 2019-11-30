package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.ast.nodes.Expression;

public class ExprBinArithm extends ExprBinary {
    
    public ExprBinArithm(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
}