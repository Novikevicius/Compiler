package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class DeclFn extends Decl {
    private Type returnType;
    private ArrayList<VarDeclaration> params;
    private StmtBody body;

    public DeclFn(Type retType, Token name, ArrayList<VarDeclaration> params, StmtBody body)
    {
        this.returnType = retType;
        this.name = name;
        this.params = params;
        this.body = body;
        addChildren(returnType);
        for (VarDeclaration param : params) {
            addChildren(param);
        }
        addChildren(body);
    }
    @Override
    public void print(ASTPrinter printer) throws Exception 
    {
        printer.print("name", name);
        printer.print("params", params);
        printer.print("returnType", returnType);
        printer.print("body", body);
    }
    @Override
    public void resolveNames(Scope parentScope)
    {
        Scope scope = new Scope(parentScope);
        params.forEach( (param) -> param.resolveNames(scope) );
        body.resolveNames(scope);
    }
    @Override
    public Node checkTypes()
    {
        params.forEach( (param) -> param.checkTypes() );
        body.checkTypes();
        return null;
    }
}