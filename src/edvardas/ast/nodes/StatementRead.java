package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class StatementRead extends Statement {
    private Token keyword;
    private ArrayList<ReadArgument> args;

    public StatementRead(Token keyword, ArrayList<ReadArgument> args) {
        this.keyword = keyword;
        this.args = args;
        for (ReadArgument arg : args) {
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
                ((ReadArgument) arg).resolveNames(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Node checkTypes() throws Exception {
        args.forEach((arg) -> {
            try {
                ((ReadArgument) arg).checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return null;
    }

    @Override
    public int getLine() {
        return keyword.getLine();
    }

    @Override
    public void genCode(CodeWriter writer) {
        try {
            for (ReadArgument arg : args) {
                arg.genCode(writer);
                writer.write(Instruction.READ, State.TYPE_VOID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}