package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class StatementIf extends Statement {
    private Branch branch;
    private ArrayList<Branch> elseif;
    private StmtBody stmtElse;

    public StatementIf(Branch branch, ArrayList<Branch> elseif, StmtBody stmtElse) {
        this.branch = branch;
        this.elseif = elseif;
        this.stmtElse = stmtElse;;
        addChildren(branch);
        for (Branch eif : elseif) {
            addChildren(eif);
        }
        addChildren(stmtElse);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("branch", branch);
        printer.print("elseif", elseif);
        printer.print("else", stmtElse);
    }
    @Override
    public void resolveNames(Scope scope)
    {
        branch.resolveNames(scope);
        elseif.forEach( (branch) -> ((Branch)branch).resolveNames(scope) );
        if(stmtElse != null)
            stmtElse.resolveNames(scope);
    }
}