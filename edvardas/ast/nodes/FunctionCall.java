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
    public Node checkTypes() {
        args.forEach((arg) -> {
            try {
                ((Expression) arg).checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return target;
    }
}