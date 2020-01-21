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
        if(t1 instanceof TypePrim && t2 instanceof TypePrim)
        {
            TypePrim t = (TypePrim)t1;
            switch(t.getKind())
            {
                case TYPE_INT:
                case TYPE_FLOAT:
                case TYPE_STRING:
                    break;
                default:
                    semanticError(this.getLine(), "Binary operation is not allowed for " + t.getKind().getName());
                    return null;
            }
        }
        unifyTypes(t1, t2, this.getLine());
        return t1;
    }
}