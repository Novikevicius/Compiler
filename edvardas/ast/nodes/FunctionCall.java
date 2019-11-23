package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class FunctionCall extends Expression {
    private Token name;
    private ArrayList<Expression> args;
    public FunctionCall(Token name, ArrayList<Expression> args) {
        this.name = name;
        this.args = args;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("args", args);
    }
    @Override
    public void resolveNames(Scope scope)
    {
        scope.resolveName(name);
        args.forEach( (arg) -> ((Expression)arg).resolveNames(scope) );
    }
}