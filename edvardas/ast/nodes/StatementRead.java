package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementRead extends Statement {
    private Token keyword;
    private ArrayList<ReadArgument> args;
    
    public StatementRead(Token keyword, ArrayList<ReadArgument> args) {
        this.keyword = keyword;
        this.args = args;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("keyword", keyword);
        printer.print("args", args);
    }
    @Override
    public void resolveNames(Scope s) 
    {
        args.forEach((arg) -> ((ReadArgument)arg).resolveNames(s));
    }
}