package edvardas.ast.nodes;

import edvardas.Main;
import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.codeGeneration.Label;
import edvardas.parser.Scope;

public class StatementBreak extends Statement {
    private Token keyword;
    public StatementBreak(Token kw) {
        keyword = kw;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("stmtBreak", keyword);
    }
    @Override
    public void resolveNames(Scope s) throws Exception {
        Node ancestor = findAncestor(StatementWhile.class);
        if(ancestor != null) return;
        ancestor = findAncestor(StatementFor.class);
        if(ancestor != null) return;
        Main.error(Main.filename + ":" + keyword.getLine() + ": " + "break is not inside a loop statement");
    }
    @Override
    public Node checkTypes()
    {
        // do nothing
        return null;
    }
    @Override
    public int getLine()
    {
        return keyword.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        Label end = null;
        StatementFor forStmt = (StatementFor)findAncestor(StatementFor.class);
        if(forStmt == null)
        {
            end = ((StatementWhile)findAncestor(StatementWhile.class)).getEnd();
        }
        else
        {
            end = forStmt.getEnd();
        }
        writer.write(Instruction.JMP, end, State.TYPE_VOID);
    }
}