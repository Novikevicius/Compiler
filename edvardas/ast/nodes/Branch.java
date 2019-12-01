package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class Branch extends Node {
    private Expression condition;
    private StmtBody body;
    
    public Branch(Expression cond, StmtBody body) {
        condition = cond;
        this.body = body;
        addChildren(condition);
        addChildren(body);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("condition", condition);
        printer.print("body", body);
    }
    @Override
    public void resolveNames(Scope parentScope) throws Exception
    {
        condition.resolveNames(parentScope);
        body.resolveNames(parentScope);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        TypePrim tBool = new TypePrim(State.TYPE_BOOL);
        Node tCond = condition.checkTypes();
        unifyTypes(tCond, tBool, this.getLine());
        body.checkTypes();
        return null;
    }
    @Override
    public boolean checkReturn()
    {
        return body.checkReturn();
    }
    @Override
    public int getLine()
    {
        return condition.getLine();
    }
}