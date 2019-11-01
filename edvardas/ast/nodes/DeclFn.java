package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class DeclFn extends Decl {
    private Type returnType;
    private Token name;
    private ArrayList<Param> params;
    private StmtBody body;

    public DeclFn(Type retType, Token name, ArrayList<Param> params, StmtBody body)
    {
        this.returnType = retType;
        this.name = name;
        this.params = params;
        this.body = body;
    }
    @Override
    public void print(ASTPrinter printer) throws Exception 
    {
        printer.print("name", name);
        printer.print("params", params);
        printer.print("returnType", returnType);
        printer.print("body", body);
    }
}