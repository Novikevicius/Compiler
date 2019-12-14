package edvardas.ast.nodes;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class VarReadArgument extends ReadArgument {
    private Token name;
    public int stack_slot;
    private State type;

    public VarReadArgument(Token name) {
        this.name = name;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
    }
    @Override
    public void resolveNames(Scope s) 
    {
        Node n = s.resolveName(name).parent;
        if(n instanceof Decl)
            stack_slot = ((Decl)n).stack_slot;
        else
        {
            stack_slot = ((StatementVarDecl)n).getDeclaration().stack_slot;
        }
        try {
			type = ((TypePrim)n.checkTypes()).getKind();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @Override
    public Node checkTypes() throws Exception {
        // do nothing
        return null;
    }
    @Override
    public int getLine()
    {
        return name.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        writer.write(Instruction.PUSH, stack_slot, State.TYPE_INT);
    }
}