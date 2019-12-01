package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class DeclFn extends Decl {
    private ArrayList<VarDeclaration> params;
    private StmtBody body;

    public DeclFn(Type retType, Token name, ArrayList<VarDeclaration> params, StmtBody body) {
        this.type = retType;
        this.name = name;
        this.params = params;
        this.body = body;
        addChildren(type);
        for (VarDeclaration param : params) {
            addChildren(param);
        }
        addChildren(body);
    }
    public ArrayList<VarDeclaration> getParams()
    {
        return params;
    }
    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("name", name);
        printer.print("params", params);
        printer.print("returnType", type);
        printer.print("body", body);
    }

    @Override
    public void resolveNames(Scope parentScope) {
        Scope scope = new Scope(parentScope);
        params.forEach((param) -> {
            try {
                param.resolveNames(scope);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        body.resolveNames(scope);
    }

    @Override
    public Node checkTypes() throws Exception {
        params.forEach((param) -> {
            try {
                param.checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        body.checkTypes();
        if(getType().getKind() != State.TYPE_VOID){
            if( !body.checkReturn() ){
                semanticError(name, "Function '" + name.getIdentifier() + "' does not have a return statement");
            }
        }
        return null;
    }
}