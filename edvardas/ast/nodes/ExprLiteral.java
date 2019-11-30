package edvardas.ast.nodes;

import edvardas.State;
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
            case LIT_TRUE:
            case LIT_FALSE:
                return new TypePrim(State.TYPE_BOOL);
            case TYPE_CHAR:
            case CHAR_LITERAL:
                return new TypePrim(State.TYPE_CHAR);
            case TYPE_FLOAT:
            case FLOAT_LITERAL:
                return new TypePrim(State.TYPE_FLOAT); // TODO check for float literal exp
            case TYPE_INT:
            case INT_LITERAL:
                return new TypePrim(State.TYPE_INT);
            case TYPE_STRING:
            case STRING_LITERAL:
                return new TypePrim(State.TYPE_STRING);
            default:
                throw new Exception("Should not happen: type: " + value.getType().toString());
        }
    }
}