package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
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
    @Override
    public Node checkTypes() throws Exception {
        args.forEach((arg) -> {
            try {
                ((Expression) arg).checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return null;
    }
    @Override
    public int getLine()
    {
        return keyword.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        for (Expression expression : args) {
            expression.genCode(writer);
        }
        writer.write(Instruction.WRITE, args.size(), State.TYPE_INT);
    }
}