package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class FunctionCall extends Expression {
    private Token name;
    private ArrayList<Expression> args;
    private Node target;

    public FunctionCall(Token name, ArrayList<Expression> args) {
        this.name = name;
        this.args = args;
        for (Expression arg : args) {
            addChildren(arg);
        }
    }

    public Node getTarget() {
        return target;
    }

    public void print(ASTPrinter printer) throws Exception {
        printer.print("name", name);
        printer.print("args", args);
    }

    @Override
    public void resolveNames(Scope scope) {
        target = scope.resolveName(name);
        args.forEach((arg) -> {
            try {
                ((Expression) arg).resolveNames(scope);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Node checkTypes() throws Exception{

        DeclFn func = (DeclFn)target;
        ArrayList<VarDeclaration> params = func.getParams();
        if(args.size() != params.size())
        {
            semanticError(name, "Number of arguments does not match number of function's parameters");
            return null;
        }
        for( int i = 0; i < args.size(); i++)
        {
            Node tArg = ((Expression) args.get(i)).checkTypes();
            Node tParam = params.get(i).checkTypes();
            unifyTypes(tArg, tParam);
        }
        return func.getType();
    }
}