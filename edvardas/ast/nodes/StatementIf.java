package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;

public class StatementIf extends Statement {
    private Branch branch;
    private ArrayList<Branch> elseif;
    private StmtBody stmtElse;

    public StatementIf(Branch branch, ArrayList<Branch> elseif, StmtBody stmtElse) {
        this.branch = branch;
        this.elseif = elseif;
        this.stmtElse = stmtElse;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("branch", branch);
        printer.print("elseif", elseif);
        printer.print("else", stmtElse);
    }
}