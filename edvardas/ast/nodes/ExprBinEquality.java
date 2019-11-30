package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.ast.nodes.Expression;

public class ExprBinEquality extends ExprBinary {
    
    public ExprBinEquality(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
}