package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementWrite extends Statement {
    private Token keyword;
    private ArrayList<Expression> args;

    public StatementWrite(Token keyword, ArrayList<Expression> args) {
        this.keyword = keyword;
        this.args = args;
        for (Expression arg : args) {
            addChildren(arg);
        }
    }

    public void print(ASTPrinter printer) throws Exception {
        printer.print("keyword", keyword);
        printer.print("args", args);
    }

    @Override
    public void resolveNames(Scope s) {
        args.forEach((arg) -> {
            try {
                ((Expression) arg).resolveNames(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}