package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.codeGeneration.Label;
import edvardas.parser.Scope;

public class StatementWhile extends Statement {
    private Expression condition;
    private StmtBody body;
    private Label start;
    private Label end;
    
    public StatementWhile(Expression cond, StmtBody body) {
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
    public Label getStart()
    {
        return start;
    }
    public Label getEnd()
    {
        return end;
    }
    @Override
    public void resolveNames(Scope s) throws Exception 
    {
        condition.resolveNames(s);
        body.resolveNames(s);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node tBool = new TypePrim(State.TYPE_BOOL);
        Node tCondition = condition.checkTypes();
        unifyTypes(tCondition, tBool, this.getLine());
        body.checkTypes();
        return null;
    }
    @Override
    public int getLine()
    {
        return condition.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        start = new Label();
        end = new Label();
        writer.placeLabel(start);
        condition.genCode(writer);
        writer.write(Instruction.JZ, end, State.TYPE_INT);
        body.genCode(writer);
        writer.placeLabel(end);
    }
}