package edvardas.ast.nodes;

import edvardas.UnaryOperator;
import edvardas.ast.ASTPrinter;
import edvardas.ast.nodes.Expression;

public class ExprUnary extends Expression {
    private Expression expr;
    private UnaryOperator operator;
    private boolean isPostfix;

    public ExprUnary(UnaryOperator operator, Expression expr) { 
        this(operator, expr, false);
    }

	public ExprUnary(UnaryOperator operator, Expression expr, boolean isPostfix) {
        this.operator = operator;
        this.expr = expr;
        this.isPostfix = isPostfix;
    }

    @Override
    public void print(ASTPrinter printer) throws Exception {
       printer.print("operator", operator);
       printer.print("expr", expr);
       if(operator == UnaryOperator.INCREMENT || operator == UnaryOperator.DECREMENT){
           printer.print("isPostfix", isPostfix);
       }
    }
    
}