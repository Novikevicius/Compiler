package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class ExprLiteral extends Expression {
    private Token value;
    public ExprLiteral(Token value) {
        this.value = value;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("value", value);
    }
    @Override
    public void resolveNames(Scope s) {
        // do nothings
    }
    @Override
    public Node checkTypes() throws Exception
    {
        switch(value.getType())
        {
            case TYPE_BOOL:
            case TYPE_CHAR:
            case TYPE_FLOAT:
            case TYPE_INT:
            case TYPE_STRING:
                return new TypePrim(value.getType());
            default:
                throw new Exception("Should not happen: type: " + value.getType().toString());
        }
    }
}