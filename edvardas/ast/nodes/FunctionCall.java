package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

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
}