package edvardas.ast.nodes;

import edvardas.Main;
import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementReturn extends Statement {
    private Token keyword;
    private Expression value;
    
    public StatementReturn(Token keyword, Expression value) {
        this.keyword = keyword;
        this.value = value;
        addChildren(value);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("keyword", keyword);
        printer.print("value", value);
    }
    @Override
    public void resolveNames(Scope s) throws Exception 
    {
        value.resolveNames(s);
    }
    @Override
    public Node checkTypes() throws Exception 
    {
        Type func = ((DeclFn)findAncestor(DeclFn.class)).getType();
        if(value == null && func.getKind() != State.TYPE_VOID)
        {
            semanticError(keyword, "function's return type should be " + func.getKind());
        }
        unifyTypes(func, value.checkTypes());
        return null;
    }
}