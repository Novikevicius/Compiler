package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;

public class StatementIf extends Statement {
    private Expression condition;
    private StmtBody body;
    private ArrayList<StatementElseIf> elseif;
    private StatementElse stmtElse;

    public StatementIf(Expression cond, StmtBody body, ArrayList<StatementElseIf> elseif, StatementElse stmtElse) {
        condition = cond;
        this.body = body;
        this.elseif = elseif;
        this.stmtElse = stmtElse;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("condition", condition);
        printer.print("body", body);
        printer.print("elseif", elseif);
        printer.print("else", stmtElse);
    }
}