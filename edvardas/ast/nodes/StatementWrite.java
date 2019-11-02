package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class StatementWrite extends Statement {
    private Token keyword;
    private ArrayList<Expression> args;
    
    public StatementWrite(Token keyword, ArrayList<Expression> args) {
        this.keyword = keyword;
        this.args = args;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("keyword", keyword);
        printer.print("args", args);
    }
}