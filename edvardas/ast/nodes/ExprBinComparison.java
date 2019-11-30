package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.ast.nodes.Expression;

public class ExprBinComparison extends ExprBinary {
    
    public ExprBinComparison(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
}