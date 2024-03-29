package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.codeGeneration.Label;
import edvardas.parser.Scope;

public class Branch extends Node {
    private Expression condition;
    private StmtBody body;
    
    public Branch(Expression cond, StmtBody body) {
        condition = cond;
        this.body = body;
        addChildren(condition);
        addChildren(body);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("condition", condition);
        printer.print("body", body);
    }
    @Override
    public void resolveNames(Scope parentScope) throws Exception
    {
        condition.resolveNames(parentScope);
        body.resolveNames(parentScope);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        TypePrim tBool = new TypePrim(State.TYPE_BOOL);
        Node tCond = condition.checkTypes();
        unifyTypes(tCond, tBool, this.getLine());
        body.checkTypes();
        return null;
    }
    @Override
    public boolean checkReturn()
    {
        return body.checkReturn();
    }
    @Override
    public int getLine()
    {
        return condition.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        Label end = new Label();
        condition.genCode(writer);
        writer.write(Instruction.JZ, end, State.TYPE_INT);
        body.genCode(writer);
        StatementIf stmtIf = (StatementIf)parent;
        ArrayList<Branch> elseifs = stmtIf.getElseifs();
        boolean isLastBranch = (elseifs.size() > 0 && elseifs.get(elseifs.size()-1) == this);
        if((!isLastBranch || stmtIf.hasElse()))
        {
            if(this != stmtIf.getBranch() || stmtIf.hasElse() || elseifs.size() > 0)
                writer.write(Instruction.JMP, ((StatementIf)parent).getElseStart(), State.TYPE_INT);
        }
        writer.placeLabel(end);
    }
}