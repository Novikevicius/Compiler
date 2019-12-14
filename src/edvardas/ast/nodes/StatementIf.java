package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Label;
import edvardas.parser.Scope;

public class StatementIf extends Statement {
    private Branch branch;
    private ArrayList<Branch> elseif;
    private StmtBody stmtElse;
    private Label elseStart;

    public StatementIf(Branch branch, ArrayList<Branch> elseif, StmtBody stmtElse) {
        this.branch = branch;
        this.elseif = elseif;
        this.stmtElse = stmtElse;
        elseStart = new Label();
        addChildren(branch);
        for (Branch eif : elseif) {
            addChildren(eif);
        }
        addChildren(stmtElse);
    }
    public Label getElseStart(){
        return elseStart;
    }
    public boolean hasElse(){
        return stmtElse != null;
    }
    public ArrayList<Branch> getElseifs(){
        return elseif;
    }
    public void print(ASTPrinter printer) throws Exception {
        printer.print("branch", branch);
        printer.print("elseif", elseif);
        printer.print("else", stmtElse);
    }

    @Override
    public void resolveNames(Scope scope) throws Exception {
        branch.resolveNames(scope);
        elseif.forEach((branch) -> {
            try {
                ((Branch) branch).resolveNames(scope);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if (stmtElse != null)
            stmtElse.resolveNames(scope);
    }

    @Override
    public Node checkTypes() throws Exception {
        branch.checkTypes();
        elseif.forEach((branch) -> {
            try {
                ((Branch) branch).checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if(stmtElse != null)
            stmtElse.checkTypes();
        return null;
    }
    @Override
    public boolean checkReturn()
    {
        if( !branch.checkReturn() ) return false;
        if( stmtElse == null || !stmtElse.checkReturn() ) return false;
        for(int i = 0; i < elseif.size(); i++)
        {
            if( !elseif.get(i).checkReturn() ) return false;
        }
        return true;
    }
    @Override
    public int getLine()
    {
        return branch.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        branch.genCode(writer);
        for(int i = 0; i < elseif.size(); i++)
        {
            elseif.get(i).genCode(writer);
        }
        if(stmtElse != null)
            stmtElse.genCode(writer);
        writer.placeLabel(elseStart);
    }
}