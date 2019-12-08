package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.codeGeneration.Label;
import edvardas.parser.Scope;

public class StatementFor extends Statement {
    private VarDeclaration initialization;
    private Expression condition;
    private StatementAssignment afterthought;
    private StmtBody body;
    private Label start;
    private Label endLoopBody;
    
    public StatementFor(VarDeclaration initialization, Expression cond, StatementAssignment afterthought, StmtBody body) {
        condition = cond;
        this.initialization = initialization;
        this.afterthought = afterthought;
        this.body = body;
        addChildren(initialization);
        addChildren(condition);
        addChildren(afterthought);
        addChildren(body);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("initialization", initialization);
        printer.print("condition", condition);
        printer.print("afterthought", afterthought);
        printer.print("body", body);
    }
    public Label getStart()
    {
        return start;
    }
    public Label getEnd()
    {
        return endLoopBody;
    }
    @Override
    public void resolveNames(Scope parentScope) throws Exception 
    {
        Scope scope = new Scope(parentScope);
        if(initialization != null)
            initialization.resolveNames(scope);
        condition.resolveNames(scope);
        if(afterthought != null)
            afterthought.resolveNames(scope);
        body.resolveNames(scope);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        if(initialization != null)
            initialization.checkTypes();
        Node tBool = new TypePrim(State.TYPE_BOOL);
        Node tCondition = condition.checkTypes();
        unifyTypes(tCondition, tBool, condition.getLine());
        if(afterthought != null)
            afterthought.checkTypes();
        body.checkTypes();
        return null;
    }
    @Override
    public int getLine()
    {
        return initialization.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        if(initialization != null)
            initialization.genCode(writer);
        start = new Label();
        endLoopBody = new Label();
        Label end = new Label();
        writer.placeLabel(start);
        condition.genCode(writer);
        writer.write(Instruction.JZ, end, State.TYPE_INT);
        body.genCode(writer);
        writer.placeLabel(endLoopBody);
        if(afterthought != null)
            afterthought.genCode(writer);
        writer.write(Instruction.JMP, start, State.TYPE_INT);
        writer.placeLabel(end);
    }
}