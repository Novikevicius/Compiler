package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.parser.Scope;

public class StmtBody extends Statement {
    private ArrayList<Statement> stmts;

    public StmtBody(ArrayList<Statement> s) {
        stmts = s;
        for (Statement stmt : stmts) {
            addChildren(stmt);
        }
    }

    public void print(ASTPrinter printer) throws Exception {
        printer.print("statements", stmts);
    }

    @Override
    public void resolveNames(Scope parentScope) {
        Scope scope = new Scope(parentScope);
        stmts.forEach((stmt) -> {
            try {
                ((Statement) stmt).resolveNames(scope);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public boolean checkReturn()
    {
        for( int i = 0; i < stmts.size(); i++)
        {
            Statement stmt = stmts.get(i);
            if(stmt instanceof StatementIf || stmt instanceof StmtBody)
            {
                if( stmt.checkReturn() )
                    return true;
            }
            if(stmt instanceof StatementReturn) return true;
        }
        return false;
    }

    @Override
    public Node checkTypes() throws Exception {
        stmts.forEach((stmt) -> {
            try {
                ((Statement) stmt).checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return null;
    }
    @Override
    public int getLine()
    {
        if(stmts.size() > 0)
            return stmts.get(0).getLine();
        return 0;
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        for(Statement stmt: stmts)
        {
            stmt.genCode(writer);
        }
    }
}