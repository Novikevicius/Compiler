package edvardas.ast.nodes;

import edvardas.BinaryOperator;
import edvardas.State;
import edvardas.ast.nodes.Expression;
import edvardas.parser.IComparable;

public class ExprBinComparison extends ExprBinary {
    
    public ExprBinComparison(BinaryOperator operator, Expression left, Expression right) {
        super(operator, left, right);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node t1 = left.checkTypes();
        Node t2 = right.checkTypes();
        if( !(t1 instanceof IComparable) )
        {
            semanticError(null, t1.getClass().getSimpleName() + " is not comparable");
            return null;
        }
        IComparable comparable = (IComparable) t1;
        if( !comparable.isComparable() )
        {
            semanticError(null, comparable.getKind() + " is not comparable");
            return null;
        }
        unifyTypes(t1, t2, this.getLine());
        return new TypePrim(State.TYPE_BOOL);
    }
}